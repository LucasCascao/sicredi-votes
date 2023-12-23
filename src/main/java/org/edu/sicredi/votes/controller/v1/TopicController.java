package org.edu.sicredi.votes.controller.v1;

import lombok.RequiredArgsConstructor;
import org.edu.sicredi.votes.builder.TopicBuilder;
import org.edu.sicredi.votes.domain.model.TopicVotesCountResult;
import org.edu.sicredi.votes.domain.request.CreateTopicRequest;
import org.edu.sicredi.votes.domain.response.CreateTopicResponse;
import org.edu.sicredi.votes.domain.response.TopicVotesCountResultResponse;
import org.edu.sicredi.votes.service.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/topic")
@RequiredArgsConstructor
public class TopicController {

  private final TopicService service;
  private final TopicBuilder topicBuilder;

  @GetMapping("/{topicId}")
  public ResponseEntity<TopicVotesCountResultResponse> getTopicResult(
      @PathVariable(name = "topicId") String topicId
  ) {
    TopicVotesCountResult topicVotesCountResult = this.service.countVotesByTopic(topicId);
    return ResponseEntity.ok().body(
        topicBuilder.buildTopicVotesResultResponse(topicVotesCountResult)
    );
  }

  @PostMapping
  public ResponseEntity<CreateTopicResponse> createTopic (
      @RequestBody CreateTopicRequest createTopicRequest
  ) {
    String topicId = this.service.createNewTopic(
        createTopicRequest.getName(),
        createTopicRequest.getDescription(),
        createTopicRequest.getSecondsToExpire()
    );
    return ResponseEntity.status(HttpStatus.CREATED).body(
        CreateTopicResponse.builder()
            .topicId(topicId)
            .build()
    );
  }

  @PostMapping("/{topicId}")
  public ResponseEntity<Void> initializeVoting (
      @PathVariable(name = "topicId") String topicId
  ) {
    this.service.startTopicVoting(topicId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

}

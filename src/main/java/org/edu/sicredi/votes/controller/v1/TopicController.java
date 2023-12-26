package org.edu.sicredi.votes.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.edu.sicredi.votes.builder.TopicBuilder;
import org.edu.sicredi.votes.domain.persistence.TopicVotesResultPersistence;
import org.edu.sicredi.votes.domain.request.CreateTopicRequest;
import org.edu.sicredi.votes.domain.response.CreateTopicResponse;
import org.edu.sicredi.votes.domain.response.ErrorMessageResponse;
import org.edu.sicredi.votes.domain.response.TopicVotesResultResponse;
import org.edu.sicredi.votes.service.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Topic", description = "Topic management APIs")
@RestController
@RequestMapping("/v1/topic")
@RequiredArgsConstructor
public class TopicController {

  private final TopicService service;
  private final TopicBuilder topicBuilder;

  @Operation(
      summary = "Get Topic Voting Result",
      description = "Endpoint responsible for counting and returning the topic voting results"
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = TopicVotesResultResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}),
      @ApiResponse(responseCode = "500", content = {
          @Content(schema = @Schema(implementation = ErrorMessageResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)})
  })
  @GetMapping("/{topicId}")
  public ResponseEntity<TopicVotesResultResponse> getTopicResult(
      @PathVariable(name = "topicId") String topicId
  ) {
    TopicVotesResultPersistence topicVotesResult = this.service.findTopicVotingResultByTopicId(topicId);
    return ResponseEntity.ok().body(
        topicBuilder.buildTopicVotesResultResponse(topicVotesResult)
    );
  }

  @Operation(
      summary = "Create a New Topic",
      description = "Endpoint responsible for create a new topic for the associates to vote"
  )
  @ApiResponses({
      @ApiResponse(responseCode = "201", content = {
          @Content(schema = @Schema(implementation = CreateTopicResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}),
      @ApiResponse(responseCode = "500", content = {
          @Content(schema = @Schema(implementation = ErrorMessageResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)})
  })
  @PostMapping
  public ResponseEntity<CreateTopicResponse> createTopic(
      @Valid @RequestBody CreateTopicRequest createTopicRequest
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

  @Operation(
      summary = "Enable The Topic to Receive Votes",
      description = "Endpoint responsible for enabling the topic to receive votes from the associates"
  )
  @ApiResponses({
      @ApiResponse(responseCode = "204", content = {
          @Content(schema = @Schema(implementation = CreateTopicResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}),
      @ApiResponse(responseCode = "500", content = {
          @Content(schema = @Schema(implementation = ErrorMessageResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)})
  })
  @PostMapping("/{topicId}")
  public ResponseEntity<Void> initializeVoting (
      @PathVariable(name = "topicId") String topicId
  ) {
    this.service.startTopicVoting(topicId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

}

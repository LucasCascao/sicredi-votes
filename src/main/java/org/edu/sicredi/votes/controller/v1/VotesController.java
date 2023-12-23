package org.edu.sicredi.votes.controller.v1;

import lombok.RequiredArgsConstructor;
import org.edu.sicredi.votes.domain.enums.VoteOptionEnum;
import org.edu.sicredi.votes.domain.request.RegisterVoteRequest;
import org.edu.sicredi.votes.service.VoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/votes")
@RequiredArgsConstructor
public class VotesController {

  private final VoteService service;

  @PostMapping
  public ResponseEntity<Void> registerIndividualVote(
      @RequestBody RegisterVoteRequest request) {
    service.registerVote(
        request.getAssociateCpf(),
        request.getTopicId(),
        VoteOptionEnum.get(request.getVote())
    );
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

}

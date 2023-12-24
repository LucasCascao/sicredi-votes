package org.edu.sicredi.votes.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.edu.sicredi.votes.domain.enums.VoteOptionEnum;
import org.edu.sicredi.votes.domain.request.RegisterVoteRequest;
import org.edu.sicredi.votes.domain.response.ErrorMessageResponse;
import org.edu.sicredi.votes.service.VoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Votes", description = "Votes management APIs")
@RestController
@RequestMapping("/v1/votes")
@RequiredArgsConstructor
public class VotesController {

  private final VoteService service;

  @Operation(
      summary = "Register Individual Vote",
      description = "Endpoint responsible for register the associate's vote"
  )
  @ApiResponses({
      @ApiResponse(responseCode = "201"),
      @ApiResponse(responseCode = "500", content = {
          @Content(schema = @Schema(implementation = ErrorMessageResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)})
  })
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

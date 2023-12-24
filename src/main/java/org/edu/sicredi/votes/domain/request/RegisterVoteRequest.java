package org.edu.sicredi.votes.domain.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterVoteRequest {
  @NotNull
  private String associateCpf;
  @NotNull
  private String topicId;
  @NotNull
  private String vote;
}

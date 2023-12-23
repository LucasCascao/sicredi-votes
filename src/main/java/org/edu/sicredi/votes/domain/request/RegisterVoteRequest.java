package org.edu.sicredi.votes.domain.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterVoteRequest {
  private String associateCpf;
  private String topicId;
  private String vote;
}

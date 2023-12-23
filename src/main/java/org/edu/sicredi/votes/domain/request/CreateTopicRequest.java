package org.edu.sicredi.votes.domain.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTopicRequest {
  private String name;
  private String description;
  private long secondsToExpire;
}

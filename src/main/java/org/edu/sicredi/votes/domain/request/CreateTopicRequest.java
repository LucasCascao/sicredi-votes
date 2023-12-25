package org.edu.sicredi.votes.domain.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTopicRequest {
  @NotNull
  private String name;
  @NotNull
  @Size(min = 10)
  private String description;
  private long secondsToExpire;
}

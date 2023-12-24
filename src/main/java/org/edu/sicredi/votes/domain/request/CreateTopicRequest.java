package org.edu.sicredi.votes.domain.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTopicRequest {
  @NotNull
  @Size(min = 5)
  private String name;
  @NotNull(message = "Field description should not be null")
  @Size(min = 10)
  private String description;
  private long secondsToExpire;
}

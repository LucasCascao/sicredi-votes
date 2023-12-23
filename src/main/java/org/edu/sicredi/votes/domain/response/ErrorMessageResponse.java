package org.edu.sicredi.votes.domain.response;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessageResponse {
  private String errorMessage;
  private Date time;
}

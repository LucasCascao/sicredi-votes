package org.edu.sicredi.votes.domain.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class BusinessException extends RuntimeException {
  private String message;
  private Throwable cause;
  private HttpStatus httpStatus;
}

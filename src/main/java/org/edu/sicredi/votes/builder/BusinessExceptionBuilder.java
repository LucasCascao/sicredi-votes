package org.edu.sicredi.votes.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.edu.sicredi.votes.domain.exception.BusinessException;
import org.springframework.http.HttpStatus;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BusinessExceptionBuilder {

  public static BusinessException buildBusinessException(HttpStatus httpStatus, String message) {
    return BusinessException.builder()
        .httpStatus(httpStatus)
        .message(message)
        .build();
  }

}

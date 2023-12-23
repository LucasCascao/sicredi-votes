package org.edu.sicredi.votes.mock;

import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.MESSAGE_MOCK;

import org.edu.sicredi.votes.domain.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class BusinessExceptionMockBuilder {

  private BusinessExceptionMockBuilder(){}

  public static BusinessException aGenericBusinessException() {
    return BusinessException.builder()
        .httpStatus(HttpStatus.I_AM_A_TEAPOT)
        .message(MESSAGE_MOCK)
        .build();
  }

}

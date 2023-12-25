package org.edu.sicredi.votes.mock;

import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.EMPTY_STRING;
import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.MESSAGE_MOCK;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.TOPIC_NOT_OPENED_FOR_VOTING_MESSAGE;

import java.lang.reflect.Method;
import java.util.Map;
import org.edu.sicredi.votes.domain.exception.BusinessException;
import org.edu.sicredi.votes.domain.response.CreateTopicResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class BusinessExceptionMockBuilder {

  private BusinessExceptionMockBuilder(){}

  public static BusinessException aGenericBusinessException() {
    return BusinessException.builder()
        .httpStatus(HttpStatus.I_AM_A_TEAPOT)
        .message(MESSAGE_MOCK)
        .build();
  }
  public static MethodArgumentNotValidException aMethodArgumentNotValidException() {
    MapBindingResult mapBindingResult = new MapBindingResult(Map.of(), EMPTY_STRING);
    Method method = CreateTopicResponse.class.getMethods()[0];
    MethodParameter methodParameter = new MethodParameter(method, 0);
    return new MethodArgumentNotValidException(methodParameter,mapBindingResult);
  }

  public static BusinessException aTopicNotOpenedBusinessException() {
    return BusinessException.builder()
        .httpStatus(HttpStatus.BAD_REQUEST)
        .message(TOPIC_NOT_OPENED_FOR_VOTING_MESSAGE)
        .build();
  }

}

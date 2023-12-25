package org.edu.sicredi.votes.handler;

import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.MESSAGE_MOCK;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.BODY_CONTENT_HAS_ERROR_MESSAGE;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.UNEXPECTED_ERROR_MESSAGE;

import org.edu.sicredi.votes.domain.exception.BusinessException;
import org.edu.sicredi.votes.domain.exception.handler.GlobalExceptionHandler;
import org.edu.sicredi.votes.domain.response.ErrorMessageResponse;
import org.edu.sicredi.votes.mock.BusinessExceptionMockBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ExtendWith(MockitoExtension.class)
public class GlobalHandlerExceptionTest {

  @InjectMocks
  private GlobalExceptionHandler exceptionHandler;

  @Test
  void givenABusinessExceptionWhenProcessingARequestThenReturnAErrorMessageResponseEntity() {
    BusinessException businessException = BusinessExceptionMockBuilder.aGenericBusinessException();
    ResponseEntity<ErrorMessageResponse> responseEntity = exceptionHandler.handleBusinessException(
        businessException);
    Assertions.assertNotNull(responseEntity);
    Assertions.assertNotNull(responseEntity.getBody());
    Assertions.assertEquals(businessException.getHttpStatus(),
        responseEntity.getStatusCode());
    Assertions.assertEquals(businessException.getMessage(),
        responseEntity.getBody().getErrorMessage());
  }

  @Test
  void givenAMethodArgumentNotValidExceptionWhenProcessingARequestThenReturnAErrorMessageResponseEntity() {
    MethodArgumentNotValidException businessException = BusinessExceptionMockBuilder.aMethodArgumentNotValidException();
    ResponseEntity<ErrorMessageResponse> responseEntity = exceptionHandler.handleMethodArgumentNotValidException(
        businessException);
    Assertions.assertNotNull(responseEntity);
    Assertions.assertNotNull(responseEntity.getBody());
    Assertions.assertEquals(HttpStatus.BAD_REQUEST,
        responseEntity.getStatusCode());
    Assertions.assertEquals(BODY_CONTENT_HAS_ERROR_MESSAGE,
        responseEntity.getBody().getErrorMessage());
  }

  @Test
  void givenAnExceptionWhenProcessingARequestThenReturnAErrorMessageResponseEntity() {
    ResponseEntity<ErrorMessageResponse> responseEntity = exceptionHandler.handleException(
        new Exception(MESSAGE_MOCK));
    Assertions.assertNotNull(responseEntity);
    Assertions.assertNotNull(responseEntity.getBody());
    Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
        responseEntity.getStatusCode());
    Assertions.assertEquals(UNEXPECTED_ERROR_MESSAGE,
        responseEntity.getBody().getErrorMessage());
  }

}

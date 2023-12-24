package org.edu.sicredi.votes.domain.exception.handler;

import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.BODY_CONTENT_HAS_ERROR_MESSAGE;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.UNEXPECTED_ERROR_MESSAGE;

import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.edu.sicredi.votes.domain.exception.BusinessException;
import org.edu.sicredi.votes.domain.response.ErrorMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorMessageResponse> handleException(Exception exception) {
    log.error(exception.getMessage(), exception);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
        ErrorMessageResponse.builder()
            .errorMessage(UNEXPECTED_ERROR_MESSAGE)
            .time(new Date())
            .build()
    );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorMessageResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
    log.error(exception.getMessage(), exception);
    return ResponseEntity.status(exception.getStatusCode()).body(
        ErrorMessageResponse.builder()
            .errorMessage(BODY_CONTENT_HAS_ERROR_MESSAGE)
            .time(new Date())
            .build()
    );
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorMessageResponse> handleBusinessException(BusinessException businessException) {
    log.error(businessException.getMessage(), businessException);
    return ResponseEntity.status(businessException.getHttpStatus()).body(
        ErrorMessageResponse.builder()
            .errorMessage(businessException.getMessage())
            .time(new Date())
            .build()
    );
  }

}

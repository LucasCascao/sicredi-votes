package org.edu.sicredi.votes.builder;

import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.MESSAGE_MOCK;

import org.edu.sicredi.votes.domain.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
public class BusinessExceptionBuilderTest {

  @Test
  void givenABusinessErrorWhenProcessingAJourneyThenBuildABusinessExceptionWithMessageAndHttpStatusCode() {
    BusinessException businessException = BusinessExceptionBuilder.buildBusinessException(
        HttpStatus.I_AM_A_TEAPOT, MESSAGE_MOCK);
    Assertions.assertNotNull(businessException);
    Assertions.assertEquals(HttpStatus.I_AM_A_TEAPOT, businessException.getHttpStatus());
    Assertions.assertEquals(MESSAGE_MOCK, businessException.getMessage());
  }

}

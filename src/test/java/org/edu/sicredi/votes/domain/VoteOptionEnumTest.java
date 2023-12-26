package org.edu.sicredi.votes.domain;

import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.UNKNOWN_OPTION_NAME;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.INVALID_VOTE_OPTION_VALUE_MESSAGE;
import static org.junit.jupiter.api.Assertions.fail;

import org.edu.sicredi.votes.domain.enums.VoteOptionEnum;
import org.edu.sicredi.votes.domain.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class VoteOptionEnumTest {

  @Test
  void givenAUnknownVoteOptionNameWhenConvertingVoteOptionNameToEnumThenThrowBusinessException() {
    try {
      VoteOptionEnum.get(UNKNOWN_OPTION_NAME);
      fail();
    } catch (BusinessException businessException) {
      Assertions.assertEquals(HttpStatus.BAD_REQUEST, businessException.getHttpStatus());
      Assertions.assertEquals(INVALID_VOTE_OPTION_VALUE_MESSAGE, businessException.getMessage());
    }
  }

}

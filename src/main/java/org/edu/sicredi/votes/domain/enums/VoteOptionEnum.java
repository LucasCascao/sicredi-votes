package org.edu.sicredi.votes.domain.enums;

import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.INVALID_VOTE_OPTION_VALUE_MESSAGE;

import java.util.Arrays;
import lombok.Getter;
import org.edu.sicredi.votes.domain.exception.BusinessException;
import org.springframework.http.HttpStatus;

@Getter
public enum VoteOptionEnum {

  YES("Sim"),
  NO("Não");

  private final String optionName;

  VoteOptionEnum(String option) {
    this.optionName = option;
  }

  public static VoteOptionEnum get(String option) {
    return Arrays.stream(VoteOptionEnum.values()).filter(voteOptionEnum ->
        voteOptionEnum.optionName.equalsIgnoreCase(option)).findFirst().orElseThrow(
        () -> BusinessException.builder()
            .httpStatus(HttpStatus.BAD_REQUEST)
            .message(INVALID_VOTE_OPTION_VALUE_MESSAGE)
            .build()
        );
  }

}

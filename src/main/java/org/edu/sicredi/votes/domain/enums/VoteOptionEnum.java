package org.edu.sicredi.votes.domain.enums;

import java.util.Arrays;
import lombok.Getter;
import org.edu.sicredi.votes.domain.exception.BusinessException;
import org.springframework.http.HttpStatus;

@Getter
public enum VoteOptionEnum {
  YES("Sim"),
  NO("Não");

  private final String description;

  VoteOptionEnum(String option) {
    this.description = option;
  }

  public static VoteOptionEnum get(String option) {
    return Arrays.stream(VoteOptionEnum.values()).filter(voteOptionEnum ->
        voteOptionEnum.description.equalsIgnoreCase(option)).findFirst().orElseThrow(
        () -> BusinessException.builder()
            .httpStatus(HttpStatus.BAD_REQUEST)
            .message("Invalid vote value")
            .build()
        );
  }

}

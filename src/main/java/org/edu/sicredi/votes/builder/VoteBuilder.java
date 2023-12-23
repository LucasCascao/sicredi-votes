package org.edu.sicredi.votes.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.edu.sicredi.votes.domain.enums.VoteOptionEnum;
import org.edu.sicredi.votes.domain.persistence.VotePersistence;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VoteBuilder {
  public static VotePersistence buildVotePersistence(String cpf, String topicId,
      VoteOptionEnum voteOption) {
    return VotePersistence.builder()
        .associateCpf(cpf)
        .topicId(topicId)
        .vote(voteOption)
        .build();
  }
}

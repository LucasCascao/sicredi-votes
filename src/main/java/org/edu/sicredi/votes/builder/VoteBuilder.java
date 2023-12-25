package org.edu.sicredi.votes.builder;

import org.edu.sicredi.votes.domain.enums.VoteOptionEnum;
import org.edu.sicredi.votes.domain.persistence.VotePersistence;
import org.springframework.stereotype.Service;

@Service
public class VoteBuilder {
  public VotePersistence buildVotePersistence(String cpf, String topicId,
      VoteOptionEnum voteOption) {
    return VotePersistence.builder()
        .associateCpf(cpf)
        .topicId(topicId)
        .vote(voteOption)
        .build();
  }
}

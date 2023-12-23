package org.edu.sicredi.votes.service;

import org.edu.sicredi.votes.domain.enums.VoteOptionEnum;

public interface VoteService {
  void registerVote(String associateCpf, String topicId, VoteOptionEnum voteOption);
}

package org.edu.sicredi.votes.service.impl;

import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.VOTE_REGISTERED_SUCCESSFULLY_MESSAGE;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edu.sicredi.votes.builder.VoteBuilder;
import org.edu.sicredi.votes.domain.enums.VoteOptionEnum;
import org.edu.sicredi.votes.domain.persistence.TopicPersistence;
import org.edu.sicredi.votes.domain.persistence.VotePersistence;
import org.edu.sicredi.votes.provider.TopicProvider;
import org.edu.sicredi.votes.service.VoteService;
import org.edu.sicredi.votes.validator.TopicValidator;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

  private final TopicValidator topicValidator;
  private final TopicProvider provider;

  @Override
  public void registerVote(String associateCpf, String topicId, VoteOptionEnum voteOption) {
    TopicPersistence topic = provider.findTopicById(topicId);
    topicValidator.verifyTopicIsOpenedToVote(topic);
    topicValidator.verifyIfAssociateCpfHasVoted(associateCpf, topic.getVotes());
    VotePersistence vote = VoteBuilder.buildVotePersistence(associateCpf, topicId, voteOption);
    topic.getVotes().add(vote);
    provider.saveTopic(topic);
    log.info(VOTE_REGISTERED_SUCCESSFULLY_MESSAGE,
        vote.getId(), topicId, voteOption.getDescription());
  }

}

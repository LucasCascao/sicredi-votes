package org.edu.sicredi.votes.service.impl;

import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.TOPIC_CREATED_SUCCESSFULLY_MESSAGE;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.TOPIC_OPENED_TO_RECEIVE_VOTES_MESSAGE;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.TOPIC_VOTES_COUNTED_SUCCESSFULLY_MESSAGE;

import java.util.Date;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edu.sicredi.votes.builder.TopicBuilder;
import org.edu.sicredi.votes.domain.model.TopicVotesCountResult;
import org.edu.sicredi.votes.domain.persistence.TopicPersistence;
import org.edu.sicredi.votes.domain.persistence.VotePersistence;
import org.edu.sicredi.votes.provider.TopicProvider;
import org.edu.sicredi.votes.service.TopicService;
import org.edu.sicredi.votes.validator.TopicValidator;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

  private final TopicProvider topicProvider;
  private final TopicBuilder topicBuilder;
  private final TopicValidator topicValidator;

  @Override
  public String createNewTopic(String topicName, String description,
      long secondsToExpire) {
    TopicPersistence debateItem = topicBuilder
        .buildTopicPersistence(topicName, description, secondsToExpire);
    String topicId = topicProvider.saveTopic(debateItem).getId();
    log.info(TOPIC_CREATED_SUCCESSFULLY_MESSAGE, topicName, topicId);
    return topicId;
  }

  @Override
  public void startTopicVoting(String topicId) {
    TopicPersistence topic = topicProvider.findTopicById(topicId);
    topicValidator.verifyIfTopicIsOpened(topic);
    Date currentTime = new Date();
    Date scheduledTimeToClose = new Date(currentTime.getTime() + topic.getTimeToExpire() * 1000);
    topic.setStartedAt(currentTime);
    topic.setClosedAt(scheduledTimeToClose);
    topicProvider.saveTopic(topic);
    log.info(TOPIC_OPENED_TO_RECEIVE_VOTES_MESSAGE,topicId);
  }

  @Override
  public TopicVotesCountResult countVotesByTopic(String topicId) {
    TopicPersistence debateItem = topicProvider.findTopicById(topicId);
    topicValidator.verifyIfTopicIsFinished(debateItem);
    Map<String, Long> countResultByOption = countVotesPerOption(debateItem);
    TopicVotesCountResult topicVotesCountResult =
        topicBuilder.buildTopicVotesCountResult(countResultByOption, debateItem);
    log.info(TOPIC_VOTES_COUNTED_SUCCESSFULLY_MESSAGE, topicId);
    return topicVotesCountResult;
  }

  private Map<String, Long> countVotesPerOption(TopicPersistence debateItem) {
    Map<String, Long> countResultByOption = topicBuilder.buildInitialCountResultByOption();
    for (VotePersistence vote : debateItem.getVotes()) {
      String description = vote.getVote().getDescription();
      long amount = countResultByOption.get(description) + 1;
      countResultByOption.put(description, amount);
    }
    return countResultByOption;
  }

}

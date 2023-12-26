package org.edu.sicredi.votes.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edu.sicredi.votes.builder.TopicBuilder;
import org.edu.sicredi.votes.domain.enums.TopicStatusEnum;
import org.edu.sicredi.votes.domain.persistence.TopicPersistence;
import org.edu.sicredi.votes.domain.persistence.TopicVotesResultPersistence;
import org.edu.sicredi.votes.domain.persistence.VotePersistence;
import org.edu.sicredi.votes.provider.TopicProvider;
import org.edu.sicredi.votes.schedule.TopicFinalizer;
import org.edu.sicredi.votes.service.TopicService;
import org.edu.sicredi.votes.validator.TopicValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

  public static final int INCREMENT_VOTE_VALUE = 1;
  private final TopicProvider topicProvider;
  private final TopicBuilder topicBuilder;
  private final TopicValidator topicValidator;
  private final KafkaTemplate<String, String> kafkaTemplate;

  @Value("${spring.kafka.topic-name}")
  private String topicName;

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
    topicValidator.verifyTopicIsAbleToBeInitialized(topic);
    topic.setStatus(TopicStatusEnum.INITIALIZED);
    topic.setStartedAt(new Date());
    topicProvider.saveTopic(topic);
    TopicFinalizer topicFinalizer = TopicFinalizer.builder()
        .topicIdScheduled(topic.getId())
        .topicService(this)
        .build();
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    scheduler.schedule(topicFinalizer, topic.getSecondsToExpire(), TimeUnit.SECONDS);
    scheduler.shutdown();
    log.info(TOPIC_OPENED_TO_RECEIVE_VOTES_MESSAGE,topicId);
  }

  @Override
  public void finalizeTopicVoting(String topicId) {
    TopicPersistence topic = topicProvider.findTopicById(topicId);
    topic.setClosedAt(new Date());
    topic.setStatus(TopicStatusEnum.FINISHED);
    topicProvider.saveTopic(topic);
    Map<String, Long> countResultByOption = countVotesPerOption(topic);
    TopicVotesResultPersistence topicVotesResult =
        topicBuilder.buildTopicVotesCountResult(countResultByOption, topic);
    topicProvider.saveTopicVotingResult(topicVotesResult);
    try{
      kafkaTemplate.send(topicName, new ObjectMapper().writeValueAsString(topicVotesResult));
    } catch (JsonProcessingException jsonProcessingException) {
      throw new RuntimeException(ERROR_DURING_OBJECT_MAPPING_CONVERSION_MESSAGE, jsonProcessingException);
    }
    log.info(TOPIC_FINALIZED_MESSAGE,topicId);
  }

  @Override
  public TopicVotesResultPersistence findTopicVotingResultByTopicId(String topicId) {
    TopicPersistence topic = topicProvider.findTopicById(topicId);
    topicValidator.verifyTopicVotingIsFinalized(topic);
    TopicVotesResultPersistence topicVotesResult = topicProvider.findVotingResultByTopicId(topicId);
    log.info(TOPIC_VOTES_COUNTED_SUCCESSFULLY_MESSAGE, topicId);
    return topicVotesResult;
  }

  private Map<String, Long> countVotesPerOption(TopicPersistence topic) {
    Map<String, Long> countResultByOption = topicBuilder.buildInitialCountResultByOption();
    for (VotePersistence vote : topic.getVotes()) {
      String optionName = vote.getVote().getOptionName();
      long currentAmount = countResultByOption.get(optionName);
      long incrementedAmount = currentAmount + INCREMENT_VOTE_VALUE;
      countResultByOption.put(optionName, incrementedAmount);
    }
    return countResultByOption;
  }
}

package org.edu.sicredi.votes.service;

import static org.edu.sicredi.votes.builder.TopicBuilder.VOTE_INITIAL_COUNT_VALUE;
import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.edu.sicredi.votes.builder.TopicBuilder;
import org.edu.sicredi.votes.constants.FieldValuesMockConstants;
import org.edu.sicredi.votes.domain.enums.TopicStatusEnum;
import org.edu.sicredi.votes.domain.enums.VoteOptionEnum;
import org.edu.sicredi.votes.domain.persistence.TopicPersistence;
import org.edu.sicredi.votes.domain.persistence.TopicVotesResultPersistence;
import org.edu.sicredi.votes.mock.TopicMockBuilder;
import org.edu.sicredi.votes.provider.TopicProvider;
import org.edu.sicredi.votes.service.impl.TopicServiceImpl;
import org.edu.sicredi.votes.validator.TopicValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class TopicServiceImplTest {

  @InjectMocks
  private TopicServiceImpl topicService;

  @Mock
  private TopicProvider topicProvider;

  @Mock
  private TopicBuilder topicBuilder;

  @Mock
  private TopicValidator topicValidator;

  @Mock
  private KafkaTemplate<String, String> kafkaTemplate;

  @BeforeEach
  void setup() {
    ReflectionTestUtils.setField(topicService, KAFKA_TOPIC_FIELD_NAME_MOCK, KAFKA_TOPIC_NAME_MOCK);
  }

  @Test
  void givenValidTopicDataWhenCreatingNewTopicThenReturnNewTopicId() {
    TopicPersistence topicPersistence = TopicMockBuilder.aCreatedTopicPersistenceWithoutTopicId();
    given(topicBuilder.buildTopicPersistence(FieldValuesMockConstants.TOPIC_NAME_MOCK, DESCRIPTION_MOCK,
        SECONDS_TO_EXPIRE_MOCK)).willReturn(topicPersistence);
    given(topicProvider.saveTopic(any(TopicPersistence.class)))
        .willReturn(topicPersistence);
    String topicId = topicService.createNewTopic(FieldValuesMockConstants.TOPIC_NAME_MOCK, DESCRIPTION_MOCK,
        SECONDS_TO_EXPIRE_MOCK);
    Assertions.assertEquals(topicPersistence.getId(), topicId);
  }

  @Test
  void givenACreatedTopicIdWhenInitializingTopicVotingThenUpdateStatusToInitializedAndScheduleFinalizeFunctionExecution() {
    TopicPersistence topic = TopicMockBuilder.aCreatedTopicPersistence();
    given(topicProvider.findTopicById(TOPIC_ID_MOCK)).willReturn(topic);
    willDoNothing().given(topicValidator).verifyTopicIsAbleToBeInitialized(topic);
    given(topicProvider.saveTopic(topic)).willReturn(topic);
    topicService.startTopicVoting(TOPIC_ID_MOCK);
    Assertions.assertNotNull(topic.getStartedAt());
    Assertions.assertEquals(TopicStatusEnum.INITIALIZED, topic.getStatus());
  }

  @Test
  void givenAInitializedTopicIdWhenFinishingTopicVotingThenUpdateStatusToFinishedAndSendMessageToAMessagingQueue() throws JsonProcessingException {
    TopicPersistence topic = TopicMockBuilder.aInitializedTopicPersistenceWithVotes();
    Map<String, Long> countResultByOption = new HashMap<>(Map.of(
        VoteOptionEnum.YES.getOptionName(), VOTE_INITIAL_COUNT_VALUE,
        VoteOptionEnum.NO.getOptionName(), VOTE_INITIAL_COUNT_VALUE
    ));
    TopicVotesResultPersistence topicVotesResultResponse = TopicMockBuilder.aTopicVotesResultPersistence();
    given(topicProvider.findTopicById(TOPIC_ID_MOCK)).willReturn(topic);
    given(topicProvider.saveTopic(topic)).willReturn(topic);
    given(topicBuilder.buildInitialCountResultByOption()).willReturn(countResultByOption);
    given(topicBuilder.buildTopicVotesCountResult(countResultByOption, topic)).willReturn(
        topicVotesResultResponse);
    willDoNothing().given(topicProvider).saveTopicVotingResult(topicVotesResultResponse);
    given(kafkaTemplate.send(KAFKA_TOPIC_NAME_MOCK, new ObjectMapper().writeValueAsString(topicVotesResultResponse)))
            .willReturn(null);
    topicService.finalizeTopicVoting(TOPIC_ID_MOCK);
    Assertions.assertNotNull(topic.getClosedAt());
    Assertions.assertEquals(TopicStatusEnum.FINISHED, topic.getStatus());
  }

  @Test
  void givenTopicIdWhenFindingTopicVotesResultThenReturnTopicVotesResultPersistence() {
    TopicPersistence topic = TopicMockBuilder.aInitializedTopicPersistence();
    TopicVotesResultPersistence topicVotesResultMock = TopicMockBuilder.aTopicVotesResultPersistence();
    given(topicProvider.findTopicById(TOPIC_ID_MOCK)).willReturn(topic);
    willDoNothing().given(topicValidator).verifyTopicVotingIsFinalized(topic);
    given(topicProvider.findVotingResultByTopicId(TOPIC_ID_MOCK)).willReturn(topicVotesResultMock);
    TopicVotesResultPersistence topicVotesResult = topicService.findTopicVotingResultByTopicId(
        TOPIC_ID_MOCK);
    Assertions.assertNotNull(topicVotesResult);
    Assertions.assertEquals(topicVotesResultMock.getTopicId(), topicVotesResult.getTopicId());
    Assertions.assertEquals(topicVotesResultMock.getStartedAt(), topicVotesResult.getStartedAt());
    Assertions.assertEquals(topicVotesResultMock.getClosedAt(), topicVotesResult.getClosedAt());
    Assertions.assertEquals(topicVotesResultMock.getVotesTotalAmount(),
        topicVotesResult.getVotesTotalAmount());
    Assertions.assertTrue(topicVotesResultMock.getVoteAmountPerOption()
        .equals(topicVotesResult.getVoteAmountPerOption()));
  }


}

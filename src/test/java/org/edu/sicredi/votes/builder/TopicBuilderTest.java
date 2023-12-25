package org.edu.sicredi.votes.builder;

import static java.math.BigInteger.ZERO;
import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.DESCRIPTION_MOCK;
import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.SECONDS_TO_EXPIRE_MOCK;
import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.TOPIC_NAME_MOCK;

import org.edu.sicredi.votes.domain.enums.TopicStatusEnum;
import org.edu.sicredi.votes.domain.persistence.TopicPersistence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class TopicBuilderTest {

  @InjectMocks
  private TopicBuilder topicBuilder;

  @BeforeEach()
  void setup() {
    ReflectionTestUtils.setField(topicBuilder, "secondsToExpireDefault", SECONDS_TO_EXPIRE_MOCK);
  }

  @Test
  void givenTopicDataWhenCreatingTopicThenBuildATopicPersistence() {
    TopicPersistence topicPersistence = topicBuilder.buildTopicPersistence(TOPIC_NAME_MOCK,
        DESCRIPTION_MOCK, SECONDS_TO_EXPIRE_MOCK);
    Assertions.assertEquals(TOPIC_NAME_MOCK, topicPersistence.getName());
    Assertions.assertEquals(DESCRIPTION_MOCK, topicPersistence.getDescription());
    Assertions.assertEquals(SECONDS_TO_EXPIRE_MOCK, topicPersistence.getSecondsToExpire());
    Assertions.assertEquals(TopicStatusEnum.CREATED, topicPersistence.getStatus());
    Assertions.assertNotNull(topicPersistence.getCreatedAt());
    Assertions.assertNotNull(topicPersistence.getVotes());
  }

  @Test
  void givenTopicDataWithFieldSecondsToExpireZeroWhenCreatingTopicThenBuildATopicPersistenceWithDefaultSecondsToExpire() {
    TopicPersistence topicPersistence = topicBuilder.buildTopicPersistence(TOPIC_NAME_MOCK,
        DESCRIPTION_MOCK, ZERO.longValue());
    Assertions.assertEquals(TOPIC_NAME_MOCK, topicPersistence.getName());
    Assertions.assertEquals(DESCRIPTION_MOCK, topicPersistence.getDescription());
    Assertions.assertEquals(SECONDS_TO_EXPIRE_MOCK, topicPersistence.getSecondsToExpire());
    Assertions.assertEquals(TopicStatusEnum.CREATED, topicPersistence.getStatus());
    Assertions.assertNotNull(topicPersistence.getCreatedAt());
    Assertions.assertNotNull(topicPersistence.getVotes());
  }

  @Test
  void givenTopicVotingResultDataWhenCountVotesBuildATopicPersistenceWithDefaultSecondsToExpire() {

  }

}

package org.edu.sicredi.votes.builder;

import static java.math.BigInteger.ZERO;
import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.DESCRIPTION_MOCK;
import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.SECONDS_TO_EXPIRE_MOCK;
import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.TOPIC_NAME_MOCK;

import java.util.HashMap;
import java.util.Map;
import org.edu.sicredi.votes.domain.enums.TopicStatusEnum;
import org.edu.sicredi.votes.domain.enums.VoteOptionEnum;
import org.edu.sicredi.votes.domain.persistence.TopicPersistence;
import org.edu.sicredi.votes.domain.persistence.TopicVotesResultPersistence;
import org.edu.sicredi.votes.domain.response.TopicVotesResultResponse;
import org.edu.sicredi.votes.mock.TopicMockBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class TopicBuilderTest {

  public static final long YES_VOTES_AMOUNT_MOCK = 2L;
  public static final long NO_VOTES_AMOUNT_MOCK = 1L;
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
  void givenTopicVotingResultDataWhenCountingVotesThenBuildATopicVotesResultPersistence() {
    TopicPersistence topic = TopicMockBuilder.aFinishedTopicPersistence();
    HashMap<String, Long> topicVotingCountResult = new HashMap<>(Map.of(
        VoteOptionEnum.YES.getOptionName(), YES_VOTES_AMOUNT_MOCK,
        VoteOptionEnum.NO.getOptionName(), NO_VOTES_AMOUNT_MOCK
    ));
    TopicVotesResultPersistence topicVotesResult = topicBuilder.buildTopicVotesCountResult(
        topicVotingCountResult, topic);
    Assertions.assertNotNull(topicVotesResult);
    Assertions.assertNotNull(topicVotesResult.getVoteAmountPerOption()
        .get(VoteOptionEnum.YES.getOptionName()));
    Assertions.assertNotNull(topicVotesResult.getVoteAmountPerOption()
        .get(VoteOptionEnum.NO.getOptionName()));
    Map<String, Long> votesCountResult = topicVotesResult.getVoteAmountPerOption();
    Assertions.assertEquals(YES_VOTES_AMOUNT_MOCK,
        votesCountResult.get(VoteOptionEnum.YES.getOptionName()));
    Assertions.assertEquals(NO_VOTES_AMOUNT_MOCK,
        votesCountResult.get(VoteOptionEnum.NO.getOptionName()));
    Assertions.assertEquals(topic.getId(), topicVotesResult.getTopicId());
    Assertions.assertEquals(topic.getStartedAt(), topicVotesResult.getStartedAt());
    Assertions.assertEquals(topic.getClosedAt(), topicVotesResult.getClosedAt());
    Assertions.assertEquals(topic.getVotes().size(), topicVotesResult.getVotesTotalAmount());
  }

  @Test
  void givenATopicVotesResultPersistenceWhenFindingVotesResultThenBuildATopicVotesResultResponseObject() {
    TopicVotesResultPersistence topic = TopicMockBuilder.aTopicVotesResultPersistence();
    TopicVotesResultResponse topicVotesResult = topicBuilder.buildTopicVotesResultResponse(topic);
    Assertions.assertEquals(topic.getStartedAt(), topicVotesResult.getStartedAt());
    Assertions.assertEquals(topic.getClosedAt(), topicVotesResult.getClosedAt());
    Assertions.assertEquals(topic.getVoteAmountPerOption(), topicVotesResult.getVoteAmountPerOption());
    Assertions.assertEquals(topic.getVotesTotalAmount(), topicVotesResult.getVotesTotalAmount());
  }

  @Test
  void givenFinalizeVotingFunctionCallWhenCountingVotesResultThenBuildVoteOptionsMapWithVoteAmountZero() {
    Map<String, Long> countResultByOption = topicBuilder.buildInitialCountResultByOption();
    Assertions.assertNotNull(countResultByOption);
    Assertions.assertNotNull(countResultByOption.get(VoteOptionEnum.YES.getOptionName()));
    Assertions.assertNotNull(countResultByOption.get(VoteOptionEnum.NO.getOptionName()));
    Assertions.assertEquals(ZERO.longValue(), countResultByOption.get(VoteOptionEnum.YES.getOptionName()));
    Assertions.assertEquals(ZERO.longValue(), countResultByOption.get(VoteOptionEnum.NO.getOptionName()));
  }

}

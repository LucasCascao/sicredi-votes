package org.edu.sicredi.votes.builder;

import static java.math.BigInteger.ZERO;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.edu.sicredi.votes.domain.model.TopicVotesCountResult;
import org.edu.sicredi.votes.domain.persistence.TopicPersistence;
import org.edu.sicredi.votes.domain.response.TopicVotesCountResultResponse;
import org.edu.sicredi.votes.domain.enums.VoteOptionEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TopicBuilder {

  public static final long VOTE_INITIAL_COUNT_VALUE = 0L;

  @Value("${topic.settings.seconds-to-expire-default}")
  private long secondsToExpireDefault;

  public TopicPersistence buildTopicPersistence(
      String topicName,
      String description,
      long secondsToExpire
  ) {
    return TopicPersistence.builder()
        .name(topicName)
        .description(description)
        .createdAt(new Date())
        .timeToExpire(
            Objects.equals(secondsToExpire, ZERO.longValue()) ? secondsToExpireDefault : secondsToExpire)
        .votes(Set.of())
        .build();
  }

  public TopicVotesCountResult buildTopicVotesCountResult(
      Map<String, Long> countResultByOption,
      TopicPersistence topic
  ) {
    return TopicVotesCountResult.builder()
        .voteAmountPerOption(countResultByOption)
        .totalAmount(topic.getVotes().size())
        .startedAt(topic.getStartedAt())
        .closedAt(topic.getClosedAt())
        .build();
  }

  public TopicVotesCountResultResponse buildTopicVotesResultResponse(
      TopicVotesCountResult topicVotesCountResult
  ) {
    return TopicVotesCountResultResponse.builder()
        .voteAmountPerOption(topicVotesCountResult.getVoteAmountPerOption())
        .totalAmount(topicVotesCountResult.getTotalAmount())
        .startedAt(topicVotesCountResult.getStartedAt())
        .closedAt(topicVotesCountResult.getClosedAt())
        .build();
  }

  public Map<String, Long> buildInitialCountResultByOption() {
    return new HashMap<>(Map.of(
        VoteOptionEnum.YES.getDescription(), VOTE_INITIAL_COUNT_VALUE,
        VoteOptionEnum.NO.getDescription(), VOTE_INITIAL_COUNT_VALUE
    ));
  }
}

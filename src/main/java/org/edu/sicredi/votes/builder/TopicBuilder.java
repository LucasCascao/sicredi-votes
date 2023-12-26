package org.edu.sicredi.votes.builder;

import static java.math.BigInteger.ZERO;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.edu.sicredi.votes.domain.enums.TopicStatusEnum;
import org.edu.sicredi.votes.domain.enums.VoteOptionEnum;
import org.edu.sicredi.votes.domain.persistence.TopicPersistence;
import org.edu.sicredi.votes.domain.persistence.TopicVotesResultPersistence;
import org.edu.sicredi.votes.domain.response.TopicVotesResultResponse;
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
        .status(TopicStatusEnum.CREATED)
        .createdAt(new Date())
        .secondsToExpire(
            Objects.equals(secondsToExpire, ZERO.longValue()) ? secondsToExpireDefault : secondsToExpire)
        .votes(Set.of())
        .build();
  }

  public TopicVotesResultPersistence buildTopicVotesCountResult(
      Map<String, Long> countResultByOption,
      TopicPersistence topic
  ) {
    return TopicVotesResultPersistence.builder()
        .voteAmountPerOption(countResultByOption)
        .votesTotalAmount(topic.getVotes().size())
        .topicId(topic.getId())
        .startedAt(topic.getStartedAt())
        .closedAt(topic.getClosedAt())
        .build();
  }

  public TopicVotesResultResponse buildTopicVotesResultResponse(
      TopicVotesResultPersistence topicVotesResult
  ) {
    return TopicVotesResultResponse.builder()
        .voteAmountPerOption(topicVotesResult.getVoteAmountPerOption())
        .votesTotalAmount(topicVotesResult.getVotesTotalAmount())
        .startedAt(topicVotesResult.getStartedAt())
        .closedAt(topicVotesResult.getClosedAt())
        .build();
  }

  public Map<String, Long> buildInitialCountResultByOption() {
    return new HashMap<>(Map.of(
        VoteOptionEnum.YES.getOptionName(), VOTE_INITIAL_COUNT_VALUE,
        VoteOptionEnum.NO.getOptionName(), VOTE_INITIAL_COUNT_VALUE
    ));
  }
}

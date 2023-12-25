package org.edu.sicredi.votes.mock;

import static org.edu.sicredi.votes.builder.TopicBuilder.VOTE_INITIAL_COUNT_VALUE;
import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.ASSOCIATE_CPF_MOCK;
import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.DESCRIPTION_MOCK;
import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.SECONDS_TO_EXPIRE_MOCK;
import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.TOPIC_ID_MOCK;
import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.TOPIC_NAME_MOCK;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.edu.sicredi.votes.domain.enums.TopicStatusEnum;
import org.edu.sicredi.votes.domain.enums.VoteOptionEnum;
import org.edu.sicredi.votes.domain.model.TopicVotesCountResult;
import org.edu.sicredi.votes.domain.persistence.TopicPersistence;
import org.edu.sicredi.votes.domain.persistence.VotePersistence;
import org.edu.sicredi.votes.domain.request.CreateTopicRequest;
import org.edu.sicredi.votes.domain.response.TopicVotesResultResponse;

public class TopicMockBuilder {

  private TopicMockBuilder() {
  }

  public static CreateTopicRequest aCreateTopicRequest() {
    return CreateTopicRequest.builder()
        .name(TOPIC_NAME_MOCK)
        .description(DESCRIPTION_MOCK)
        .secondsToExpire(SECONDS_TO_EXPIRE_MOCK)
        .build();
  }

  public static TopicPersistence aCreatedTopicPersistence() {
    return aTopicPersistence()
        .id(TOPIC_ID_MOCK)
        .status(TopicStatusEnum.CREATED)
        .build();
  }

  public static TopicPersistence aCreatedTopicPersistenceWithoutTipicId() {
    return aTopicPersistence()
        .status(TopicStatusEnum.CREATED)
        .build();
  }

  public static TopicPersistence aInitializedTopicPersistence() {
    return aTopicPersistence()
        .status(TopicStatusEnum.INITIALIZED)
        .build();
  }

  public static TopicPersistence aInitializedTopicPersistenceWithVotes() {
    ;
    return aTopicPersistence()
        .status(TopicStatusEnum.INITIALIZED)
        .votes(Set.of(aYesVotePersistence()))
        .build();
  }

  public static VotePersistence aYesVotePersistence() {
    return VotePersistence.builder()
        .vote(VoteOptionEnum.YES)
        .topicId(TOPIC_ID_MOCK)
        .associateCpf(ASSOCIATE_CPF_MOCK)
        .build();
  }

  public static TopicPersistence aFinishedTopicPersistence() {
    return aTopicPersistence()
        .status(TopicStatusEnum.FINISHED)
        .build();
  }

  private static TopicPersistence.TopicPersistenceBuilder aTopicPersistence() {
    return TopicPersistence.builder()
        .name(TOPIC_NAME_MOCK)
        .description(DESCRIPTION_MOCK)
        .secondsToExpire(SECONDS_TO_EXPIRE_MOCK)
        .votes(new HashSet<>())
        .createdAt(new Date());
  }

  public static TopicVotesResultResponse aTopicVotesResultResponse() {
    Date closedTime = new Date();
    Date staredTime = new Date(closedTime.getTime() - SECONDS_TO_EXPIRE_MOCK * 1000);
    Map<String, Long> result = new HashMap<>(Map.of(
        VoteOptionEnum.YES.getDescription(), VOTE_INITIAL_COUNT_VALUE,
        VoteOptionEnum.NO.getDescription(), VOTE_INITIAL_COUNT_VALUE
    ));
    return TopicVotesResultResponse.builder()
        .startedAt(staredTime)
        .closedAt(staredTime)
        .voteAmountPerOption(result)
        .votesTotalAmount(result.size())
        .build();
  }

  public static TopicVotesCountResult aTopicVotesCountResult() {
    Date closedTime = new Date();
    Date staredTime = new Date(closedTime.getTime() - SECONDS_TO_EXPIRE_MOCK * 1000);
    Map<String, Long> result = new HashMap<>(Map.of(
        VoteOptionEnum.YES.getDescription(), VOTE_INITIAL_COUNT_VALUE,
        VoteOptionEnum.NO.getDescription(), VOTE_INITIAL_COUNT_VALUE
    ));
    return TopicVotesCountResult.builder()
        .startedAt(staredTime)
        .closedAt(staredTime)
        .voteAmountPerOption(result)
        .votesTotalAmount(result.size())
        .build();
  }

}

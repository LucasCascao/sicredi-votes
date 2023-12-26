package org.edu.sicredi.votes.mock;

import static org.edu.sicredi.votes.builder.TopicBuilder.VOTE_INITIAL_COUNT_VALUE;
import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.ASSOCIATE_CPF_1_MOCK;
import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.ASSOCIATE_CPF_2_MOCK;
import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.ASSOCIATE_CPF_3_MOCK;
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
import org.edu.sicredi.votes.domain.persistence.TopicVotesResultPersistence;
import org.edu.sicredi.votes.domain.persistence.TopicPersistence;
import org.edu.sicredi.votes.domain.persistence.VotePersistence;
import org.edu.sicredi.votes.domain.request.CreateTopicRequest;
import org.edu.sicredi.votes.domain.request.InitializeTopicRequest;
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

  public static TopicPersistence aCreatedTopicPersistenceWithoutTopicId() {
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
    Date closedTime = new Date();
    Date staredTime = new Date(closedTime.getTime() - SECONDS_TO_EXPIRE_MOCK * 1000);
    Set<VotePersistence> votes = aVoteSet();
    return aTopicPersistence()
        .status(TopicStatusEnum.FINISHED)
        .startedAt(staredTime)
        .closedAt(closedTime)
        .votes(votes)
        .build();
  }

  public static Set<VotePersistence> aVoteSet() {
    return Set.of(
        VotePersistence.builder().associateCpf(ASSOCIATE_CPF_1_MOCK)
            .vote(VoteOptionEnum.YES).build(),
        VotePersistence.builder().associateCpf(ASSOCIATE_CPF_2_MOCK)
            .vote(VoteOptionEnum.YES).build(),
        VotePersistence.builder().associateCpf(ASSOCIATE_CPF_3_MOCK)
            .vote(VoteOptionEnum.NO).build()
    );
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
        VoteOptionEnum.YES.getOptionName(), VOTE_INITIAL_COUNT_VALUE,
        VoteOptionEnum.NO.getOptionName(), VOTE_INITIAL_COUNT_VALUE
    ));
    return TopicVotesResultResponse.builder()
        .startedAt(staredTime)
        .closedAt(closedTime)
        .voteAmountPerOption(result)
        .votesTotalAmount(result.size())
        .build();
  }

  public static TopicVotesResultPersistence aTopicVotesResultPersistence() {
    Date closedTime = new Date();
    Date staredTime = new Date(closedTime.getTime() - SECONDS_TO_EXPIRE_MOCK * 1000);
    Map<String, Long> result = new HashMap<>();
    result.put(VoteOptionEnum.YES.getOptionName(), VOTE_INITIAL_COUNT_VALUE);
    result.put( VoteOptionEnum.NO.getOptionName(), VOTE_INITIAL_COUNT_VALUE);
    return TopicVotesResultPersistence.builder()
        .startedAt(staredTime)
        .closedAt(staredTime)
        .voteAmountPerOption(result)
        .votesTotalAmount(result.size())
        .build();
  }

  public static InitializeTopicRequest aInitializeTopicRequest() {
    return InitializeTopicRequest.builder()
        .topicId(TOPIC_ID_MOCK)
        .build();
  }

}

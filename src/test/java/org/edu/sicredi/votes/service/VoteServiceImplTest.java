package org.edu.sicredi.votes.service;

import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.ASSOCIATE_CPF_MOCK;
import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.TOPIC_ID_MOCK;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.edu.sicredi.votes.builder.VoteBuilder;
import org.edu.sicredi.votes.domain.enums.VoteOptionEnum;
import org.edu.sicredi.votes.domain.exception.BusinessException;
import org.edu.sicredi.votes.domain.persistence.TopicPersistence;
import org.edu.sicredi.votes.mock.BusinessExceptionMockBuilder;
import org.edu.sicredi.votes.mock.TopicMockBuilder;
import org.edu.sicredi.votes.provider.TopicProvider;
import org.edu.sicredi.votes.service.impl.VoteServiceImpl;
import org.edu.sicredi.votes.validator.TopicValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VoteServiceImplTest {

  @InjectMocks
  private VoteServiceImpl voteService;

  @Mock
  private TopicValidator topicValidator;

  @Mock
  private TopicProvider provider;

  @Mock
  private VoteBuilder voteBuilder;

  @Test
  void givenAValidVoteRequestWhenAssociateIsVotingThenCreateNewVote() {
    TopicPersistence topicPersistence = TopicMockBuilder.aInitializedTopicPersistence();
    TopicPersistence topicPersistenceWithVote = TopicMockBuilder.aInitializedTopicPersistenceWithVotes();
    given(provider.findTopicById(TOPIC_ID_MOCK)).willReturn(topicPersistence);
    willDoNothing().given(topicValidator).verifyTopicIsOpenedToVote(topicPersistence);
    given(voteBuilder.buildVotePersistence(ASSOCIATE_CPF_MOCK, TOPIC_ID_MOCK, VoteOptionEnum.YES))
        .willReturn(TopicMockBuilder.aYesVotePersistence());
    given(provider.saveTopic(topicPersistence))
        .willReturn(topicPersistenceWithVote);
    voteService.registerVote(ASSOCIATE_CPF_MOCK, TOPIC_ID_MOCK, VoteOptionEnum.YES);
    verify(provider, times(1)).findTopicById(TOPIC_ID_MOCK);
    verify(topicValidator, times(1)).verifyTopicIsOpenedToVote(topicPersistence);
    verify(voteBuilder, times(1)).buildVotePersistence(ASSOCIATE_CPF_MOCK, TOPIC_ID_MOCK,
        VoteOptionEnum.YES);
    verify(provider, times(1)).saveTopic(topicPersistence);
  }

}

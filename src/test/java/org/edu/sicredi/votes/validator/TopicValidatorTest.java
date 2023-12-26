package org.edu.sicredi.votes.validator;

import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.ASSOCIATE_CPF_1_MOCK;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.ASSOCIATE_HAS_ALREADY_VOTED_MESSAGE;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.TOPIC_CLOSED_FOR_VOTING;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.TOPIC_IS_NOT_ABLE_TO_RECEIVE_VOTES_MESSAGE;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.TOPIC_IS_NOT_FINALIZED_TO_GET_VOTES_RESULT_MESSAGE;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.TOPIC_NOT_OPENED_FOR_VOTING_MESSAGE;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.TOPIC_REMAINS_OPEN_FOR_VOTING_MESSAGE;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.TOPIC_WAS_INITIALIZED_MESSAGE;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Set;
import org.edu.sicredi.votes.domain.exception.BusinessException;
import org.edu.sicredi.votes.domain.persistence.TopicPersistence;
import org.edu.sicredi.votes.domain.persistence.VotePersistence;
import org.edu.sicredi.votes.mock.TopicMockBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class TopicValidatorTest {

  @InjectMocks
  private TopicValidator topicValidator;

  @Test
  void givenTopicPersistenceWithInitializedStatusWhenAssociateIsVotingThenReturnSuccessfully() {
    TopicPersistence topicPersistence = TopicMockBuilder.aInitializedTopicPersistence();
    topicValidator.verifyTopicIsOpenedToVote(topicPersistence);
  }

  @Test
  void givenTopicPersistenceWithCreatedStatusWhenAssociateIsVotingThenThrowBusinessException() {
    TopicPersistence topicPersistence = TopicMockBuilder.aCreatedTopicPersistence();
    try {
      topicValidator.verifyTopicIsOpenedToVote(topicPersistence);
      fail();
    } catch (BusinessException businessException) {
      Assertions.assertEquals(HttpStatus.BAD_REQUEST, businessException.getHttpStatus());
      Assertions.assertEquals(TOPIC_NOT_OPENED_FOR_VOTING_MESSAGE, businessException.getMessage());
    }
  }

  @Test
  void givenTopicPersistenceWithFinishedStatusWhenAssociateIsVotingThenThrowBusinessException() {
    TopicPersistence topicPersistence = TopicMockBuilder.aFinishedTopicPersistence();
    try {
      topicValidator.verifyTopicIsOpenedToVote(topicPersistence);
      fail();
    } catch (BusinessException businessException) {
      Assertions.assertEquals(HttpStatus.BAD_REQUEST, businessException.getHttpStatus());
      Assertions.assertEquals(TOPIC_CLOSED_FOR_VOTING, businessException.getMessage());
    }
  }

  @Test
  void givenTopicPersistenceWithUnknownStatusWhenAssociateIsVotingThenThrowBusinessException() {
    TopicPersistence topicPersistence = TopicMockBuilder.aCreatedTopicPersistence();
    topicPersistence.setStatus(null);
    try {
      topicValidator.verifyTopicIsOpenedToVote(topicPersistence);
      fail();
    } catch (BusinessException businessException) {
      Assertions.assertEquals(HttpStatus.BAD_REQUEST, businessException.getHttpStatus());
      Assertions.assertEquals(TOPIC_IS_NOT_ABLE_TO_RECEIVE_VOTES_MESSAGE,
          businessException.getMessage());
    }
  }

  @Test
  void givenAssociateCpfWhenAssociateIsVotingTwiceThenThrowBusinessException() {
    Set<VotePersistence> votes = TopicMockBuilder.aVoteSet();
    try {
      topicValidator.verifyIfAssociateCpfHasVoted(ASSOCIATE_CPF_1_MOCK, votes);
      fail();
    } catch (BusinessException businessException) {
      Assertions.assertEquals(HttpStatus.BAD_REQUEST, businessException.getHttpStatus());
      Assertions.assertEquals(ASSOCIATE_HAS_ALREADY_VOTED_MESSAGE, businessException.getMessage());
    }
  }

  @Test
  void givenATopicWithCreatedStatusWhenGettingVotesResultThenThrowBusinessException() {
    TopicPersistence topicPersistence = TopicMockBuilder.aCreatedTopicPersistence();
    try {
      topicValidator.verifyTopicVotingIsFinalized(topicPersistence);
      fail();
    } catch (BusinessException businessException) {
      Assertions.assertEquals(HttpStatus.BAD_REQUEST, businessException.getHttpStatus());
      Assertions.assertEquals(TOPIC_NOT_OPENED_FOR_VOTING_MESSAGE, businessException.getMessage());
    }
  }

  @Test
  void givenATopicWithInitializedStatusWhenGettingVotesResultThenThrowBusinessException() {
    TopicPersistence topicPersistence = TopicMockBuilder.aInitializedTopicPersistence();
    try {
      topicValidator.verifyTopicVotingIsFinalized(topicPersistence);
      fail();
    } catch (BusinessException businessException) {
      Assertions.assertEquals(HttpStatus.BAD_REQUEST, businessException.getHttpStatus());
      Assertions.assertEquals(TOPIC_REMAINS_OPEN_FOR_VOTING_MESSAGE,
          businessException.getMessage());
    }
  }

  @Test
  void givenATopicWithUnknownStatusWhenGettingVotesResultThenThrowBusinessException() {
    TopicPersistence topicPersistence = TopicMockBuilder.aCreatedTopicPersistence();
    topicPersistence.setStatus(null);
    try {
      topicValidator.verifyTopicVotingIsFinalized(topicPersistence);
      fail();
    } catch (BusinessException businessException) {
      Assertions.assertEquals(HttpStatus.BAD_REQUEST, businessException.getHttpStatus());
      Assertions.assertEquals(TOPIC_IS_NOT_FINALIZED_TO_GET_VOTES_RESULT_MESSAGE,
          businessException.getMessage());
    }
  }

  @Test
  void givenTopicWithStatusDifferentFromCreatedWhenInitializingTopicVotingThenThrowBusinessException() {
    TopicPersistence topicPersistence = TopicMockBuilder.aInitializedTopicPersistence();
    try {
      topicValidator.verifyTopicIsAbleToBeInitialized(topicPersistence);
      fail();
    } catch (BusinessException businessException) {
      Assertions.assertEquals(HttpStatus.BAD_REQUEST, businessException.getHttpStatus());
      Assertions.assertEquals(TOPIC_WAS_INITIALIZED_MESSAGE,
          businessException.getMessage());
    }
  }

}

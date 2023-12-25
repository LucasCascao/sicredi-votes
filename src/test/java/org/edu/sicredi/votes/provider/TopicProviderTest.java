package org.edu.sicredi.votes.provider;

import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.TOPIC_ID_MOCK;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.TOPIC_NOT_FOUND_MESSAGE;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import org.edu.sicredi.votes.domain.exception.BusinessException;
import org.edu.sicredi.votes.domain.persistence.TopicPersistence;
import org.edu.sicredi.votes.mock.TopicMockBuilder;
import org.edu.sicredi.votes.repository.TopicRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
public class TopicProviderTest {

  @InjectMocks
  private TopicProvider topicProvider;
  @Mock
  private TopicRepository repository;

  @Test
  void givenAValidTopicPersistenceWhenSavingATopicThenCallRepositorySaveFunctionSuccessfully() {
    TopicPersistence newTopic = TopicMockBuilder.aCreatedTopicPersistenceWithoutTipicId();
    TopicPersistence newTopicWithId = TopicMockBuilder.aCreatedTopicPersistence();
    given(repository.save(newTopic)).willReturn(newTopicWithId);
    TopicPersistence createdTopic = topicProvider.saveTopic(newTopic);
    verify(repository, times(1)).save(newTopic);
    Assertions.assertNotNull(createdTopic);
    Assertions.assertNotNull(createdTopic.getId());
  }

  @Test
  void givenAValidTopicIdWhenFindingATopicThenCallRepositoryFindFunctionSuccessfully() {
    TopicPersistence topic = TopicMockBuilder.aCreatedTopicPersistence();
    given(repository.findById(TOPIC_ID_MOCK)).willReturn(Optional.of(topic));
    TopicPersistence foundTopic = topicProvider.findTopicById(TOPIC_ID_MOCK);
    verify(repository).findById(TOPIC_ID_MOCK);
    Assertions.assertNotNull(foundTopic);
  }

  @Test
  void givenAInvalidTopicIdWhenFindingATopicThenThrowANotFoundBusinessException() {
    given(repository.findById(TOPIC_ID_MOCK)).willReturn(Optional.empty());
    try{
      topicProvider.findTopicById(TOPIC_ID_MOCK);
      fail();
    } catch (BusinessException exception) {
      Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
      Assertions.assertEquals(TOPIC_NOT_FOUND_MESSAGE, exception.getMessage());
    }
  }

}

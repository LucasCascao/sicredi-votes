package org.edu.sicredi.votes.service;

import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.DESCRIPTION_MOCK;
import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.SECONDS_TO_EXPIRE_MOCK;
import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.TOPIC_NAME_MOCK;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.edu.sicredi.votes.builder.TopicBuilder;
import org.edu.sicredi.votes.domain.persistence.TopicPersistence;
import org.edu.sicredi.votes.mock.TopicMockBuilder;
import org.edu.sicredi.votes.provider.TopicProvider;
import org.edu.sicredi.votes.service.impl.TopicServiceImpl;
import org.edu.sicredi.votes.validator.TopicValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

  @Test
  void givenValidTopicDataWhenCreatingNewTopicThenReturnNewTopicId() {
    TopicPersistence topicPersistence = TopicMockBuilder.aCreatedTopicPersistenceWithoutTipicId();
    given(topicBuilder.buildTopicPersistence(TOPIC_NAME_MOCK, DESCRIPTION_MOCK,
        SECONDS_TO_EXPIRE_MOCK)).willReturn(topicPersistence);
    given(topicProvider.saveTopic(any(TopicPersistence.class)))
        .willReturn(topicPersistence);
    String topicId = topicService.createNewTopic(TOPIC_NAME_MOCK, DESCRIPTION_MOCK,
        SECONDS_TO_EXPIRE_MOCK);
    Assertions.assertEquals(topicPersistence.getId(), topicId);
  }



}

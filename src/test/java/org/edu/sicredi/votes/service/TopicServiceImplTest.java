package org.edu.sicredi.votes.service;

import org.edu.sicredi.votes.provider.TopicProvider;
import org.edu.sicredi.votes.service.impl.TopicServiceImpl;
import org.edu.sicredi.votes.validator.TopicValidator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
public class TopicServiceImplTest {

  @InjectMocks
  private TopicServiceImpl topicService;

  @MockBean
  private TopicProvider topicProvider;

  @MockBean
  private TopicValidator topicValidator;

  void givenWhenThen()
  {

  }

}

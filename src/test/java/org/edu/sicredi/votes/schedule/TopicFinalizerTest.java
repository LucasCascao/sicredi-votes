package org.edu.sicredi.votes.schedule;

import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.TOPIC_ID_MOCK;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.edu.sicredi.votes.service.TopicService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TopicFinalizerTest {

  @InjectMocks
  private TopicFinalizer topicFinalizer;

  @Mock
  private TopicService topicService;

  @Test
  void givenATopicFinalizeScheduleWhenTimeToVoteEndsThenCallFinalizeTopicVotingFunction() {
    willDoNothing().given(topicService).finalizeTopicVoting(TOPIC_ID_MOCK);
    topicFinalizer.setTopicIdScheduled(TOPIC_ID_MOCK);
    topicFinalizer.setTopicService(topicService);
    topicFinalizer.run();
    verify(topicService, times(1)).finalizeTopicVoting(TOPIC_ID_MOCK);
  }

}

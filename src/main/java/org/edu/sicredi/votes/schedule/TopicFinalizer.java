package org.edu.sicredi.votes.schedule;

import lombok.Builder;
import lombok.Setter;
import org.edu.sicredi.votes.service.TopicService;

@Setter
@Builder
public class TopicFinalizer implements Runnable {

  private String topicIdScheduled;
  private TopicService topicService;

  @Override
  public void run() {
    topicService.finalizeTopicVoting(topicIdScheduled);
  }
}

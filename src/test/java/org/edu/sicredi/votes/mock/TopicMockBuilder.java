package org.edu.sicredi.votes.mock;

import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.DECRIPTION_MOCK;
import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.NAME_MOCK;

import org.edu.sicredi.votes.domain.request.CreateTopicRequest;

public class TopicMockBuilder {
  private TopicMockBuilder(){}

  public static CreateTopicRequest aCreateTopicRequest() {
    return CreateTopicRequest.builder()
        .name(NAME_MOCK)
        .description(DECRIPTION_MOCK)
        .build();
  }

}

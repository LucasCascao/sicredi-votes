package org.edu.sicredi.votes.mock;

import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.ASSOCIATE_CPF_MOCK;
import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.NO_MOCK;
import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.TOPIC_ID_MOCK;

import org.edu.sicredi.votes.domain.request.RegisterVoteRequest;

public class VoteMockBuilder {

  private VoteMockBuilder() {}

  public static RegisterVoteRequest aRegisterVoteRequest() {
    return RegisterVoteRequest.builder()
        .associateCpf(ASSOCIATE_CPF_MOCK)
        .topicId(TOPIC_ID_MOCK)
        .vote(NO_MOCK)
        .build();
  }

}

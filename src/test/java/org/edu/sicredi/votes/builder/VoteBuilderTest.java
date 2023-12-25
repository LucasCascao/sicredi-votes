package org.edu.sicredi.votes.builder;

import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.ASSOCIATE_CPF_MOCK;
import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.TOPIC_ID_MOCK;

import org.edu.sicredi.votes.domain.enums.VoteOptionEnum;
import org.edu.sicredi.votes.domain.persistence.VotePersistence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VoteBuilderTest {

  @InjectMocks
  private VoteBuilder voteBuilder;

  @Test
  void givenAValidVoteDataWhenRegisteringNewVoteThenBuildVotePersistence() {
    VotePersistence votePersistence = voteBuilder.buildVotePersistence(ASSOCIATE_CPF_MOCK,
        TOPIC_ID_MOCK, VoteOptionEnum.YES);
    Assertions.assertNotNull(votePersistence);
    Assertions.assertEquals(ASSOCIATE_CPF_MOCK, votePersistence.getAssociateCpf());
    Assertions.assertEquals(TOPIC_ID_MOCK, votePersistence.getTopicId());
    Assertions.assertEquals(VoteOptionEnum.YES, votePersistence.getVote());
  }

}

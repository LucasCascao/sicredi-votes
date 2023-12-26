package org.edu.sicredi.votes.validator;

import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.ASSOCIATE_HAS_ALREADY_VOTED_MESSAGE;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.TOPIC_CLOSED_FOR_VOTING;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.TOPIC_IS_NOT_ABLE_TO_RECEIVE_VOTES_MESSAGE;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.TOPIC_IS_NOT_FINALIZED_TO_GET_VOTES_RESULT_MESSAGE;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.TOPIC_NOT_OPENED_FOR_VOTING_MESSAGE;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.TOPIC_REMAINS_OPEN_FOR_VOTING_MESSAGE;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.TOPIC_WAS_INITIALIZED_MESSAGE;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.edu.sicredi.votes.builder.BusinessExceptionBuilder;
import org.edu.sicredi.votes.domain.enums.TopicStatusEnum;
import org.edu.sicredi.votes.domain.persistence.TopicPersistence;
import org.edu.sicredi.votes.domain.persistence.VotePersistence;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TopicValidator {

  public void verifyTopicIsOpenedToVote(TopicPersistence topic) {
    if(!TopicStatusEnum.INITIALIZED.equals(topic.getStatus())){
      String message;
      if (TopicStatusEnum.CREATED.equals(topic.getStatus())) {
        message = TOPIC_NOT_OPENED_FOR_VOTING_MESSAGE;
      } else if (TopicStatusEnum.FINISHED.equals(topic.getStatus())) {
        message = TOPIC_CLOSED_FOR_VOTING;
      } else {
        message = TOPIC_IS_NOT_ABLE_TO_RECEIVE_VOTES_MESSAGE;
      }
      throw BusinessExceptionBuilder.buildBusinessException(
          HttpStatus.BAD_REQUEST,
          message
      );
    }
  }

  public void verifyIfAssociateCpfHasVoted(String cpf, Set<VotePersistence> votes) {
    Optional<VotePersistence> cpfFound = votes.stream()
        .filter(voteItem -> voteItem.getAssociateCpf().equalsIgnoreCase(cpf)).findAny();
    if (cpfFound.isPresent()) {
      throw BusinessExceptionBuilder.buildBusinessException(HttpStatus.BAD_REQUEST,
          ASSOCIATE_HAS_ALREADY_VOTED_MESSAGE);
    }
  }

  public void verifyTopicVotingIsFinalized(TopicPersistence topic) {
    if(!TopicStatusEnum.FINISHED.equals(topic.getStatus())){
      String message;
      if (TopicStatusEnum.CREATED.equals(topic.getStatus())) {
        message = TOPIC_NOT_OPENED_FOR_VOTING_MESSAGE;
      } else if (TopicStatusEnum.INITIALIZED.equals(topic.getStatus())) {
        message = TOPIC_REMAINS_OPEN_FOR_VOTING_MESSAGE;
      } else {
        message = TOPIC_IS_NOT_FINALIZED_TO_GET_VOTES_RESULT_MESSAGE;
      }
      throw BusinessExceptionBuilder.buildBusinessException(HttpStatus.BAD_REQUEST,
          message);
    }
  }

  public void verifyTopicIsAbleToBeInitialized(TopicPersistence topic) {
    if (!TopicStatusEnum.CREATED.equals(topic.getStatus())) {
      throw BusinessExceptionBuilder.buildBusinessException(HttpStatus.BAD_REQUEST,
          TOPIC_WAS_INITIALIZED_MESSAGE);
    }
  }

}

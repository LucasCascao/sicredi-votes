package org.edu.sicredi.votes.validator;

import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.ASSOCIATE_HAS_ALREADY_VOTED_MESSAGE;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.TOPIC_REMAINS_OPEN_FOR_VOTING_MESSAGE;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.TOPIC_WAS_INITIALIZED_MESSAGE;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.TOPIC_CLOSED_FOR_VOTING;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.TOPIC_NOT_OPENED_FOR_VOTING_MESSAGE;

import java.util.Date;
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
    if (TopicStatusEnum.CREATED.equals(topic.getStatus())) {
      throw BusinessExceptionBuilder.buildBusinessException(HttpStatus.BAD_REQUEST,
          TOPIC_NOT_OPENED_FOR_VOTING_MESSAGE);
    } else if (TopicStatusEnum.FINISHED.equals(topic.getStatus())) {
      throw BusinessExceptionBuilder.buildBusinessException(HttpStatus.BAD_REQUEST,
          TOPIC_CLOSED_FOR_VOTING);
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
    if (topic.getStatus().equals(TopicStatusEnum.CREATED)) {
      throw BusinessExceptionBuilder.buildBusinessException(HttpStatus.BAD_REQUEST,
          TOPIC_NOT_OPENED_FOR_VOTING_MESSAGE);
    } else if (topic.getStatus().equals(TopicStatusEnum.INITIALIZED)) {
      throw BusinessExceptionBuilder.buildBusinessException(HttpStatus.BAD_REQUEST,
          TOPIC_REMAINS_OPEN_FOR_VOTING_MESSAGE);
    }
  }

  public void verifyTopicIsAbleToBeInitialized(TopicPersistence topic) {
    if (Objects.nonNull(topic.getStartedAt())) {
      throw BusinessExceptionBuilder.buildBusinessException(HttpStatus.BAD_REQUEST,
          TOPIC_WAS_INITIALIZED_MESSAGE);
    }
  }

}

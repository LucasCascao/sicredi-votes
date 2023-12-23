package org.edu.sicredi.votes.validator;

import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.ASSOCIATE_HAS_ALREADY_VOTED_MESSAGE;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.DEBATE_ITEM_REMAINS_OPEN_FOR_VOTING_MESSAGE;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.DEBATE_ITEM_WAS_OPENED_MESSAGE;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.ITEM_DEBATE_CLOSED_FOR_VOTING;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.ITEM_DEBATE_NOT_OPENED_FOR_VOTING_MESSAGE;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.edu.sicredi.votes.builder.BusinessExceptionBuilder;
import org.edu.sicredi.votes.domain.persistence.TopicPersistence;
import org.edu.sicredi.votes.domain.persistence.VotePersistence;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TopicValidator {

  public void verifyTopicIsOpenedToVote(TopicPersistence debateItem) {
    if (Objects.isNull(debateItem.getStartedAt())) {
      throw BusinessExceptionBuilder.buildBusinessException(HttpStatus.BAD_REQUEST,
          ITEM_DEBATE_NOT_OPENED_FOR_VOTING_MESSAGE);
    } else if (debateItem.getClosedAt().before(new Date())) {
      throw BusinessExceptionBuilder.buildBusinessException(HttpStatus.BAD_REQUEST,
          ITEM_DEBATE_CLOSED_FOR_VOTING);
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

  public void verifyIfTopicIsFinished(TopicPersistence debateItem) {
    if (Objects.isNull(debateItem.getStartedAt())) {
      throw BusinessExceptionBuilder.buildBusinessException(HttpStatus.BAD_REQUEST,
          ITEM_DEBATE_NOT_OPENED_FOR_VOTING_MESSAGE);
    }
    if (debateItem.getClosedAt().after(new Date())) {
      throw BusinessExceptionBuilder.buildBusinessException(HttpStatus.BAD_REQUEST,
          DEBATE_ITEM_REMAINS_OPEN_FOR_VOTING_MESSAGE);
    }
  }

  public void verifyIfTopicIsOpened(TopicPersistence topic) {
    if (Objects.nonNull(topic.getStartedAt())) {
      throw BusinessExceptionBuilder.buildBusinessException(HttpStatus.BAD_REQUEST,
          DEBATE_ITEM_WAS_OPENED_MESSAGE);
    }
  }

}

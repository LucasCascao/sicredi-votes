package org.edu.sicredi.votes.provider;

import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.TOPIC_NOT_FOUND_MESSAGE;
import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.TOPIC_RESULT_NOT_FOUND_MESSAGE;

import lombok.RequiredArgsConstructor;
import org.edu.sicredi.votes.builder.BusinessExceptionBuilder;
import org.edu.sicredi.votes.domain.persistence.TopicPersistence;
import org.edu.sicredi.votes.domain.persistence.TopicVotesResultPersistence;
import org.edu.sicredi.votes.repository.TopicRepository;
import org.edu.sicredi.votes.repository.TopicVotesResultRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicProvider {

  private final TopicRepository topicRepository;
  private final TopicVotesResultRepository topicVotesResultRepository;

  public TopicPersistence saveTopic(TopicPersistence topic) {
    return topicRepository.save(topic);
  }

  public TopicPersistence findTopicById(String topicId) {
    return topicRepository.findById(topicId).orElseThrow(
        () -> BusinessExceptionBuilder.buildBusinessException(HttpStatus.NOT_FOUND,
            TOPIC_NOT_FOUND_MESSAGE)
    );
  }

  public void saveTopicVotingResult(TopicVotesResultPersistence topicVotesResult) {
    topicVotesResultRepository.save(topicVotesResult);
  }

  public TopicVotesResultPersistence findVotingResultByTopicId(String topicId) {
    return topicVotesResultRepository.findByTopicId(topicId).orElseThrow(
        () -> BusinessExceptionBuilder.buildBusinessException(HttpStatus.NOT_FOUND,
            TOPIC_RESULT_NOT_FOUND_MESSAGE)
    );
  }


}

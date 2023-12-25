package org.edu.sicredi.votes.provider;

import static org.edu.sicredi.votes.domain.constants.LogMessagesConstant.TOPIC_NOT_FOUND_MESSAGE;

import lombok.RequiredArgsConstructor;
import org.edu.sicredi.votes.builder.BusinessExceptionBuilder;
import org.edu.sicredi.votes.domain.persistence.TopicPersistence;
import org.edu.sicredi.votes.repository.TopicRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicProvider {

  private final TopicRepository repository;

  public TopicPersistence saveTopic(TopicPersistence topic) {
    return repository.save(topic);
  }

  public TopicPersistence findTopicById(String topicId) {
    return repository.findById(topicId).orElseThrow(
        () -> BusinessExceptionBuilder.buildBusinessException(HttpStatus.NOT_FOUND,
            TOPIC_NOT_FOUND_MESSAGE)
    );
  }


}

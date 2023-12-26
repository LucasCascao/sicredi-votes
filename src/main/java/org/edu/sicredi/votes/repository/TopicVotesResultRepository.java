package org.edu.sicredi.votes.repository;

import java.util.Optional;
import org.edu.sicredi.votes.domain.persistence.TopicVotesResultPersistence;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TopicVotesResultRepository extends MongoRepository<TopicVotesResultPersistence, String> {
  Optional<TopicVotesResultPersistence> findByTopicId(String topicId);
}

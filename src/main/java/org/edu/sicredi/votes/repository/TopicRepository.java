package org.edu.sicredi.votes.repository;

import org.edu.sicredi.votes.domain.persistence.TopicPersistence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends MongoRepository<TopicPersistence, String> {

}

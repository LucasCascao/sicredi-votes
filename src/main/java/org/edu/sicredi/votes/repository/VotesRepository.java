package org.edu.sicredi.votes.repository;

import org.edu.sicredi.votes.domain.persistence.VotePersistence;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VotesRepository extends MongoRepository<VotePersistence, String> {

}

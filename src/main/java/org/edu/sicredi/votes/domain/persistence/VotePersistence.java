package org.edu.sicredi.votes.domain.persistence;

import lombok.Builder;
import lombok.Data;
import org.edu.sicredi.votes.domain.enums.VoteOptionEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
public class VotePersistence {
  private String id;
  private String associateCpf;
  private String topicId;
  private VoteOptionEnum vote;
}

package org.edu.sicredi.votes.domain.persistence;

import java.util.Date;
import java.util.Set;
import lombok.Builder;
import lombok.Data;
import org.edu.sicredi.votes.domain.enums.TopicStatusEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("Topics")
public class TopicPersistence {
  @Id
  private String id;
  private String name;
  private String description;
  private TopicStatusEnum status;
  private Date createdAt;
  private Date startedAt;
  private Date closedAt;
  private Long secondsToExpire;
  private Set<VotePersistence> votes;
}

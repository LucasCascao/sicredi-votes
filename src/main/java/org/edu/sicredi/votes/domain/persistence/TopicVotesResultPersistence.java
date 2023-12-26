package org.edu.sicredi.votes.domain.persistence;

import java.util.Date;
import java.util.Map;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("TopicVotesResult")
public class TopicVotesResultPersistence {
  @Id
  private String id;
  private String topicId;
  private Map<String, Long> voteAmountPerOption;
  private long votesTotalAmount;
  private Date startedAt;
  private Date closedAt;
}

package org.edu.sicredi.votes.domain.model;

import java.util.Date;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopicVotesCountResult {
  private Map<String, Long> voteAmountPerOption;
  private long votesTotalAmount;
  private Date startedAt;
  private Date closedAt;
}

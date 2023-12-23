package org.edu.sicredi.votes.domain.response;

import java.util.Date;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopicVotesCountResultResponse {
  private Map<String, Long> voteAmountPerOption;
  private long totalAmount;
  private Date startedAt;
  private Date closedAt;
}

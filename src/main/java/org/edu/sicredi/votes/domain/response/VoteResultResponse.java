package org.edu.sicredi.votes.domain.response;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VoteResultResponse {
  private long yesVotesAmount;
  private long noVotesAmount;
  private Date startedAt;
  private Date closedAt;
}

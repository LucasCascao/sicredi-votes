package org.edu.sicredi.votes.domain.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogMessagesConstant {
  public static final String TOPIC_VOTES_COUNTED_SUCCESSFULLY_MESSAGE = "Topic votes were counted successfully . [debateItemId = {}]";
  public static final String TOPIC_CREATED_SUCCESSFULLY_MESSAGE = "Topic created successfully. [debateItemName = {}, debateItemId = {}]";
  public static final String TOPIC_OPENED_TO_RECEIVE_VOTES_MESSAGE = "Topic was opened to receive votes successfully. [debateItemId = {}]";
  public static final String VOTE_REGISTERED_SUCCESSFULLY_MESSAGE = "Vote was registered successfully. [associateCpf = {}, debateItemId = {}, voteOption = {}]";
  public static final String DEBATE_ITEM_NOT_FOUND_MESSAGE = "Debate item not found";
  public static final String DEBATE_ITEM_WAS_OPENED_MESSAGE = "Debate item was previously opened";
  public static final String ITEM_DEBATE_NOT_OPENED_FOR_VOTING_MESSAGE = "Item under debate is not open for voting";
  public static final String ASSOCIATE_HAS_ALREADY_VOTED_MESSAGE = "Associate has already voted";
  public static final String ITEM_DEBATE_CLOSED_FOR_VOTING = "Item under debate is now closed for voting";
  public static final String DEBATE_ITEM_REMAINS_OPEN_FOR_VOTING_MESSAGE = "Debate item remains open for voting";
  public static final String UNEXPECTED_ERROR_MESSAGE = "An unexpected error appeared the system processing";
}

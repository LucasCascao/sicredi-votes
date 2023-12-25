package org.edu.sicredi.votes.domain.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogMessagesConstant {
  public static final String TOPIC_VOTES_COUNTED_SUCCESSFULLY_MESSAGE = "Topic votes were counted successfully . [topicId = {}]";
  public static final String TOPIC_CREATED_SUCCESSFULLY_MESSAGE = "Topic created successfully. [debateItemName = {}, topicId = {}]";
  public static final String TOPIC_OPENED_TO_RECEIVE_VOTES_MESSAGE = "Topic was opened to receive votes successfully. [topicId = {}]";
  public static final String VOTE_REGISTERED_SUCCESSFULLY_MESSAGE = "Vote was registered successfully. [associateCpf = {}, topicId = {}, voteOption = {}]";
  public static final String TOPIC_NOT_FOUND_MESSAGE = "Topic not found";
  public static final String TOPIC_WAS_INITIALIZED_MESSAGE = "This topic was previously opened";
  public static final String TOPIC_NOT_OPENED_FOR_VOTING_MESSAGE = "This topic is not open for voting";
  public static final String ASSOCIATE_HAS_ALREADY_VOTED_MESSAGE = "Associate has already voted";
  public static final String TOPIC_CLOSED_FOR_VOTING = "This topic is now closed for voting";
  public static final String TOPIC_REMAINS_OPEN_FOR_VOTING_MESSAGE = "This topic remains open for voting";
  public static final String UNEXPECTED_ERROR_MESSAGE = "An unexpected error appeared the system processing";
  public static final String TOPIC_FINALIZED_MESSAGE = "Topic {} finalized";
  public static final String BODY_CONTENT_HAS_ERROR_MESSAGE = "The body content has some error";
}

package org.edu.sicredi.votes.service;

import org.edu.sicredi.votes.domain.model.TopicVotesCountResult;

public interface TopicService {

  String createNewTopic(String topicName, String description, long secondsToExpire);

  void startTopicVoting(String topicId);
  void finalizeTopicVoting(String topicId);

  TopicVotesCountResult countVotesByTopic(String topicId);

}

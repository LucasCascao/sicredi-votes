package org.edu.sicredi.votes.controller.v1;

import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.TOPIC_ID_MOCK;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.edu.sicredi.votes.builder.TopicBuilder;
import org.edu.sicredi.votes.domain.persistence.TopicVotesResultPersistence;
import org.edu.sicredi.votes.domain.request.CreateTopicRequest;
import org.edu.sicredi.votes.domain.response.CreateTopicResponse;
import org.edu.sicredi.votes.domain.response.TopicVotesResultResponse;
import org.edu.sicredi.votes.mock.TopicMockBuilder;
import org.edu.sicredi.votes.service.TopicService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class TopicControllerTest {

  public static final String TOPIC_V1_PATH = "/v1/topic";
  public static final String TOPIC_ID_PATH_VARIABLE = "/{topicId}";
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private TopicService service;

  @MockBean
  private TopicBuilder topicBuilder;

  @Test
  void givenAValidCreateTopicRequestWhenCreatingANewTopicThenReturn201AndTopicId()
      throws Exception {
    CreateTopicRequest request = TopicMockBuilder.aCreateTopicRequest();
    given(service.createNewTopic(request.getName(), request.getDescription(),
        request.getSecondsToExpire())).willReturn(TOPIC_ID_MOCK);
    MvcResult mvcResult = mockMvc.perform(post(TOPIC_V1_PATH)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andReturn();
    String responseBody = mvcResult.getResponse().getContentAsString();
    CreateTopicResponse createTopicResponse =
        objectMapper.readValue(responseBody, CreateTopicResponse.class);
    Assertions.assertNotNull(createTopicResponse.getTopicId());
    Assertions.assertEquals(TOPIC_ID_MOCK, createTopicResponse.getTopicId());
  }

  @Test
  void givenAInitializeTopicRequestWhenInitializeVotingThenReturn204NoContent()
      throws Exception {
    CreateTopicRequest request = TopicMockBuilder.aCreateTopicRequest();
    willDoNothing().given(service).startTopicVoting(TOPIC_ID_MOCK);
    mockMvc.perform(
            post(TOPIC_V1_PATH.concat(TOPIC_ID_PATH_VARIABLE), TOPIC_ID_MOCK)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isNoContent());
    verify(service).startTopicVoting(TOPIC_ID_MOCK);
  }

  @Test
  void givenAGetTopicVotingResultRequestWhenVotingWasFinalizedThenReturn200AndTopicVotesResultResponse()
      throws Exception {
    CreateTopicRequest request = TopicMockBuilder.aCreateTopicRequest();
    TopicVotesResultPersistence topicVotesResult = TopicMockBuilder.aTopicVotesResultPersistence();
    TopicVotesResultResponse topicVotesResultResponse = TopicMockBuilder.aTopicVotesResultResponse();
    given(service.findTopicVotingResultByTopicId(TOPIC_ID_MOCK)).willReturn(topicVotesResult);
    given(topicBuilder.buildTopicVotesResultResponse(topicVotesResult))
        .willReturn(topicVotesResultResponse);
    mockMvc.perform(
            get(TOPIC_V1_PATH.concat(TOPIC_ID_PATH_VARIABLE), TOPIC_ID_MOCK)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk());
//    verify(service).startTopicVoting(TOPIC_ID_MOCK);
//    verify(topicBuilder).buildTopicVotesResultResponse(topicVotesCountResult);
  }

}

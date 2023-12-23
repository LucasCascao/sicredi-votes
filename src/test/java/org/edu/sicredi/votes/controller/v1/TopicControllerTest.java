package org.edu.sicredi.votes.controller.v1;

import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.TOPIC_ID_MOCK;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.edu.sicredi.votes.domain.request.CreateTopicRequest;
import org.edu.sicredi.votes.domain.response.CreateTopicResponse;
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
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private TopicService service;

  @Test
  void givenAValidTopicRequestWhenCreatingANewTopicThenReturn201AndTopicId()
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

}

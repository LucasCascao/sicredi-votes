package org.edu.sicredi.votes.controller.v1;

import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.ASSOCIATE_CPF_MOCK;
import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.EMPTY_STRING;
import static org.edu.sicredi.votes.constants.FieldValuesMockConstants.TOPIC_ID_MOCK;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.edu.sicredi.votes.domain.enums.VoteOptionEnum;
import org.edu.sicredi.votes.domain.request.RegisterVoteRequest;
import org.edu.sicredi.votes.mock.VoteMockBuilder;
import org.edu.sicredi.votes.service.TopicService;
import org.edu.sicredi.votes.service.VoteService;
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
public class VotesControllerTest {

  public static final String VOTES_V1_PATH = "/v1/votes";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private VoteService service;

  @Test
  void givenAVoteRequestWhenATopicIsOpenedToVoteThenReturn201NoContent()
      throws Exception {
    RegisterVoteRequest request = VoteMockBuilder.aRegisterVoteRequest();
    willDoNothing().given(service).registerVote(request.getAssociateCpf(), request.getTopicId(),
        VoteOptionEnum.get(request.getVote()));
    MvcResult mvcResult = mockMvc.perform(post(VOTES_V1_PATH)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andReturn();
    Assertions.assertNotNull(mvcResult.getResponse());
    Assertions.assertEquals(EMPTY_STRING, mvcResult.getResponse().getContentAsString());
  }
}

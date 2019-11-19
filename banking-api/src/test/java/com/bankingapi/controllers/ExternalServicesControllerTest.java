package com.bankingapi.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bankcore.apicommon.configuration.mapping.Mapper;
import com.bankcore.apicommon.service.BankingService;
import com.bankingapi.utils.ResourceUtility;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class ExternalServicesControllerTest {

  private static final String EXTERNAL_SERVICE_VIEW_MODEL_VALID_DEBIT_JSON
      = ResourceUtility.generateStringFromResource("requestJson/ExternalServiceViewModel_Valid_Debit.json");

  private static final String EXTERNAL_SERVICE_VIEW_MODEL_VALID_CHECK_JSON
      = ResourceUtility.generateStringFromResource("requestJson/ExternalServiceViewModel_Valid_Check.json");

  @Mock
  private Mapper mapperMock;
  @Mock
  private BankingService bankingServiceMock;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(
        new ExternalServicesController(mapperMock, bankingServiceMock))
        .build();
  }

  @Test
  public void processExternalService_With_Debit_Returns_OK() throws Exception {
    mockMvc.perform(put("/external/")
        .content(EXTERNAL_SERVICE_VIEW_MODEL_VALID_DEBIT_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void processExternalService_With_Check_Returns_OK() throws Exception {
    mockMvc.perform(put("/external/")
        .content(EXTERNAL_SERVICE_VIEW_MODEL_VALID_CHECK_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

}

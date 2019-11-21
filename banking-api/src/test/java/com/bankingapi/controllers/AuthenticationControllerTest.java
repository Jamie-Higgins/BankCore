package com.bankingapi.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bankcore.apicommon.configuration.mapping.Mapper;
import com.bankcore.apicommon.service.AuthenticationService;
import com.bankcore.apicommon.service.BankingService;
import com.bankingapi.utils.ResourceUtility;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationControllerTest {

  private static final String REGISTER_BANK_ACCOUNT_VIEW_MODEL_VALID_JSON
      = ResourceUtility.generateStringFromResource("requestJson/CreateBankAccountViewModel_Valid.json");

  private static final String LOGIN_TO_BANK_ACCOUNT_VIEW_MODEL_VALID_JSON
      = ResourceUtility.generateStringFromResource("requestJson/LoginViewModel_Valid.json");

  @Mock
  private Mapper mapperMock;
  @Mock
  private BankingService bankingServiceMock;
  @Mock
  private AuthenticationService authenticationService;
  @Mock
  private PasswordEncoder passwordEncoder;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(
        new AuthenticationController(mapperMock, bankingServiceMock, authenticationService, passwordEncoder))
        .build();
  }

  @Test
  public void registerBankAccount_Returns_Created() throws Exception {
    mockMvc.perform(post("/register/")
        .content(REGISTER_BANK_ACCOUNT_VIEW_MODEL_VALID_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  public void loginToBankAccount_Returns_Created() throws Exception {
    mockMvc.perform(post("/login/")
        .content(LOGIN_TO_BANK_ACCOUNT_VIEW_MODEL_VALID_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

}

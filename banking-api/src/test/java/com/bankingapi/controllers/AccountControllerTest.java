package com.bankingapi.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bankcore.apicommon.configuration.mapping.Mapper;
import com.bankcore.apicommon.configuration.security.jwt.JwtPrincipalViewModel;
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
public class AccountControllerTest {

  private static final String CREATE_BANK_ACCOUNT_VIEW_MODEL_VALID_JSON
      = ResourceUtility.generateStringFromResource("requestJson/CreateBankAccountViewModel_Valid.json");

  private static final String DEPOSIT_TRANSACTION_VIEW_MODEL_VALID
      = ResourceUtility.generateStringFromResource("requestJson/DepositTransactionViewModel_Valid.json");

  private static final String WITHDRAW_TRANSACTION_VIEW_MODEL_VALID
      = ResourceUtility.generateStringFromResource("requestJson/WithdrawTransactionViewModel_Valid.json");

  @Mock
  private Mapper mapperMock;
  @Mock
  private BankingService bankingServiceMock;
  @Mock
  private JwtPrincipalViewModel jwtPrincipalViewModelMock;


  private MockMvc mockMvc;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(
        new AccountController(mapperMock, bankingServiceMock, jwtPrincipalViewModelMock))
        .build();
  }

  @Test
  public void closeBankAccount_Returns_OK() throws Exception {
    mockMvc.perform(delete("/account/")
        .content(CREATE_BANK_ACCOUNT_VIEW_MODEL_VALID_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void depositFunds_Returns_OK() throws Exception {
    mockMvc.perform(put("/account/deposit/")
        .content(DEPOSIT_TRANSACTION_VIEW_MODEL_VALID)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void withdrawFunds_Returns_OK() throws Exception {
    mockMvc.perform(put("/account/withdraw/")
        .content(WITHDRAW_TRANSACTION_VIEW_MODEL_VALID)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void getCurrentBalance_Returns_OK() throws Exception {
    mockMvc.perform(get("/account/currentBalance")
        .content(WITHDRAW_TRANSACTION_VIEW_MODEL_VALID)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void getAccountOverview_Returns_OK() throws Exception {
    mockMvc.perform(get("/account/overview/")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

}

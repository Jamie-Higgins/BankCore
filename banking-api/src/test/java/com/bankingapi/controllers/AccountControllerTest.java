package com.bankingapi.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    /*  @Mock
    private JwtPrincipalViewModel jwtPrincipalViewModelMock;*/

  private MockMvc mockMvc;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(
        new AccountController(mapperMock, bankingServiceMock))
        .build();
  }

  @Test
  public void createBankAccount_Returns_Created() throws Exception {
    mockMvc.perform(post("/account/")
        .content(CREATE_BANK_ACCOUNT_VIEW_MODEL_VALID_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  public void closeBankAccount_Returns_OK() throws Exception {
    mockMvc.perform(delete("/account/1234")
        .content(CREATE_BANK_ACCOUNT_VIEW_MODEL_VALID_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void depositFunds_Returns_OK() throws Exception {
    mockMvc.perform(put("/account/deposit/1234")
        .content(DEPOSIT_TRANSACTION_VIEW_MODEL_VALID)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void withdrawFunds_Returns_OK() throws Exception {
    mockMvc.perform(put("/account/withdraw/1234")
        .content(WITHDRAW_TRANSACTION_VIEW_MODEL_VALID)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void getCurrentBalance_Returns_OK() throws Exception {
    mockMvc.perform(get("/account/1234")
        .content(WITHDRAW_TRANSACTION_VIEW_MODEL_VALID)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void getAccountOverview_Returns_OK() throws Exception {
    mockMvc.perform(get("/account/overview/1234")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

}

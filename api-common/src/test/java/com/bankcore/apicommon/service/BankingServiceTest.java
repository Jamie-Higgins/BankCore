package com.bankcore.apicommon.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bankcore.apicommon.configuration.exception.BadRequestException;
import com.bankcore.apicommon.configuration.mapping.Mapper;
import com.bankcore.apicommon.dto.AccountDTO;
import com.bankcore.apicommon.dto.CreateBankAccountDTO;
import com.bankcore.apicommon.dto.ExternalServiceDTO;
import com.bankcore.apicommon.dto.TransactionDTO;
import com.bankcore.apicommon.entity.Account;
import com.bankcore.apicommon.entity.Transaction;
import com.bankcore.apicommon.enums.TransactionType;
import com.bankcore.apicommon.repository.AccountRepository;
import com.bankcore.apicommon.repository.TransactionRepository;
import com.flextrade.jfixture.JFixture;
import com.flextrade.jfixture.rules.FixtureRule;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BankingServiceTest {

  @Rule
  public FixtureRule fr = FixtureRule.initFixtures();
  private Account accountFixture;
  private AccountDTO accountDTOFixture;

  @Mock
  private Mapper mapperMock;
  @Mock
  private AccountRepository accountRepositoryMock;
  @Mock
  private TransactionRepository transactionRepositoryMock;

  @InjectMocks
  private BankingService classUnderTest;

  @Before
  public void setUp() {

    JFixture fixture = new JFixture();
    fixture.customise().circularDependencyBehaviour().omitSpecimen();

    accountFixture = fixture.create(Account.class);
    accountDTOFixture = fixture.create(AccountDTO.class);
  }

  @Test
  public void createBankAccount_Creates_New_Bank_Account() {
    final CreateBankAccountDTO createBankAccountDTO = CreateBankAccountDTO.builder().forename("Jamie").surname("Higgins").pin("1234").ssn("123456789").build();

    when(accountRepositoryMock.findFirstBySsn(anyString())).thenReturn(null);
    when(mapperMock.map(any(CreateBankAccountDTO.class), any())).thenReturn(accountFixture);
    when(mapperMock.map(any(Account.class), any())).thenReturn(accountDTOFixture);

    Assert.assertEquals(accountDTOFixture.getAccountNumber(), classUnderTest.createBankAccount(createBankAccountDTO).getAccountNumber());
    verify(accountRepositoryMock, times(1)).save(accountFixture);
  }

  @Test(expected = BadRequestException.class)
  public void createBankAccount_Does_Not_Create_New_Bank_Account_When_Account_Exists() {
    final CreateBankAccountDTO createBankAccountDTO = CreateBankAccountDTO.builder().forename("Jamie").surname("Higgins").pin("1234").ssn("123456789").build();

    when(accountRepositoryMock.findFirstBySsn(anyString())).thenReturn(accountFixture);

    classUnderTest.createBankAccount(createBankAccountDTO);
  }

  @Test
  public void closeBankAccount_Closes_Bank_Account() {

    classUnderTest.closeBankAccount(accountFixture.getAccountNumber());

    verify(accountRepositoryMock, times(1)).deleteByAccountNumber(accountFixture.getAccountNumber());
  }

  @Test
  public void depositFunds_Adds_Amount_Submitted_To_The_Accounts_Current_Balance() {

    final BigDecimal valueBeforeDeposit = accountFixture.getCurrentBalance();
    final TransactionDTO transactionDTO = TransactionDTO.builder().amount(BigDecimal.TEN).description("Test Deposit").build();

    when(mapperMock.map(any(TransactionDTO.class), any())).thenReturn(Transaction.builder().amount(transactionDTO.getAmount()).description(transactionDTO.getDescription()).build());
    when(accountRepositoryMock.findFirstByAccountNumber(accountFixture.getAccountNumber())).thenReturn(accountFixture);

    classUnderTest.depositFunds(accountFixture.getAccountNumber(), transactionDTO);

    Assert.assertEquals(accountFixture.getCurrentBalance().subtract(valueBeforeDeposit), BigDecimal.TEN);
    verify(transactionRepositoryMock, times(1)).save(any(Transaction.class));
    verify(accountRepositoryMock, times(1)).save(any(Account.class));
  }

  @Test
  public void withdrawFunds_Subtracts_Amount_Submitted_To_The_Accounts_Current_Balance() {

    final BigDecimal valueBeforeDeposit = accountFixture.getCurrentBalance();
    final TransactionDTO transactionDTO = TransactionDTO.builder().amount(BigDecimal.TEN).description("Test Withdrawal").build();

    when(mapperMock.map(any(TransactionDTO.class), any())).thenReturn(Transaction.builder().amount(transactionDTO.getAmount()).description(transactionDTO.getDescription()).build());
    when(accountRepositoryMock.findFirstByAccountNumber(accountFixture.getAccountNumber())).thenReturn(accountFixture);

    classUnderTest.withdrawFunds(accountFixture.getAccountNumber(), transactionDTO);

    Assert.assertEquals(valueBeforeDeposit.subtract(accountFixture.getCurrentBalance()), BigDecimal.TEN);
    verify(transactionRepositoryMock, times(1)).save(any(Transaction.class));
    verify(accountRepositoryMock, times(1)).save(any(Account.class));
  }

  @Test(expected = BadRequestException.class)
  public void withdrawFunds_Does_Not_Subtract_Amount_Submitted_To_The_Accounts_Current_Balance_When_Withdrawal_Amount_Is_Greater_Than_Current_Balance() {
    accountFixture.setCurrentBalance(BigDecimal.ONE);
    final TransactionDTO transactionDTO = TransactionDTO.builder().amount(BigDecimal.TEN).description("Test Withdrawal").build();

    when(accountRepositoryMock.findFirstByAccountNumber(accountFixture.getAccountNumber())).thenReturn(accountFixture);

    classUnderTest.withdrawFunds(accountFixture.getAccountNumber(), transactionDTO);
  }

  @Test
  public void getAccountOverview_Returns_Account_Overview_With_Latest_Transactions_Showing_Only_Latest_Five_Transactions() {
    when(accountRepositoryMock.findFirstByAccountNumber(accountFixture.getAccountNumber())).thenReturn(accountFixture);
    when(mapperMock.map(any(Account.class), any())).thenReturn(accountDTOFixture);

    final AccountDTO accountDTO = classUnderTest.getAccountOverview(accountFixture.getAccountNumber());

    Assert.assertTrue(accountDTO.getLastFiveTransactions().size() <= 5);
  }

  @Test
  public void getCurrentBalance_Returns_Account_Balance() {
    when(accountRepositoryMock.findFirstByAccountNumber(accountFixture.getAccountNumber())).thenReturn(accountFixture);

    Assert.assertEquals(classUnderTest.getCurrentBalance(accountFixture.getAccountNumber()), accountFixture.getCurrentBalance());
  }

  @Test
  public void processExternalServices_Returns_TransactionId_With_Successful_Debit_Transaction() {
    final BigDecimal valueBeforeDeposit = accountFixture.getCurrentBalance();

    final ExternalServiceDTO externalServiceDTO = ExternalServiceDTO.builder()
        .accountNumber("1234")
        .type(TransactionType.DEBIT.toString())
        .amount(BigDecimal.TEN)
        .description("Phone Bill")
        .build();

    when(accountRepositoryMock.findFirstByAccountNumber(externalServiceDTO.getAccountNumber())).thenReturn(accountFixture);
    when(mapperMock.map(any(ExternalServiceDTO.class), any()))
        .thenReturn(TransactionDTO.builder()
            .amount(externalServiceDTO.getAmount())
            .description(externalServiceDTO.getDescription())
            .build());
    when(mapperMock.map(any(TransactionDTO.class), any()))
        .thenReturn(Transaction.builder()
            .amount(externalServiceDTO.getAmount())
            .description(externalServiceDTO.getDescription())
            .build());

    classUnderTest.processExternalServices(externalServiceDTO);

    Assert.assertEquals(valueBeforeDeposit.subtract(accountFixture.getCurrentBalance()), BigDecimal.TEN);
    verify(transactionRepositoryMock, times(1)).save(any(Transaction.class));
    verify(accountRepositoryMock, times(1)).save(any(Account.class));

  }


  @Test(expected = BadRequestException.class)
  public void processExternalServices_Throws_Bad_Request_When_Sufficient_Funds_Availabile() {
    accountFixture.setCurrentBalance(BigDecimal.ONE);

    final ExternalServiceDTO externalServiceDTO = ExternalServiceDTO.builder()
        .accountNumber("1234")
        .type(TransactionType.DEBIT.toString())
        .amount(BigDecimal.TEN)
        .description("Phone Bill")
        .build();

    when(accountRepositoryMock.findFirstByAccountNumber(externalServiceDTO.getAccountNumber())).thenReturn(accountFixture);

    classUnderTest.processExternalServices(externalServiceDTO);
  }

  @Test
  public void processExternalServices_Returns_TransactionId_With_Successful_Check_Transaction() {
    final BigDecimal valueBeforeDeposit = accountFixture.getCurrentBalance();

    final ExternalServiceDTO externalServiceDTO = ExternalServiceDTO.builder()
        .accountNumber("1234")
        .type(TransactionType.CHECK.toString())
        .amount(BigDecimal.TEN)
        .description("Phone Bill")
        .build();

    when(accountRepositoryMock.findFirstByAccountNumber(externalServiceDTO.getAccountNumber())).thenReturn(accountFixture);
    when(mapperMock.map(any(ExternalServiceDTO.class), any()))
        .thenReturn(TransactionDTO.builder()
            .amount(externalServiceDTO.getAmount())
            .description(externalServiceDTO.getDescription())
            .build());
    when(mapperMock.map(any(TransactionDTO.class), any()))
        .thenReturn(Transaction.builder()
            .amount(externalServiceDTO.getAmount())
            .description(externalServiceDTO.getDescription())
            .build());

    classUnderTest.processExternalServices(externalServiceDTO);

    Assert.assertEquals(accountFixture.getCurrentBalance().subtract(valueBeforeDeposit), BigDecimal.TEN);
    verify(transactionRepositoryMock, times(1)).save(any(Transaction.class));
    verify(accountRepositoryMock, times(1)).save(any(Account.class));

  }
}

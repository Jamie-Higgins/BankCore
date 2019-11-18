package com.bankcore.apicommon.service;

import static org.mockito.Mockito.when;

import com.bankcore.apicommon.configuration.mapping.Mapper;
import com.bankcore.apicommon.dto.AccountDTO;
import com.bankcore.apicommon.entity.Account;
import com.bankcore.apicommon.repository.AccountRepository;
import com.bankcore.apicommon.repository.TransactionRepository;
import com.flextrade.jfixture.JFixture;
import com.flextrade.jfixture.rules.FixtureRule;
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
  public void getCurrentBalance_Returns_Account_Balance() {
    final String accountNumber = "1234-5678";

    when(accountRepositoryMock.findFirstByAccountNumber(accountNumber)).thenReturn(accountFixture);

    Assert.assertEquals(classUnderTest.getCurrentBalance(accountNumber), accountFixture.getCurrentBalance());
  }

  @Test
  public void getAccountOverview_Returns_Overview_Of_Account() {
    final String accountNumber = "1234-5678";

    when(accountRepositoryMock.findFirstByAccountNumber(accountNumber)).thenReturn(accountFixture);
    when(mapperMock.map(Account.class, AccountDTO.class)).thenReturn(accountDTOFixture);
    when(mapperMock.map(AccountDTO.class, Account.class)).thenReturn(accountFixture);

    Assert.assertEquals(classUnderTest.getAccountOverview(accountNumber), accountFixture);
  }


}

package com.bankcore.apicommon.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bankcore.apicommon.configuration.mapping.Mapper;
import com.bankcore.apicommon.dto.AccountDTO;
import com.bankcore.apicommon.entity.Account;
import com.bankcore.apicommon.repository.AccountRepository;
import com.flextrade.jfixture.JFixture;
import com.flextrade.jfixture.rules.FixtureRule;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

  @Rule
  public FixtureRule fr = FixtureRule.initFixtures();
  private Account accountFixture;
  private AccountDTO accountDTOFixture;

  @Mock
  private Mapper mapperMock;
  @Mock
  private AccountRepository accountRepositoryMock;
  @Mock
  private PasswordEncoder passwordEncoderMock;

  @InjectMocks
  private AccountService classUnderTest;

  @Before
  public void setUp() {

    JFixture fixture = new JFixture();
    fixture.customise().circularDependencyBehaviour().omitSpecimen();

    accountFixture = fixture.create(Account.class);
    accountDTOFixture = fixture.create(AccountDTO.class);
  }

  @Test
  public void createNewAccount_Creates_New_Account() {

    when(mapperMock.map(any(Account.class), any())).thenReturn(accountDTOFixture);
    when(mapperMock.map(any(AccountDTO.class), any())).thenReturn(accountFixture);
    when(accountRepositoryMock.save(any(Account.class))).thenReturn(accountFixture);

    Assert.assertNotNull(classUnderTest.createNewAccount(accountDTOFixture));
    verify(accountRepositoryMock, times(1)).save(any(Account.class));
  }

  @Test
  public void getAccountByAccountNumber_Returns_Account() {
    when(accountRepositoryMock.findByAccountNumber(anyString())).thenReturn(Optional.of(accountFixture));
    when(mapperMock.map(any(Account.class), any())).thenReturn(accountDTOFixture);

    Assert.assertEquals(classUnderTest.getAccountByAccountNumber(accountDTOFixture.getAccountNumber()).get().getAccountNumber(), (accountDTOFixture.getAccountNumber()));
  }

  @Test
  public void getAccountById_Returns_Account() {
    when(accountRepositoryMock.findById(anyInt())).thenReturn(Optional.of(accountFixture));
    when(mapperMock.map(any(Account.class), any())).thenReturn(accountDTOFixture);

    Assert.assertEquals(classUnderTest.getAccountById(accountDTOFixture.getId()).get().getAccountNumber(), (accountDTOFixture.getAccountNumber()));
  }

}

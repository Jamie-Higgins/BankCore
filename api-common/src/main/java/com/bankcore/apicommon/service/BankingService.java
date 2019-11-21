package com.bankcore.apicommon.service;

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
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BankingService {

  private final Mapper mapper;
  private final AccountRepository accountRepository;
  private final TransactionRepository transactionRepository;

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public String createBankAccount(final CreateBankAccountDTO createBankAccountDTO) {

    final Account newAccount = mapper.map(createBankAccountDTO, Account.class);
    newAccount.setCurrentBalance(BigDecimal.ZERO);
    newAccount.setAccountNumber(UUID.randomUUID().toString());
    accountRepository.save(newAccount);

    return newAccount.getAccountNumber();
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public void closeBankAccount(final String accountNumber) {
    accountRepository.deleteByAccountNumber(accountNumber);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public void depositFunds(final String accountNumber, final TransactionDTO transactionDTO) {
    final Account account = accountRepository.findFirstByAccountNumber(accountNumber);
    account.setCurrentBalance(account.getCurrentBalance().add(transactionDTO.getAmount()));

    transactionRepository.save(buildTransaction(transactionDTO, account, TransactionType.DEPOSIT));
    accountRepository.save(account);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public void withdrawFunds(final String accountNumber, final TransactionDTO transactionDTO) {
    final Account account = accountRepository.findFirstByAccountNumber(accountNumber);
    if (transactionDTO.getAmount().compareTo(account.getCurrentBalance()) == 1) {
      throw new BadRequestException("Insufficient Funds Available");
    } else {
      account.setCurrentBalance(account.getCurrentBalance().subtract(transactionDTO.getAmount()));

      transactionRepository.save(buildTransaction(transactionDTO, account, TransactionType.WITHDRAWAL));
      accountRepository.save(account);
    }
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public BigDecimal getCurrentBalance(final String accountNumber) {
    return accountRepository.findFirstByAccountNumber(accountNumber).getCurrentBalance();
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public AccountDTO getAccountOverview(final String accountNumber) {
    final AccountDTO accountDTO = mapper.map(accountRepository.findFirstByAccountNumber(accountNumber), AccountDTO.class);

    trimListTo5MostRecentTransactions(accountDTO); //TODO do this in DB to reduce unncessary data returned

    return accountDTO;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public String processExternalServices(final ExternalServiceDTO externalServiceDTO) {

    if (externalServiceDTO.getType().equals(TransactionType.DEBIT.toString())) {
      return processDebitTransaction(externalServiceDTO);
    } else if (externalServiceDTO.getType().equals(TransactionType.CHECK.toString())) {
      return processCheckTransaction(externalServiceDTO);
    } else {
      throw new BadRequestException("Invalid Transaction Type");
    }
  }


  private Transaction buildTransaction(final TransactionDTO transactionDTO, final Account account, final TransactionType transactionType) {
    final Transaction transaction = mapper.map(transactionDTO, Transaction.class);
    transaction.setDate(ZonedDateTime.now()); //TODO Move to mapper
    transaction.setType(transactionType.toString());
    transaction.setTransactionId(UUID.randomUUID().toString());
    transaction.setAccount(account);

    return transaction;
  }

  private void trimListTo5MostRecentTransactions(final AccountDTO accountDTO) { //TODO Do in DB
    accountDTO.getLastFiveTransactions().sort(Comparator.comparing(TransactionDTO::getDate));
    if (accountDTO.getLastFiveTransactions().size() >= 5) {
      accountDTO.setLastFiveTransactions(accountDTO.getLastFiveTransactions().subList(0, 5));
    }
  }

  private String processCheckTransaction(final ExternalServiceDTO externalServiceDTO) {
    final Account account = accountRepository.findFirstByAccountNumber(externalServiceDTO.getAccountNumber());
    account.setCurrentBalance(account.getCurrentBalance().add(externalServiceDTO.getAmount()));

    return saveExternalTransaction(externalServiceDTO, account, TransactionType.CHECK);
  }

  private String processDebitTransaction(final ExternalServiceDTO externalServiceDTO) {
    final Account account = accountRepository.findFirstByAccountNumber(externalServiceDTO.getAccountNumber());
    if (externalServiceDTO.getAmount().compareTo(account.getCurrentBalance()) == 1) {
      throw new BadRequestException("Insufficient Funds Available");
    } else {
      account.setCurrentBalance(account.getCurrentBalance().subtract(externalServiceDTO.getAmount()));
    }
    return saveExternalTransaction(externalServiceDTO, account, TransactionType.DEBIT);
  }

  private String saveExternalTransaction(final ExternalServiceDTO externalServiceDTO, final Account account, final TransactionType transactionType) {
    final Transaction transaction = buildTransaction(mapper.map(externalServiceDTO, TransactionDTO.class), account, transactionType);
    transactionRepository.save(transaction);
    accountRepository.save(account);
    return transaction.getTransactionId();
  }
}

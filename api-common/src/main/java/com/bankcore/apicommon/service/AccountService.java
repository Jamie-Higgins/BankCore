package com.bankcore.apicommon.service;

import com.bankcore.apicommon.configuration.mapping.Mapper;
import com.bankcore.apicommon.dto.AccountDTO;
import com.bankcore.apicommon.entity.Account;
import com.bankcore.apicommon.repository.AccountRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final Mapper mapper;
  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;

  public AccountDTO createNewAccount(AccountDTO newAccountDTO) {
    final Account account = mapper.map(newAccountDTO, Account.class);
    account.setPin(passwordEncoder.encode(newAccountDTO.getPin()));
    final Account newAccount = accountRepository.save(account);

    return mapper.map(newAccount, AccountDTO.class);
  }

  public Optional<AccountDTO> getAccountByAccountNumber(final String accountNumber) {
    return accountRepository.findByAccountNumber(accountNumber).map(account -> mapper.map(account, AccountDTO.class));
  }

  public Optional<AccountDTO> getAccountById(final int id) {
    return accountRepository.findById(id).map(account -> mapper.map(account, AccountDTO.class));
  }
}

package com.bankcore.apicommon.configuration.security;

import com.bankcore.apicommon.configuration.mapping.Mapper;
import com.bankcore.apicommon.dto.AccountDTO;
import com.bankcore.apicommon.service.AccountService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

  final AccountService accountService;
  final Mapper mapper;

  @Override
  public UserDetails loadUserByUsername(String accountNumber) {
    Optional<AccountDTO> account = accountService.getAccountByAccountNumber(accountNumber);
    return UserPrincipal.create(account.get());
  }

  public UserDetails loadUserById(int id) {
    Optional<AccountDTO> user = accountService.getAccountById(id);
    return UserPrincipal.create(user.get());
  }
}
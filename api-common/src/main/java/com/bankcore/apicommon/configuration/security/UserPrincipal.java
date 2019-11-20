package com.bankcore.apicommon.configuration.security;

import com.bankcore.apicommon.dto.AccountDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserPrincipal implements UserDetails {

  final private int id;

  final private String accountNumber; //Username

  final private String forename;

  final private String surname;

  @JsonIgnore
  final private String password; //PIN


  final private Collection<? extends GrantedAuthority> authorities;

  public static UserPrincipal create(AccountDTO account) {

    final List<GrantedAuthority> authorities = getUserRoles()
        .stream()
        .map(x -> new SimpleGrantedAuthority(x))
        .collect(Collectors.toList());

    return UserPrincipal.builder()
        .id(account.getId())
        .forename(account.getForename())
        .surname(account.getSurname())
        .accountNumber(account.getAccountNumber())
        .password(account.getPin())
        .authorities(authorities)
        .build();
  }

  private static Set<String> getUserRoles() {

    Set<String> roles = new HashSet<>();
    roles.add("Account Holder");

    return roles;
  }

  @Override
  public String getUsername() {
    return accountNumber;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
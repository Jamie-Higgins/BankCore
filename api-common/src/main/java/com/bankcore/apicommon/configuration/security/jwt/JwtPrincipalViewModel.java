package com.bankcore.apicommon.configuration.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtPrincipalViewModel {

  private String accountNumber;
  private int userId;
}

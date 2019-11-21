package com.bankcore.apicommon.service;

import com.bankcore.apicommon.configuration.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider tokenProvider;

  public String signInUser(final String accountNumber, final String pin) {

    final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(accountNumber, pin);
    final Authentication authentication = authenticationManager.authenticate(authenticationToken);

    SecurityContextHolder.getContext().setAuthentication(authentication);

    return tokenProvider.generateToken(authentication);
  }
}

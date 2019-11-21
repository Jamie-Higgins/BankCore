package com.bankcore.apicommon.configuration.security.jwt;

import com.bankcore.apicommon.configuration.exception.JwtException;
import com.bankcore.apicommon.configuration.security.UserPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
class JwtProviderConfiguration {

  @Bean
  @RequestScope
  public JwtPrincipalViewModel providePrincipalFromJwt() {

    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication.getPrincipal() instanceof UserPrincipal) {
      final UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
      return new JwtPrincipalViewModel(userPrincipal.getAccountNumber(), userPrincipal.getId());
    } else {
      throw new JwtException("Unable to retrieve Principal object from JWT.");
    }
  }
}

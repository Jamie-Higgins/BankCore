package com.bankcore.apicommon.configuration.security.jwt;

import com.bankcore.apicommon.configuration.exception.JwtException;
import com.bankcore.apicommon.configuration.security.UserPrincipal;
import com.bankcore.apicommon.configuration.security.UserSecurityService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  JwtTokenProvider tokenProvider;

  @Autowired
  UserSecurityService userSecurityService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    try {
      String jwt = getJwtFromRequest(request);
      authenticateJwtToken(request, jwt);
    } catch (Exception e) {
      SecurityContextHolder.clearContext();
      System.out.println("e: " + e);
    }

    filterChain.doFilter(request, response);
  }

  private void authenticateJwtToken(HttpServletRequest request, String jwt) throws JwtException {
    if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
      JwtMetadata metadata = tokenProvider.getJwtMetadata(jwt);
      UserPrincipal userDetails = (UserPrincipal) userSecurityService.loadUserById(Math.toIntExact(metadata.getId()));

      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
  }

  private String getJwtFromRequest(HttpServletRequest request) {
    final String Bearer_Prefix = "Bearer ";
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && StringUtils.startsWithIgnoreCase(bearerToken, Bearer_Prefix)) {
      return StringUtils.delete(bearerToken, Bearer_Prefix);
    }
    return null;
  }
}
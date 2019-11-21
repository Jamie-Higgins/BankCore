package com.bankingapi.configuration.security;

import com.bankcore.apicommon.configuration.security.BaseSecurityConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    securedEnabled = true,
    jsr250Enabled = true,
    prePostEnabled = true
)
public class SecurityConfiguration extends BaseSecurityConfiguration {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/v2/api-docs")
        .permitAll()
        .antMatchers("swagger-ui.html")
        .permitAll()
        .antMatchers("/swagger-resources/**")
        .permitAll()
        .antMatchers("/authentication")
        .permitAll()
        .antMatchers("/external")
        .permitAll()
        .antMatchers("/account/**")
        .authenticated();
    super.configure(http);
  }
}

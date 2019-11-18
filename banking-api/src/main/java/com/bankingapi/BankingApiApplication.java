package com.bankingapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
@ComponentScan({"com.bankingapi", "com.bankcore.apicommon"})
@EntityScan({"com.bankingapi", "com.bankcore.apicommon.entity"})
@EnableJpaRepositories({"com.bankingapi", "com.bankcore.apicommon.repository"})
@EnableDiscoveryClient
public class BankingApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(BankingApiApplication.class, args);
  }
}

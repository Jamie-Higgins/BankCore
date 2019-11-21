package com.bankingapi.controllers;

import com.bankcore.apicommon.configuration.mapping.Mapper;
import com.bankcore.apicommon.configuration.security.jwt.JwtPrincipalViewModel;
import com.bankcore.apicommon.dto.TransactionDTO;
import com.bankcore.apicommon.service.BankingService;
import com.bankingapi.viewModels.AccountOverviewViewModel;
import com.bankingapi.viewModels.DepositTransactionViewModel;
import com.bankingapi.viewModels.WithdrawTransactionViewModel;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

  private final Mapper mapper;
  private final BankingService bankingService;
  private final JwtPrincipalViewModel jwtPrincipalViewModel;

  @ApiOperation(value = "Close an account")
  @DeleteMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> closeBankAccount() {

    bankingService.closeBankAccount(jwtPrincipalViewModel.getAccountNumber());

    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @ApiOperation(value = "Deposit funds to an account with an amount to deposit along with a description of the deposit")
  @PutMapping(value = "/deposit", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> depositFunds(@Valid @RequestBody final DepositTransactionViewModel depositTransactionViewModel) {

    bankingService.depositFunds(jwtPrincipalViewModel.getAccountNumber(), mapper.map(depositTransactionViewModel, TransactionDTO.class));

    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @ApiOperation(value = "Withdraw funds to an account with an amount to withdraw along with a description of the withdrawal")
  @PutMapping(value = "/withdraw", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> withdrawFunds(@Valid @RequestBody final WithdrawTransactionViewModel withdrawTransactionViewModel) {

    bankingService.withdrawFunds(jwtPrincipalViewModel.getAccountNumber(), mapper.map(withdrawTransactionViewModel, TransactionDTO.class));

    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @ApiOperation(value = "Get an overview of the current user's balance")
  @GetMapping(value = "/currentBalance", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getCurrentBalance() {

    return ResponseEntity.ok(bankingService.getCurrentBalance(jwtPrincipalViewModel.getAccountNumber()));
  }

  @ApiOperation(value = "Get an overview of the current user's bank account including the last 5 transactions")
  @GetMapping(value = "/overview/", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getAccountOverview() {

    return ResponseEntity.ok(mapper.map(bankingService.getAccountOverview(jwtPrincipalViewModel.getAccountNumber()), AccountOverviewViewModel.class));
  }
}

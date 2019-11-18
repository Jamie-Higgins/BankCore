package com.bankingapi.controllers;

import com.bankcore.apicommon.configuration.mapping.Mapper;
import com.bankcore.apicommon.dto.CreateBankAccountDTO;
import com.bankcore.apicommon.dto.TransactionDTO;
import com.bankcore.apicommon.service.BankingService;
import com.bankingapi.viewModels.AccountOverviewViewModel;
import com.bankingapi.viewModels.AccountViewModel;
import com.bankingapi.viewModels.CreateBankAccountViewModel;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
  //private final JwtPrincipalViewModel jwtPrincipalViewModel; //TODO Replace pathable variables with JWT

  @ApiOperation(value = "Create a new account by supplying Forename, Surname, desired pin and an SSN")
  @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> createBankAccount(@Valid @RequestBody final CreateBankAccountViewModel createBankAccountViewModel) {

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(mapper.map(bankingService.createBankAccount
            (mapper.map(createBankAccountViewModel, CreateBankAccountDTO.class)), AccountViewModel.class));
  }

  @ApiOperation(value = "Close an account")
  @DeleteMapping(value = "/{accountNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> closeBankAccount(@Valid @PathVariable final String accountNumber) {

    bankingService.closeBankAccount(accountNumber);

    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @ApiOperation(value = "Deposit funds to an account with an amount to deposit along with a description of the deposit")
  @PutMapping(value = "/deposit/{accountNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> depositFunds(@Valid @PathVariable final String accountNumber, @RequestBody final DepositTransactionViewModel depositTransactionViewModel) {

    bankingService.depositFunds(accountNumber, mapper.map(depositTransactionViewModel, TransactionDTO.class));

    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @ApiOperation(value = "Withdraw funds to an account with an amount to withdraw along with a description of the withdrawal")
  @PutMapping(value = "/withdraw/{accountNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> withdrawFunds(@Valid @PathVariable final String accountNumber, @RequestBody final WithdrawTransactionViewModel withdrawTransactionViewModel) {

    bankingService.withdrawFunds(accountNumber, mapper.map(withdrawTransactionViewModel, TransactionDTO.class));

    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @ApiOperation(value = "Get an overview of the current user's balance")
  @GetMapping(value = "/{accountNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getCurrentBalance(@Valid @PathVariable final String accountNumber) {

    return ResponseEntity.ok(bankingService.getCurrentBalance(accountNumber));
  }

  @ApiOperation(value = "Get an overview of the current user's bank account including the last 5 transactions")
  @GetMapping(value = "/overview/{accountNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getAccountOverview(@Valid @PathVariable final String accountNumber) {

    return ResponseEntity.ok(mapper.map(bankingService.getAccountOverview(accountNumber), AccountOverviewViewModel.class));
  }


}

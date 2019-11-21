package com.bankingapi.controllers;

import com.bankcore.apicommon.configuration.mapping.Mapper;
import com.bankcore.apicommon.dto.CreateBankAccountDTO;
import com.bankcore.apicommon.service.AuthenticationService;
import com.bankcore.apicommon.service.BankingService;
import com.bankingapi.viewModels.CreateBankAccountViewModel;
import com.bankingapi.viewModels.LoginViewModel;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthenticationController {

  private final Mapper mapper;
  private final BankingService bankingService;
  private final AuthenticationService authenticationService;
  private final PasswordEncoder passwordEncoder;

  @ApiOperation(value = "Create a new account by supplying Forename, Surname, desired pin and an SSN")
  @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> createBankAccount(@Valid @RequestBody final CreateBankAccountViewModel createBankAccountViewModel) {

    createBankAccountViewModel.setPin(passwordEncoder.encode(createBankAccountViewModel.getPin()));
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(bankingService.createBankAccount
            (mapper.map(createBankAccountViewModel, CreateBankAccountDTO.class)));
  }

  @ApiOperation(value = "Sign in to an account with your account number and pin")
  @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> signInToAccount(@Valid @RequestBody final LoginViewModel loginViewModel) {

    return ResponseEntity.status(HttpStatus.OK).body(authenticationService.signInUser(loginViewModel.getAccountNumber(), loginViewModel.getPin()));

  }
}

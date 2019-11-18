package com.bankingapi.controllers;

import com.bankcore.apicommon.configuration.mapping.Mapper;
import com.bankcore.apicommon.service.BankingService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/external/")
@RequiredArgsConstructor
public class DebitAndCheckProcessingController {

  private final Mapper mapper;
  private final BankingService bankingService;
  //private final JwtPrincipalViewModel jwtPrincipalViewModel; //TODO Replace pathable variables with JWT


  @ApiOperation(value = "External Debit processing")
  @GetMapping(value = "/debit/", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> debitProcessing() {

    return null;
  }

  @ApiOperation(value = "External Check processing")
  @GetMapping(value = "/check/", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> checkProcessing() {

    return null;
  }


}

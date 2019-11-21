package com.bankingapi.controllers;

import com.bankcore.apicommon.configuration.mapping.Mapper;
import com.bankcore.apicommon.dto.ExternalServiceDTO;
import com.bankcore.apicommon.service.BankingService;
import com.bankingapi.viewModels.ExternalServiceViewModel;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/external/")
@RequiredArgsConstructor
public class ExternalServicesController {

  private final Mapper mapper;
  private final BankingService bankingService;

  @ApiOperation(value = "External Debit processing")
  @PutMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> processExternalService(@Valid @RequestBody final ExternalServiceViewModel externalServiceViewModel) {

    return ResponseEntity.status(HttpStatus.OK)
        .body(bankingService.processExternalServices(mapper.map(externalServiceViewModel, ExternalServiceDTO.class)));
  }


}

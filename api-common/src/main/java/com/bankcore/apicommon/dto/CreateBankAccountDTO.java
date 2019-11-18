package com.bankcore.apicommon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBankAccountDTO {

  private String forename;

  private String surname;

  private String pin;

  private String ssn;

}

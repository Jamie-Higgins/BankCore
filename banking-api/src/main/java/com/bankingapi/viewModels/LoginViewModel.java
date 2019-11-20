package com.bankingapi.viewModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NotNull
public class LoginViewModel {

  @JsonProperty("accountNumber")
  private String accountNumber;

  @JsonProperty("pin")
  private String pin;

}

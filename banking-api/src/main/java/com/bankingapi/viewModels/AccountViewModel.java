package com.bankingapi.viewModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NotNull
public class AccountViewModel {


  @NotBlank
  @Size(min = 3, max = 30)
  @JsonProperty("accountNumber")
  private String accountNumber;

  @NotBlank
  @Size(min = 3, max = 30)
  @JsonProperty("forename")
  private String forename;

  @NotBlank
  @Size(min = 3, max = 30)
  @JsonProperty("surname")
  private String surname;

  @NotBlank
  @Size(min = 4, max = 4)
  @JsonProperty("pin")
  private String pin;

  @NotBlank
  @Size(min = 9, max = 9)
  @JsonProperty("ssn")
  private String ssn;

}

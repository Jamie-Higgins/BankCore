package com.bankingapi.viewModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NotNull
public class ExternalServiceViewModel {

  @NotNull
  @JsonProperty("accountNumber")
  private String accountNumber;

  @NotNull
  @Positive
  @Digits(integer = 9, fraction = 2)
  @JsonProperty("amount")
  private BigDecimal amount;

  @NotBlank
  @Size(min = 3, max = 30)
  @JsonProperty("type") //TODO Restrict to Debit or Credit
  private String type;

  @NotBlank
  @Size(min = 3, max = 30)
  @JsonProperty("description")
  private String description;

}

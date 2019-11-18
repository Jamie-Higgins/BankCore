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
public class WithdrawTransactionViewModel {
//If not using mapping can use the one view model for deposit and withdraw
  @NotNull
  @Positive
  @Digits(integer=9, fraction=2)
  @JsonProperty("amount")
  private BigDecimal amount;

  @NotBlank
  @Size(min = 3, max = 30)
  @JsonProperty("description")
  private String description;

}

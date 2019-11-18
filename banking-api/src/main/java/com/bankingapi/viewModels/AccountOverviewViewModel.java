package com.bankingapi.viewModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NotNull
public class AccountOverviewViewModel {

  @JsonProperty("accountNumber")
  private String accountNumber;

  @JsonProperty("forename")
  private String forename;

  @JsonProperty("surname")
  private String surname;

  @JsonProperty("currentBalance")
  private BigDecimal currentBalance;

  @JsonProperty("recentTransactions")
  private List<TransactionViewModel> lastFiveTransactions;

}

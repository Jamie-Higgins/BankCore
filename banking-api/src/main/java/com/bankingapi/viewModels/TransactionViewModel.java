package com.bankingapi.viewModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NotNull
public class TransactionViewModel {

  @JsonProperty("date")
  private ZonedDateTime date;

  @JsonProperty("type")
  private String type;

  @JsonProperty("amount")
  private BigDecimal amount;

  @JsonProperty("description")
  private String description;


}

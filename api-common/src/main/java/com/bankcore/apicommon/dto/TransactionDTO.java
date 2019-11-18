package com.bankcore.apicommon.dto;


import java.math.BigDecimal;
import java.time.ZonedDateTime;
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
public class TransactionDTO {

  private String transactionId;

  private ZonedDateTime date;

  private String type;

  private BigDecimal amount;

  private String description;
}

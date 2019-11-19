package com.bankcore.apicommon.dto;

import java.math.BigDecimal;
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
public class ExternalServiceDTO {

  private String accountNumber;

  private BigDecimal amount;

  private String type;

  private String description;
}

package com.bankcore.apicommon.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {

  DEPOSIT(1, "Deposit"),
  WITHDRAWAL(2, "Withdrawal"),
  DEBIT(3, "Debit"),
  CHECKS(4, "Checks");

  private final int id;
  private final String description;
}

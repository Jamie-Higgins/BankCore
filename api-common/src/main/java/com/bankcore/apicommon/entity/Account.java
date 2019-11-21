package com.bankcore.apicommon.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ACCOUNTS")
public class Account {

  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private int id;

  @Column(name = "account_number", updatable = false, nullable = false)
  private String accountNumber;

  @Size(min = 3, max = 30)
  @Column(name = "forename", nullable = false)
  private String forename;

  @Size(min = 3, max = 30)
  @Column(name = "surname", nullable = false)
  private String surname;

  @Column(name = "pin", nullable = false)
  private String pin;

  @Size(min = 9, max = 9)
  @Column(name = "ssn", nullable = false)
  private String ssn;

  @Column(name = "current_balance", nullable = false)
  private BigDecimal currentBalance;

  @Builder.Default
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "account", cascade = CascadeType.ALL)
  private List<Transaction> lastFiveTransactions = new ArrayList<>();

}

package com.bankcore.apicommon.entity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
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
@Table(name = "TRANSACTIONS")
public class Transaction {

  @Id
  @GeneratedValue
  @Column(name = "id", updatable = false, nullable = false)
  private int id;

  @Column(name = "account", updatable = false, nullable = false)
  private String transactionId;

  @Column(name = "date", nullable = false)
  private ZonedDateTime date;

  @Size(min = 5, max = 10)
  @Column(name = "type", nullable = false)
  private String type;

  @Digits(integer=9, fraction=2)
  @Column(name = "amount", nullable = false)
  private BigDecimal amount;

  @Size(min = 3, max = 30)
  @Column(name = "description", nullable = false)
  private String description;

  @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id", nullable = false)
  private Account account;

}

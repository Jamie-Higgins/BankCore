package com.bankcore.apicommon.repository;

import com.bankcore.apicommon.entity.Transaction;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>, JpaSpecificationExecutor<Transaction> {

  Optional<List<Transaction>> findTop5ByAccountIdOrderByDateDesc(final int accountId);

  void deleteAllByAccount_AccountNumber(final String accountNumber);

}

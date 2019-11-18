package com.bankcore.apicommon.repository;

import com.bankcore.apicommon.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account> {

  Account findFirstByAccountNumber(final String accountNumber);

  Account findFirstBySsn(final String ssn);

  void deleteByAccountNumber(final String accountNumber);

}

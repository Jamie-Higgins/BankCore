package com.bankcore.apicommon.repository;

import com.bankcore.apicommon.entity.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account> {

  Optional<Account> findByAccountNumber(final String accountNumber);

  void deleteByAccountNumber(final String accountNumber);

}

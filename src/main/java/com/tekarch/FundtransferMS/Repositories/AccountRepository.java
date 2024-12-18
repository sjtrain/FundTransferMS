package com.tekarch.FundtransferMS.Repositories;

import com.tekarch.FundtransferMS.Models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}

package com.tekarch.FundtransferMS.Repositories;

import com.tekarch.FundtransferMS.Models.FundTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FundTransferRepository extends JpaRepository<FundTransfer,Long> {

   // List<FundTransfer> findByAccountId(Long userId);
}

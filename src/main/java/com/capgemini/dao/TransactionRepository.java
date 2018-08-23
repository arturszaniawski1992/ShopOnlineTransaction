package com.capgemini.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.domain.TransactionEntity;
import com.capgemini.enums.TransactionStatus;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

	TransactionEntity findById(Long id);

	List<TransactionEntity> findByTransactionStatus(TransactionStatus transactionStatus);

	void deleteByTransactionStatus(TransactionStatus transactionStatus);

	void deleteById(Long id);
}

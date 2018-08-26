package com.capgemini.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.dao.customize.CustomizedTransactionRepository;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.enums.TransactionStatus;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long>, CustomizedTransactionRepository {
	/**
	 * This is the method which find transaction by id.
	 * 
	 * @param Long
	 *            as id of transaction.
	 * 
	 * @return Transaction as TransactionEntity.
	 */
	TransactionEntity findById(Long id);

	/**
	 * This is the method which find transaction by its status.
	 * 
	 * @param TransactionStatus
	 *            as status of transaction.
	 * 
	 * @return List of transactions.
	 */
	List<TransactionEntity> findByTransactionStatus(TransactionStatus transactionStatus);

	/**
	 * This is the method which remove transaction from collection.
	 * 
	 * @param TransactionStatus
	 *            as status of transaction.
	 * 
	 */
	void deleteByTransactionStatus(TransactionStatus transactionStatus);

	/**
	 * This is the method which remove transaction from collection.
	 * 
	 * @param Long
	 *            as id of transaction.
	 * 
	 */
	void deleteById(Long id);

}

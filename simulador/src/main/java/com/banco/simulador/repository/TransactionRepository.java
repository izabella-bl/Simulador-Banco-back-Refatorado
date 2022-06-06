package com.banco.simulador.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.banco.simulador.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction,Long>{

	@Query( nativeQuery=true,value ="SELECT * FROM Transactions t  WHERE date_part('day', TIMESTAMP t.created_at) = ?1")
	List<Transaction> findTransactionsByDateAndUser(LocalDateTime date);
}

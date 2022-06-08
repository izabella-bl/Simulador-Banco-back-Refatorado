package com.banco.simulador.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.banco.simulador.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction,Long>{

	List<Transaction> findByCreatedAtAndStatus(LocalDate date, byte status);

	List<Transaction> findByOriginId(Long userId);


}

package com.banco.simulador.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.banco.simulador.model.Transaction;
import com.banco.simulador.repository.TransactionRepository;

@Configuration
@EnableScheduling
public class SchedulerService {	
	
	@Autowired
	private TransactionService transactionService;
	
	@Scheduled(cron = " 0 0/1 * * * *")
	private void executeTransaction() {
		
		LocalDateTime dateNow = LocalDateTime.now();
		dateNow.minusDays(1);
		List<Transaction> transactionsToday = transactionService.getTodayTransactions(dateNow);
		transactionsToday.forEach(transaction -> {
			transactionService.executeTransaction(transaction);
		});
		
	}


}

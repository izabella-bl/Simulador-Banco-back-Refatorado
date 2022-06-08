package com.banco.simulador.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
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
	
	@Scheduled(cron = " 0 0 0 * * *")
	private void executeTransaction() {
		
		LocalDate dateNow = LocalDate.now().minusDays(1);
		List<Transaction> transactionsToday = transactionService.getTodayTransactions(dateNow,(byte)0);
		transactionsToday.forEach(transaction -> {
			transactionService.executeTransaction(transaction);
		});
		
	}


}

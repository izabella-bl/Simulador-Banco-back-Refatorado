package com.banco.simulador.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banco.simulador.model.Transaction;
import com.banco.simulador.model.Users;
import com.banco.simulador.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private HistoryService historyService;
	
	public void executeTransaction(Transaction transaction) {
		Users originUser = usersService.getUserById(transaction.getOriginId());
		Users destinyUser = usersService.getUserByCpf(transaction.getRecipientCpf());
		
		BigDecimal destinyBalance = destinyUser.getAccount().getBalance();
        BigDecimal originBalance = originUser.getAccount().getBalance();
        BigDecimal transferedValue = BigDecimal.valueOf(transaction.getValue());
        destinyUser.getAccount().setBalance(transferedValue.add(destinyBalance));
        originUser.getAccount().setBalance(originBalance.subtract(transferedValue));
        destinyUser = usersService.saveUser(destinyUser);
        originUser = usersService.saveUser(originUser);
        historyService.saveHistory(originUser, destinyUser, transferedValue);
	}
	
	public List<Transaction> getTodayTransactions(LocalDateTime dateNow) {
		return transactionRepository.findTransactionsByDateAndUser(dateNow);
	}
	public Transaction saveTransaction(Transaction transaction) {
		return transactionRepository.save(transaction);
	}
	
//	public Transaction createTransaction(Long originId, String recipientCpf, double transferValue) {
//		Transaction transaction = new Transaction();
//		transaction
//		return null;
//	}
}

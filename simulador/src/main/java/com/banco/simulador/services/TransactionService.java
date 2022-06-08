package com.banco.simulador.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.banco.simulador.model.Account;
import com.banco.simulador.repository.AccountRepository;
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

	@Autowired
	private AccountRepository accountRepository;
	
	public void executeTransaction(Transaction transaction) {
		Users originUser = usersService.getUserById(transaction.getOriginId());
		Users destinyUser = usersService.getUserByCpf(transaction.getRecipientCpf());

		Account originAccount = originUser.getAccount();
		Account  destinyAccount = destinyUser.getAccount();

		BigDecimal destinyBalance = destinyUser.getAccount().getBalance();
        BigDecimal originBalance = originUser.getAccount().getBalance();
        BigDecimal transferedValue = BigDecimal.valueOf(transaction.getValue());
        originBalance = originBalance.subtract(transferedValue);
        destinyBalance = destinyBalance.add(transferedValue);
        originAccount.setBalance(originBalance);
        destinyAccount.setBalance(destinyBalance);
        originUser.setAccount(originAccount);
		destinyUser.setAccount(destinyAccount);
        transaction.setStatus((byte)1);
        transaction.setUpdated_At(LocalDateTime.now());
        accountRepository.save(originUser.getAccount());
        accountRepository.save(destinyUser.getAccount());
		destinyUser = usersService.saveUser(destinyUser);
		originUser = usersService.saveUser(originUser);
        transactionRepository.save(transaction);
        historyService.saveHistory(originUser, destinyUser, transferedValue);
	}
	
	public List<Transaction> getTodayTransactions(LocalDate dateNow ,byte status) {
		return transactionRepository.findByCreatedAtAndStatus(dateNow,status);
	}
	public Transaction saveTransaction(Transaction transaction) {
		return transactionRepository.save(transaction);
	}
	
    public void updateTransation(Transaction transaction) throws Exception {
		LocalDate dateNow = LocalDate.now();
		Transaction databaseTransaction = transactionRepository.findById(transaction.getId()).get();
		if(!databaseTransaction.getCreatedAt().isEqual(dateNow)){
			throw new Exception("Can't update");
		}else {
			databaseTransaction.setValue(transaction.getValue());
			databaseTransaction.setDescription(transaction.getDescription());
			databaseTransaction.setUpdated_At(LocalDateTime.now());
			transactionRepository.save(databaseTransaction);
		}
	}

	public List<Transaction> getUserTransaction(Long userId){
		return  transactionRepository.findByOriginId(userId);
	}
}

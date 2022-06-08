package com.banco.simulador.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banco.simulador.model.Account;
import com.banco.simulador.model.Transaction;
import com.banco.simulador.model.Users;
import com.banco.simulador.repository.AccountRepository;
import com.banco.simulador.repository.UsersRepository;


@Service
public class AccountService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private AccountRepository accountRepository;
//    @Autowired
//    private HistoryService historyService;
    @Autowired
    private TransactionService transactionService;

    public void transfer(Long originId, String recipientCpf, double transferValue) throws Exception {

        if((originId == null) || (recipientCpf.isEmpty())){
            throw new Exception("User not found");
        }

        Users originUser = usersRepository.findById(originId).get();
        Users destinyUser = usersRepository.findByCpf(recipientCpf);

        if((originUser == null) || (destinyUser == null)){
            throw new Exception("User not found");
        }

        if(transferValue <= Double.parseDouble(originUser.getAccount().getBalance().toString())){

        	Transaction transaction = new Transaction(originUser.getId(),recipientCpf,transferValue,'P',(byte)0,"");
        	transactionService.saveTransaction(transaction);

        }else{
            throw new Exception("Balance is not sufficient");
        }

    }

    public Users initializeAccount(Users user){
        if(user != null ){
            Account account = new Account();
            account.setBalance(new BigDecimal(1000));
            user.setAccount(saveAccount(account));
            return user;
        }

        return null;
    }

    public Account saveAccount (Account account){
        if(account != null){
            return accountRepository.save(account);
        }
        return  null;
    }
    
   public void updateTransaction(Transaction transaction) throws Exception{
        transactionService.updateTransation(transaction);
   }

   public List<Transaction> getUserTransaction(Long userId){
        return transactionService.getUserTransaction(userId);
   }

}

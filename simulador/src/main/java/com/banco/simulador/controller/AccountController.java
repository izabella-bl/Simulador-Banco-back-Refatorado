package com.banco.simulador.controller;


import com.banco.simulador.model.Transaction;
import com.banco.simulador.services.AccountService;
import com.banco.simulador.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UsersService usersService;

    @PostMapping("/transfer")
    public String transfer(@RequestBody Transaction accountTransfer) throws Exception {
    	accountService.transfer(accountTransfer.getOriginId(),accountTransfer.getRecipientCpf(),accountTransfer.getValue());
        return "Succesfull Transfer!";
    }

    @PutMapping("/transfer/update")
    public String updateTransfer(@RequestBody Transaction accountTransfer) throws Exception{
        try {
            accountService.updateTransaction(accountTransfer);
            return "Succesfull Transfer!";
        }catch (Exception e) {
            return "Can't transfer";
        }
    }

    @GetMapping("/transfer")
    public List<Transaction> getTransfer(@RequestParam Long userId){
        return accountService.getUserTransaction(userId);
    }

}

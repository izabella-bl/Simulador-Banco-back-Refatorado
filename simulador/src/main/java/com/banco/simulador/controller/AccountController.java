package com.banco.simulador.controller;


import com.banco.simulador.model.Transaction;
import com.banco.simulador.services.AccountService;
import com.banco.simulador.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


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


}

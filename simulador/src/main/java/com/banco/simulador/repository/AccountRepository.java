package com.banco.simulador.repository;

import com.banco.simulador.model.Account;
import org.springframework.data.repository.CrudRepository;


public interface AccountRepository  extends CrudRepository<Account,Long> {

}
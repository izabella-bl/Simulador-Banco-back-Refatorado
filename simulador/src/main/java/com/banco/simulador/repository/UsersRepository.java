package com.banco.simulador.repository;

import com.banco.simulador.model.Users;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<Users,Long> {

    Users findByCpf(String cpf);
}

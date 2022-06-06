package com.banco.simulador.controller;


import com.banco.simulador.model.Users;
import com.banco.simulador.services.AccountService;
import com.banco.simulador.services.SecurityService;
import com.banco.simulador.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.banco.simulador.repository.UsersRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class UsersController {


    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UsersService usersService;
    @Autowired
    private AccountService accountService;

    @Autowired
    private SecurityService securityService;

    @PostMapping("/login")
    public Users login(@RequestBody Users user) {

        return securityService.authWithAuthManager(user.getCpf(), user.getPassword());
    }

    @GetMapping("/isLogged")
    public boolean isLogged(@RequestBody String cpf) {
        return securityService.isLoggedIn(cpf);
    }

    @GetMapping("/listUsers")
    @PreAuthorize("hasRole('USER')")
    public List<Users> listUsers() {
        List<Users> users = (List<Users>) usersRepository.findAll();
        return users;
    }

    @GetMapping("/home/{id}")
    public Optional<Users> home(@PathVariable Long id) {
        Optional<Users> users = usersRepository.findById(id);
        return users;
    }

    @CrossOrigin(origins = "http://localhost:3000/cadastro")
    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody Users user) {
        boolean isCpfValid = usersService.isCpfValid(user.getCpf());
        boolean isEmailValid = usersService.isEmailValid(user.getEmail());
        boolean isUserValid = usersService.isUserValid(user);
        if (isCpfValid && isEmailValid && isUserValid) {
            String password = usersService.encryptPassword(user);
            user.setPassword(password);
            user = accountService.initializeAccount(user);
            usersRepository.save(user);
            return new ResponseEntity(HttpStatus.OK);
        } else if (!isCpfValid || !isEmailValid) {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }


    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable long userId) {
    	usersRepository.deleteById(userId);
        return "Succesfully Deleted";
    }

    @PutMapping("/{id}")
    public String update(@RequestBody Users user, @PathVariable int userId) {
        if (user.getId() == userId) {
        	usersRepository.save(user);
            return "Update succesfull!";
        }
        return "Id doesnt match";
    }
    
    @PutMapping("/deactivate/{id}")
    public String deactivateUser(@PathVariable Long userId) {
    	Users user = usersService.deactivateUser(userId);
    	if (user != null) {
    		return "User Deactivated";
    	}else {
    		return "User Not Found!";
    	}
    }

}

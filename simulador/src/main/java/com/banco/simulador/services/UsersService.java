package com.banco.simulador.services;


import br.com.caelum.stella.validation.CPFValidator;
import com.banco.simulador.model.Users;
import com.banco.simulador.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


@Service
public class UsersService {

    @Autowired
    private UsersRepository userRepository;

    public String encryptPassword(Users model){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(model.getPassword());

        return password;
    }


    public boolean isCpfValid(String cpf){
        CPFValidator cpfValidator = new CPFValidator();
        try {
            cpfValidator.assertValid(cpf);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean isEmailValid(String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
            return true;
        } catch (AddressException ex) {
            return false;
        }
    }

    public  boolean isUserValid(Users user){
        Users userExists  =   userRepository.findByCpf(user.getCpf());
        if (userExists == null){
            return true;
        }else{
            return  false;
        }
    }
    public Users getUserById(Long userId) {
    	return userRepository.findById(userId).get();
    }
    
    public Users getUserByCpf(String cpf) {
    	return userRepository.findByCpf(cpf);
    }
    
    public Users deactivateUser(Long userId) {
    	if(userId != null) {
    		return userRepository.findById(userId).get();
    	}
    	return null;
    }
    
    public Users saveUser(Users user) {
    	return userRepository.save(user);
    }




}

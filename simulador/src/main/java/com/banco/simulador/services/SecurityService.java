package com.banco.simulador.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.banco.simulador.config.JwtTokenUtil;
import com.banco.simulador.model.Users;
import com.banco.simulador.repository.UsersRepository;

@Service
public class SecurityService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsersRepository userRepository;
    
    @Autowired
    CustomUserDetailService userDetailsService;
    
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    public Users authWithAuthManager(String cpf, String password) {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                cpf, password);

        Authentication authentication = authenticationManager.authenticate(authRequest);

        UserDetails userDetails = userDetailsService.loadUserByUsername(cpf);
        final String tokenJwt = jwtTokenUtil.generateToken(userDetails);
        
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        Users user = userRepository.findByCpf(cpf);
        user.setJwt(tokenJwt);
        return user;
    }

    public boolean isLoggedIn(String cpf) {
        if(SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase(cpf)) return true;
        else return false;

    }
}
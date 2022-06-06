package com.banco.simulador.services;

import com.banco.simulador.model.Users;
import com.banco.simulador.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {
    private final UsersRepository userRepository;

    @Autowired
    public CustomUserDetailService(UsersRepository usuarioRepository) {
        this.userRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        Users user =Optional.ofNullable(userRepository.findByCpf(cpf)).orElseThrow(()-> new UsernameNotFoundException("User not Found"));
        List<GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList("ROLE+ADMIN");
        List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList("ROLE_USER");
        return new User(user.getCpf(), user.getPassword(),authorityListUser );
    }
}

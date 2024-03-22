package com.example.demopostman.jwt;


import com.example.demo.jwt.UserInDB;
import com.example.demo.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws BadCredentialsException {
        UserInDB user = userRepository.findUserInDBByUsername(username);
        Set<SimpleGrantedAuthority> authoritySet = Arrays.stream(user.getRoles().split(","))
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toSet());
        return new CustomUserDetails(user.getUsername(), user.getPassword(), authoritySet);
    }
}

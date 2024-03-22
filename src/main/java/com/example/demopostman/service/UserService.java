package com.example.demopostman.service;

import com.example.demopostman.detail.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

     @Autowired
     private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw  new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user);
    }

    public UserDetails loadUserById(long userId) throws UsernameNotFoundException {
        User user = userRepository.findUserById(userId);
        String Id = String.valueOf(userId);
        if (user == null){
            throw  new UsernameNotFoundException(Id);
        }
        return new CustomUserDetails(user);
    }



}

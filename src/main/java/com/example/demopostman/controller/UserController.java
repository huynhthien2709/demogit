package com.example.demopostman.controller;

import com.example.demo.jwt.UserInDB;
import com.example.demo.jwt.caching.CacheProperties;
import com.example.demo.jwt.caching.SpringCachingService;
import com.example.demo.jwt.dto.LoginDto;
import com.example.demo.jwt.repository.UserRepository;
import com.example.demo.jwt.service.JwtHs512Service;
import com.example.demo.jwt.service.JwtRs256Service;
import com.example.demo.jwt.utils.SecurityUtils;
import com.example.demopostman.dto.UserDTO;
import com.example.demopostman.repository.UserRepository;
import com.example.demopostman.service.JwtHs512Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/public")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;



    @Autowired
    private JwtHs512Service jwtHs512Service;

    @Autowired
    private UserRepository userRepository;





    @GetMapping("/valid-hsa")
    public Map<String, String> validHSAToken(@RequestParam String token) throws Exception{
        return jwtHs512Service.parseToken(token);
    }


    @PostMapping("/login-hsa")
    public String loginWithHSA(@RequestBody UserDTO dto) throws Exception{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        User user = userRepository.findUserInDBByUsername(dto.getUsername());
        return jwtHs512Service.genToken(user);
    }

}

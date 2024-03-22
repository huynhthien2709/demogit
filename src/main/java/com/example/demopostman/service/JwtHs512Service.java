package com.example.demopostman.service;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class JwtHs512Service {
    private Key key;
    private JwtParser jwtParser;
    private String jwtBase64Secret = "ZDdlZTJkNTViMWJiMzA3OTFmMThhNGU5MTZiNzMwMmNhMGQyZjUwMzcwZWMwZThiNTYwN2FiMmEwNDNhMDkxNmMzMjBkMmMzNmRlYjM1YzhkYzJmZDU0YjU1YWMxNzZmOTY0ZjA3MmMyZTkxNzI5MjY1MTc4ODQ5YWJhNTY0YzQ=";
    private static final String AUTHORITIES_KEY = "auth";

    @PostConstruct
    public void setup() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtBase64Secret);
        key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    public String genToken(User user) {
        return "token: " +
                Jwts.builder()
                    .setHeaderParam("typ","JWT")
                    .setIssuer("ACB")
                    .setSubject(user.getUsername())
                    .setAudience("Loyalty")
                    .setExpiration(new Date((new Date()).getTime() + 2*60*1000L))
                    .setNotBefore(new Date())
                    .setIssuedAt(new Date())
                    .setId(user.get().toString())
                    .claim(AUTHORITIES_KEY, user.getRoles())
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Map<String, String> parseToken(String authToken) {
        Map<String, String> result = new HashMap<>();
        try {
            jwtParser.isSigned(authToken);
            Jws parseClaimsJws = jwtParser.parseClaimsJws(authToken);
            result.put("Header: ", parseClaimsJws.getHeader().toString());
            result.put("Body: ", parseClaimsJws.getBody().toString());
            result.put("Signature: ", parseClaimsJws.getSignature());
            return result;
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            throw e;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("Invalid JWT token.");
            throw e;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        String username = claims.get("sub").toString();
        Set<SimpleGrantedAuthority> authorities = Arrays
                .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        return new UsernamePasswordAuthenticationToken(new User(username, "", authorities), token, authorities);
    }

}

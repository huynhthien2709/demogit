package com.example.demopostman.jwt;

import com.example.demo.jwt.caching.CacheProperties;
import com.example.demo.jwt.caching.SpringCachingService;
import com.example.demo.jwt.service.JwtHs512Service;
import com.example.demo.jwt.service.JwtRs256Service;
import com.example.demo.jwt.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private JwtHs512Service jwtHs512Service;

    @Autowired
    private JwtRs256Service jwtRs256Service;

    @Autowired
    private SpringCachingService cachingService;

    @Autowired
    private CacheProperties cacheProperties;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String bearerToken = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
        String jwt = SecurityUtils.resolveToken(bearerToken);
        try {
            if (Objects.nonNull(bearerToken) &&
                    !cachingService.checkExisted(jwt, cacheProperties.getLogOutBlacklist().getName())) {
                Authentication authentication = jwtHs512Service.getAuthentication(jwt);
//                Authentication authentication = jwtRs256Service.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}

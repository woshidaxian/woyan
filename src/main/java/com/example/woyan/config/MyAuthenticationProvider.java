package com.example.woyan.config;

import com.example.woyan.user.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserServiceImpl userDetailsService;

    private MyPasswordEncoder myPasswordEncoder = new MyPasswordEncoder();

    private Logger logger = LoggerFactory.getLogger(MyAuthenticationProvider.class);

    /**
     * 用户登录验证
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String)authentication.getCredentials();
        try{
            UserDetails user = userDetailsService.loadUserByUsername(username);
            logger.info(user.toString());
            if (myPasswordEncoder.matches(password, user.getPassword())){
                return new UsernamePasswordAuthenticationToken(username,null,user.getAuthorities());
            }
            throw new BadCredentialsException("WrongPassword");

        }catch (BadCredentialsException e){
            logger.info("wrong msg: "+e.getMessage());
            throw new BadCredentialsException(e.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}

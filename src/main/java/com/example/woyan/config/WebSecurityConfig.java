package com.example.woyan.config;

import com.example.woyan.filter.JWTAuthenticationFilter;
import com.example.woyan.filter.JWTLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationProvider provider;// 自定义的AuthenticationProvider

    @Bean
    public PasswordEncoder myPasswordEncoder() {
        return new MyPasswordEncoder();//自定义的加密工具
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/*.html").permitAll()
                .antMatchers("/css/*").permitAll()
                .antMatchers("/js/*").permitAll()
                .antMatchers("/layui/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/getCode").permitAll()
                .antMatchers("/changeVCourse").hasAnyAuthority("W0YAN_ADMIN")
                .anyRequest().hasAnyAuthority("W0YAN_USER","W0YAN_ADMIN","W0YAN_UPPER","W0YAN_TEACHER","W0YAN_EDITOR","W0YAN_CHECK")
                .and()
                .formLogin().loginProcessingUrl("/login")
                .and()
                .csrf().disable()
                .addFilter(new JWTLoginFilter(authenticationManager()))
                .addFilter(new JWTAuthenticationFilter(authenticationManager()));
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
    }
}

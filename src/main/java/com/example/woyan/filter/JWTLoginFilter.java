package com.example.woyan.filter;

import com.alibaba.fastjson.JSON;
import com.example.woyan.returnmsg.ReturnCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * 用户名、密码正确 返回User + token给客户端
 */
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(JWTLoginFilter.class);
    public static String JWTSECRET = "W0yAns3crET";

    private AuthenticationManager authenticationManager;

    public JWTLoginFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    // 解析用户凭证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("account");
        String password = request.getParameter("password");
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password,new ArrayList<>()));
    }

    /**
     * 如果登录成功，则返回token
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Claims claims = Jwts.claims();
        claims.put("authority",authResult.getAuthorities().stream()
                .map(s -> ((GrantedAuthority) s).getAuthority()).collect(Collectors.toList()));
        String token = Jwts.builder().setClaims(claims)
                .setSubject(authResult.getName()) //用户名
                .setExpiration(new Date(System.currentTimeMillis() + 7*24*60*60*1000))
                .signWith(SignatureAlgorithm.HS512,JWTSECRET).compact();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        logger.info(authResult.toString());
        PrintWriter out;
        String str = "{\"token\":\"" + token + "\"}";
        try {
            out = response.getWriter();
            out.print(str);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录失败则返回错误信息
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        logger.info("errors");
        logger.info(failed.getMessage());
        // 如果失败，返回用户名或密码错误
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        if (failed.getMessage().equals("logined")){
            writer.print(JSON.toJSON(ReturnCode.LOGIN_LOGINED));
        }else  {
            writer.print(JSON.toJSON(ReturnCode.LOGIN_WRONG_ACCOUNT_OR_PASSWORD));
        }
        writer.flush();
        writer.close();
    }
}

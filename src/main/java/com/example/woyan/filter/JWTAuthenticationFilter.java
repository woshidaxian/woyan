package com.example.woyan.filter;

import com.alibaba.fastjson.JSON;
import com.example.woyan.returnmsg.ReturnCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
    private Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager){
        super(authenticationManager);
    }

    /**
     * 获取token并解析
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        logger.info("header: " + header);
        logger.info(request.getRequestURI());
        if (request.getRequestURI().equals("/register") || request.getRequestURI().equals("/getCode")) {
            chain.doFilter(request, response);
        }else if (request.getRequestURI().contains(".html") || request.getRequestURI().contains("/js/") || request.getRequestURI().contains("/css/") || request.getRequestURI().contains("/img/") || request.getRequestURI().contains("/layui/")){
            chain.doFilter(request, response);
        }else if (header == null){
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print(JSON.toJSON(ReturnCode.LOGIN_NO_USER));
            out.flush();
        }else {
            try{
                UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                chain.doFilter(request, response);
            }catch (Exception e){
                e.printStackTrace();
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.print(JSON.toJSON(ReturnCode.LOGIN_NO_USER));
                out.flush();
            }
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) throws ExpiredJwtException, MalformedJwtException {
        String token = request.getHeader("Authorization");
        if (token != null) {
            Claims claims = Jwts.parser().setSigningKey(JWTLoginFilter.JWTSECRET).parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody();
            String user = claims.getSubject();
            @SuppressWarnings("unchecked")
            List<String> roles = claims.get("authority", List.class);
            List<SimpleGrantedAuthority> auth = roles.stream().map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toList());

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, auth);
            }
            return null;
        }
        return null;
    }
}

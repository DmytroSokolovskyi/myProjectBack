package com.example.myprojectback.configs;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RequestProcessingJWTFilter extends GenericFilterBean {
    @Override //фильтер запитiв
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        Authentication authentication = null;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("Authorization");

        System.out.println("RequestProcessingJWTFilter  ==> " + " token ==> " + token );

        if (token != null && token.startsWith("Bearer")) {
            String clearToken = token.replace("Bearer ", "");

             System.out.println( "Token ==> " + clearToken);

            String tokenData = Jwts.parser()
                    .setSigningKey("yes".getBytes())
                    .parseClaimsJws(clearToken)
                    .getBody()
                    .getSubject();

             System.out.println("tokenData ==>" + tokenData);

             if (tokenData.equals("admin")) {
                authentication = new UsernamePasswordAuthenticationToken(tokenData, "admin");

                 System.out.println("authentication ==> " + authentication);

            }
        }
        System.out.println(" :( Tokena net");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}


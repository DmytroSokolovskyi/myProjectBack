package com.example.myprojectback.configs;

import com.example.myprojectback.dao.AuthTokenDAO;
import com.example.myprojectback.dto.UserDTO;
import com.example.myprojectback.models.AuthToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

  private  AuthTokenDAO authTokenDAO;

    public LoginFilter(String url,
                       AuthTokenDAO authTokenDAO,
                       AuthenticationManager authenticationManager)
    {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authenticationManager);
        this.authTokenDAO = authTokenDAO;
        System.out.println("loginFilter URL  ==>  " + url);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        ServletInputStream requestBody = request.getInputStream();

        UserDTO userDTO = new ObjectMapper().readValue(requestBody, UserDTO.class);

        System.out.println("attemptAuthentication ==>  " + userDTO);

        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
                );
    }



    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String jwtoken = Jwts.builder()
                .setSubject(authResult.getName())
                .signWith(SignatureAlgorithm.HS512, "yes".getBytes())
//                .setExpiration(new Date(System.currentTimeMillis() + 200000))
                .compact();

        authTokenDAO.save(new AuthToken(jwtoken));
        response.addHeader("Authorization", "Bearer " + jwtoken);
        chain.doFilter(request, response);
    }
}

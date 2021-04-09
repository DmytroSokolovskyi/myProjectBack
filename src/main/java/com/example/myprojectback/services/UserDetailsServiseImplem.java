package com.example.myprojectback.services;


import com.example.myprojectback.dao.UserDAO;
import com.example.myprojectback.models.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiseImplem implements UserDetailsService {

    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        User user = userDAO.findUserByName(username);
        System.out.println(user);
        return user;
    }
}

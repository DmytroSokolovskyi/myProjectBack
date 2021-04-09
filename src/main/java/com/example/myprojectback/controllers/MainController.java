package com.example.myprojectback.controllers;


import com.example.myprojectback.dao.UserDAO;
import com.example.myprojectback.models.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
public class MainController {

    private UserDAO userDAO;
    private PasswordEncoder passwordEncoder;


//    @GetMapping("/")
//    public String home(Principal principal) {
//        System.out.println("home");
//        System.out.println(principal);
//        return "home";
//    }

    @GetMapping("/")
    public List<User> home() {
        System.out.println("home");
        return userDAO.findAll();
    }

    @GetMapping("/user/test")
    public String userhome() {

        return "user home";
    }

    @GetMapping("/admin/test")
    public String adminhome() {
        return "admin home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @PostMapping("/register")

    public void reg(@RequestBody User user) {
        System.out.println(user);
        String encode = passwordEncoder.encode(user.getPass());
        user.setPass(encode);

        System.out.println(user);

        userDAO.save(user);
    }

}

package com.example.myprojectback.dao;

import com.example.myprojectback.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Integer> {
    User findUserByName(String name);
}

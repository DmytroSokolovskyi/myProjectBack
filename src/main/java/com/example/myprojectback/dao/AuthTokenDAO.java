package com.example.myprojectback.dao;

import com.example.myprojectback.models.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthTokenDAO extends JpaRepository<AuthToken, Integer> {

}

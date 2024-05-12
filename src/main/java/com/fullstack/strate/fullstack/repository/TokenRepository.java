package com.fullstack.strate.fullstack.repository;

import com.fullstack.strate.fullstack.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token,Integer> {
    Token findByToken(String token);
}

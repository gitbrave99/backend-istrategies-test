package com.fullstack.strate.fullstack.serviceImplement;

import com.fullstack.strate.fullstack.entity.Token;
import com.fullstack.strate.fullstack.repository.TokenRepository;
import com.fullstack.strate.fullstack.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImplement implements TokenService {

    @Autowired
    TokenRepository tokenRepository;

    @Override
    public Token saveToken(Token token) {
        return this.tokenRepository.save(token);
    }

    @Override
    public Token updateToken(Token token) {
        return this.tokenRepository.save(token);
    }

    @Override
    public Token findToken(String token) {
        return this.tokenRepository.findByToken(token);
    }
}

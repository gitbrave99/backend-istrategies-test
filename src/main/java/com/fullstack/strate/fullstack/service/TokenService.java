package com.fullstack.strate.fullstack.service;

import com.fullstack.strate.fullstack.entity.Token;

public interface TokenService {

    Token saveToken(Token token);
    Token updateToken(Token token);
    Token findToken(String token);

}

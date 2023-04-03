package com.danmag.ecommerce.service.service;

import com.danmag.ecommerce.service.enums.TokenType;
import com.danmag.ecommerce.service.exceptions.CustomNotFoundException;
import com.danmag.ecommerce.service.model.Account;
import com.danmag.ecommerce.service.model.Token;
import com.danmag.ecommerce.service.model.request.AuthenticationRequest;
import com.danmag.ecommerce.service.model.request.RegisterRequest;
import com.danmag.ecommerce.service.model.response.AuthenticationResponse;
import com.danmag.ecommerce.service.repository.AccountsRepository;
import com.danmag.ecommerce.service.repository.TokenRepository;
import com.danmag.ecommerce.service.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {

    private final AccountsRepository accountsRepository;
    private final AuthenticationManager authenticationManager;

    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;


    private final JwtService jwtService;

    @Autowired
    public AuthenticationService(AccountsRepository accountsRepository, AuthenticationManager authenticationManager, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository, JwtService jwtService) {
        this.accountsRepository = accountsRepository;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        Account user = accountsRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new CustomNotFoundException("User not found"));
        String jwtToken = jwtService.generateJwtToken(user);
        saveUserToken(user, jwtToken);
        return new AuthenticationResponse(jwtToken);
    }

    void saveUserToken(Account user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .type(TokenType.BEARER)
                .build();
        tokenRepository.save(token);
    }

    public AuthenticationResponse register(RegisterRequest request) {
        Account user = Account.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        accountsRepository.save(user);
        String jwtToken = jwtService.generateJwtToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
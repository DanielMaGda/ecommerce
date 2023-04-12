package com.danmag.ecommerce.service.service;

import com.danmag.ecommerce.service.dto.request.AuthenticationRequest;
import com.danmag.ecommerce.service.dto.request.RegisterRequest;
import com.danmag.ecommerce.service.dto.response.AuthenticationResponse;
import com.danmag.ecommerce.service.enums.Role;
import com.danmag.ecommerce.service.exceptions.ConflictException;
import com.danmag.ecommerce.service.model.Account;
import com.danmag.ecommerce.service.model.Token;
import com.danmag.ecommerce.service.repository.AccountsRepository;
import com.danmag.ecommerce.service.repository.TokenRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
class AuthenticationServiceTest {
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private AccountsRepository accountsRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private JwtService jwtService;
    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        authenticationService =
                new AuthenticationService(accountsRepository, authenticationManager, tokenRepository, passwordEncoder, jwtService);
    }

    @Test
    void should_return_valid_jwt_token_when_authenticating_valid_user() {
        // Given
        String username = "adam";
        String password = "password123";
        String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        Account account = Account.builder()
                .id(1L)
                .userName(username)
                .build();
        AuthenticationRequest request = AuthenticationRequest.builder()
                .userName(username)
                .password(password)
                .build();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        AuthenticationResponse expectedResponse = new AuthenticationResponse(jwtToken);

        when(authenticationManager.authenticate(authToken)).thenReturn(null); // authentication successful
        when(accountsRepository.findByUserName(username)).thenReturn(Optional.of(account));
        when(jwtService.generateJwtToken(account)).thenReturn(jwtToken);

        // When
        AuthenticationResponse response = authenticationService.authenticate(request);

        // Then

        verify(authenticationManager, times(1)).authenticate(authToken);
        verify(accountsRepository, times(1)).findByUserName(username);
        verify(jwtService, times(1)).generateJwtToken(account);
        verify(tokenRepository, times(1)).save(any(Token.class)); // verify that saveUserToken method is called
        Assertions.assertEquals(expectedResponse.getToken(), response.getToken()); // compare expected and actual response tokens
    }

    @Test
    void should_throw_exception_when_authentication_fails() {
        // Given
        String username = "adam";
        String invalidPassword = "wrongPassword";
        AuthenticationRequest request = AuthenticationRequest.builder()
                .userName(username)
                .password(invalidPassword)
                .build();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, invalidPassword);

        when(authenticationManager.authenticate(authToken)).thenThrow(new BadCredentialsException("Invalid username/password"));

        // When/Then
        assertThrows(AuthenticationException.class, () -> authenticationService.authenticate(request));
    }


    @Test
    void should_register_new_user_and_return_valid_jwt_token() {
        // given
        RegisterRequest request = RegisterRequest.builder()
                .userName("username")
                .password("password")
                .email("email@example.com")
                .role(Role.USER)
                .build();
        when(accountsRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(accountsRepository.existsByUserName(request.getUserName())).thenReturn(false);
        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode(request.getPassword())).thenReturn(encodedPassword);
        String jwtToken = "jwtToken";
        Account user = Account.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .password(encodedPassword)
                .role(request.getRole())
                .build();
        when(jwtService.generateJwtToken(user)).thenReturn(jwtToken);

        // when
        AuthenticationResponse response = authenticationService.register(request);

        // then
        verify(accountsRepository).existsByEmail(request.getEmail());
        verify(accountsRepository).existsByUserName(request.getUserName());
        verify(accountsRepository).save(any(Account.class));
        verify(passwordEncoder).encode(request.getPassword());
        verify(jwtService).generateJwtToken(user);
        assertNotNull(response);
        assertEquals(jwtToken, response.getToken());
    }

    @Test
    void should_throw_ConflictException_if_email_already_exists() {
        // given
        RegisterRequest request = RegisterRequest.builder()
                .userName("testuser")
                .email("test@example.com")
                .password("testpassword")
                .role(Role.USER)
                .build();
        when(accountsRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // when + then
        assertThrows(ConflictException.class, () -> authenticationService.register(request));
    }

    @Test
    void should_throw_ConflictException_if_username_already_taken() {
        // given
        RegisterRequest request = RegisterRequest.builder()
                .userName("testuser")
                .email("test@example.com")
                .password("testpassword")
                .role(Role.USER)
                .build();
        when(accountsRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(accountsRepository.existsByUserName(request.getUserName())).thenReturn(true);

        // when + then
        assertThrows(ConflictException.class, () -> authenticationService.register(request));
    }

}

//    @Test
//    void should_throw_exception_when_username_or_password_is_empty() {
//        // Given
//        String username = "";
//        String password = "password123";
//        AuthenticationRequest request = AuthenticationRequest.builder()
//                .userName(username)
//                .password(password)
//                .build();
//
//        // When/Then
//        assertThrows(IllegalArgumentException.class, () -> authenticationService.authenticate(request));
//    }


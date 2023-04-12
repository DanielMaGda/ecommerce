package com.danmag.ecommerce.service.model;

import com.danmag.ecommerce.service.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {
    @ManyToOne
    @JoinColumn(name = "user_id")
    public Account user;
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String token;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private TokenType type = TokenType.BEARER;
    private boolean revoked;
    private boolean expired;


}
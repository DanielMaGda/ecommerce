package com.danmag.ecommerce.service.model;

import com.danmag.ecommerce.service.dto.AccountDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    @Column(name = "userName")
    private String userName;
    private String email;
    private String password;



    public boolean isEnabled() {
        return true;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_roles",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "userrole_id")
    )
    private Set<UserRole> roles = new HashSet<>();

    public Account(AccountDTO accountDTO) {
        this.id = accountDTO.getId();
        this.email = accountDTO.getEmail();
        this.userName = accountDTO.getUsername();
    }

    @JsonIgnore
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> orderList;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private Cart cart;


}

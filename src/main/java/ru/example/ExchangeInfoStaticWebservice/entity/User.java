package ru.example.ExchangeInfoStaticWebservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import ru.example.ExchangeInfoStaticWebservice.security.Authority;

import java.util.Set;

@Data
@Entity(name="User_App")
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection(targetClass = Authority.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Authority> authorities;

}
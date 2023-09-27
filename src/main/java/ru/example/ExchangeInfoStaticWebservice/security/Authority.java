package ru.example.ExchangeInfoStaticWebservice.security;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    USER,
    ADMIN,
    MODERATOR;

    @Override
    public String getAuthority() {
        return name();
    }
}
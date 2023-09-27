package ru.example.ExchangeInfoStaticWebservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserRegistrationDTO {
    private String username;
    private String password;
}
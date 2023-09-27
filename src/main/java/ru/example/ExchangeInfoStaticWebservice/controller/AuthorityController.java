package ru.example.ExchangeInfoStaticWebservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.example.ExchangeInfoStaticWebservice.configuration.SecurityConfiguration;
import ru.example.ExchangeInfoStaticWebservice.dto.UserRegistrationDTO;
import ru.example.ExchangeInfoStaticWebservice.entity.User;
import ru.example.ExchangeInfoStaticWebservice.security.Authority;
import ru.example.ExchangeInfoStaticWebservice.repository.UserRepository;

import java.util.Collections;

@Controller
public class AuthorityController {

    /**
     *  Контроллер регистрации пользователя
     */

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/sign-up")
    public String getRegisterPage(Model model) {
        model.addAttribute("user", new UserRegistrationDTO());

        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String newUserRegistration(@ModelAttribute UserRegistrationDTO userRegistrationDTO, Model model) {
        var newUser = new User();
        newUser.setUsername(userRegistrationDTO.getUsername());
        newUser.setPassword(SecurityConfiguration.passwordEncoder().encode(userRegistrationDTO.getPassword()));
        newUser.setAuthorities(Collections.singleton(Authority.USER));
        userRepository.save(newUser);

        return "redirect:/registration-access";
    }
}
package ru.example.ExchangeInfoStaticWebservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.example.ExchangeInfoStaticWebservice.entity.SecuritiesDataHub;
import ru.example.ExchangeInfoStaticWebservice.security.UserPrincipal;
import ru.example.ExchangeInfoStaticWebservice.service.SecuritiesDataHubService;

@Controller
@RequestMapping("/datahub")
public class SecuritiesDataHubController {

    /**
     *  Контроллер "Информация о ценных бумагах"
     */

    @Autowired
    private final SecuritiesDataHubService securitiesDataHubService;

    @Autowired
    public SecuritiesDataHubController(SecuritiesDataHubService securitiesDataHubService) {
        this.securitiesDataHubService = securitiesDataHubService;
    }

    @GetMapping
    public String getDatahubList(Model model, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        var user = userPrincipal.getUser();
        Iterable<SecuritiesDataHub> secDataHubList = securitiesDataHubService.getAllSecurities();
        model.addAttribute("securitiesDataHubList", secDataHubList);
        model.addAttribute("securitiesDataHub", new SecuritiesDataHub());

        return "datahub";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        SecuritiesDataHub securitiesDataHub = securitiesDataHubService.getSecuritiesById(id);
        model.addAttribute("securitiesDataHub", securitiesDataHub);

        return "edit";
    }

    @PostMapping("/update/{id}")
    public String updateSecuritiForm(@PathVariable("id") Long id, @ModelAttribute("securitiesDataHub") SecuritiesDataHub securitiesDataHub) {
        securitiesDataHubService.updateSecurities(id, securitiesDataHub);

        return "redirect:/datahub";
    }
}

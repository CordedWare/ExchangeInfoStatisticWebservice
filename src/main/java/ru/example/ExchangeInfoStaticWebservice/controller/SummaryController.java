package ru.example.ExchangeInfoStaticWebservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.example.ExchangeInfoStaticWebservice.entity.SecuritiesDataHub;
import ru.example.ExchangeInfoStaticWebservice.entity.TradeHistoryTracker;
import ru.example.ExchangeInfoStaticWebservice.repository.SecuritiesDataHubRepository;
import ru.example.ExchangeInfoStaticWebservice.repository.TradeHistoryTrackerRepository;
import ru.example.ExchangeInfoStaticWebservice.security.UserPrincipal;
import ru.example.ExchangeInfoStaticWebservice.service.SecuritiesDataHubService;
import ru.example.ExchangeInfoStaticWebservice.service.TradeHistoryTrackerService;

import java.util.List;

@Controller
@RequestMapping("/summary")
public class SummaryController {

    /**
     *  Контроллер "Сводные данные"
     */

    @Autowired
    private final SecuritiesDataHubService securitiesDataHubService;
    @Autowired
    private final TradeHistoryTrackerService tradeHistoryTrackerService;
    @Autowired
    private final SecuritiesDataHubRepository securitiesDataHubRepository;
    @Autowired
    private final TradeHistoryTrackerRepository tradeHistoryTrackerRepository;

    public SummaryController(SecuritiesDataHubService securitiesDataHubService, TradeHistoryTrackerService tradeHistoryTrackerService, SecuritiesDataHubRepository securitiesDataHubRepository, TradeHistoryTrackerRepository tradeHistoryTrackerRepository) {
        this.securitiesDataHubService = securitiesDataHubService;
        this.tradeHistoryTrackerService = tradeHistoryTrackerService;
        this.securitiesDataHubRepository = securitiesDataHubRepository;
        this.tradeHistoryTrackerRepository = tradeHistoryTrackerRepository;
    }

    @GetMapping
    public String getSummaryList(Model model, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        var user = userPrincipal.getUser();
        Iterable<SecuritiesDataHub> secDataHubList = securitiesDataHubService.getAllSecurities();
        Iterable<TradeHistoryTracker> tradeHistoryTrackerList = tradeHistoryTrackerService.getAllTradeHistory();
        model.addAttribute("securitiesDataHubList", secDataHubList);
        model.addAttribute("tradeHistoryTrackerList", tradeHistoryTrackerList);
        model.addAttribute("securitiesDataHub", new SecuritiesDataHub());
        model.addAttribute("tradeHistoryTracker", new TradeHistoryTracker());

        return "summary";
    }

    @PostMapping("/{id}/delete")
    public String deleteSummaryEntry(@PathVariable Long id) {
        SecuritiesDataHub securitiesDataHub = securitiesDataHubService.getById(id);
        if (securitiesDataHub != null) {
            List<TradeHistoryTracker> tradeHistoryTrackers = tradeHistoryTrackerService.getBySecid(securitiesDataHub);
            tradeHistoryTrackers.forEach(tradeHistoryTrackerService::delete); // удаляем вначале объект истории ссылающийся на бумаги
            securitiesDataHubService.delete(securitiesDataHub);

            return "redirect:/summary";
        } else {

            return "error";
        }
    }

    @PostMapping("/deleteAll")
    public String deleteAllEntries() {
        tradeHistoryTrackerRepository.deleteAll();
        securitiesDataHubRepository.deleteAll();

        return "redirect:/summary";
    }
}

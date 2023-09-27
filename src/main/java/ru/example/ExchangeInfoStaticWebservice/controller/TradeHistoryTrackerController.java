package ru.example.ExchangeInfoStaticWebservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.example.ExchangeInfoStaticWebservice.entity.TradeHistoryTracker;
import ru.example.ExchangeInfoStaticWebservice.security.UserPrincipal;
import ru.example.ExchangeInfoStaticWebservice.service.TradeHistoryTrackerService;

@Controller
@RequestMapping("/tradehistory")
public class TradeHistoryTrackerController {

    /**
     *  Контроллер "История торгов за произвольную дату"
     */

    @Autowired
    private final TradeHistoryTrackerService tradeHistoryTrackerService;

    public TradeHistoryTrackerController(TradeHistoryTrackerService tradeHistoryTrackerService) {
        this.tradeHistoryTrackerService = tradeHistoryTrackerService;
    }

    @GetMapping
    public String getTradeHistoryList(Model model, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Iterable<TradeHistoryTracker> tradeHistoryTrackerList = tradeHistoryTrackerService.getAllTradeHistory();
        model.addAttribute("tradeHistoryTrackerList", tradeHistoryTrackerList);
        model.addAttribute("tradeHistoryTracker", new TradeHistoryTracker());

        return "tradehistory";
    }
}

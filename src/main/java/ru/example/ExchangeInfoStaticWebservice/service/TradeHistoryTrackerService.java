package ru.example.ExchangeInfoStaticWebservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.example.ExchangeInfoStaticWebservice.entity.SecuritiesDataHub;
import ru.example.ExchangeInfoStaticWebservice.entity.TradeHistoryTracker;
import ru.example.ExchangeInfoStaticWebservice.repository.TradeHistoryTrackerRepository;

import java.util.List;

@Service
public class TradeHistoryTrackerService {

    @Autowired
    private TradeHistoryTrackerRepository tradeHistoryTrackerRepository;

    public Iterable<TradeHistoryTracker> getAllTradeHistory() {

        return tradeHistoryTrackerRepository.findAll();
    }

    public List<TradeHistoryTracker> getBySecid(SecuritiesDataHub securitiesDataHub) {

        return tradeHistoryTrackerRepository.findBySecid(securitiesDataHub);
    }


    public void delete(TradeHistoryTracker tradeHistoryTracker) {
        tradeHistoryTrackerRepository.delete(tradeHistoryTracker);
    }

    public void deleteBySecidIn(List<Long> tradeHistoryTrackerIds) {
        tradeHistoryTrackerRepository.deleteAllByIdInBatch(tradeHistoryTrackerIds);
    }
}

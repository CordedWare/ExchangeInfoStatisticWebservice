package ru.example.ExchangeInfoStaticWebservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.ExchangeInfoStaticWebservice.entity.SecuritiesDataHub;
import ru.example.ExchangeInfoStaticWebservice.entity.TradeHistoryTracker;
import ru.example.ExchangeInfoStaticWebservice.entity.User;

import java.util.List;

@Repository
public interface TradeHistoryTrackerRepository extends JpaRepository<TradeHistoryTracker, Long> {

    void deleteAll();
    List<TradeHistoryTracker> findAllByNumtrades(String name);
    List<TradeHistoryTracker> findAllBySecid(SecuritiesDataHub secid);

    List<TradeHistoryTracker> findBySecid(SecuritiesDataHub secid);
}

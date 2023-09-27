package ru.example.ExchangeInfoStaticWebservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.ExchangeInfoStaticWebservice.entity.SecuritiesDataHub;

import java.util.List;

@Repository
public interface SecuritiesDataHubRepository extends JpaRepository<SecuritiesDataHub, Long> {

    void deleteAll();
    List<SecuritiesDataHub> findByRegnumber(String name);

    SecuritiesDataHub findBySecid(String secid);
}

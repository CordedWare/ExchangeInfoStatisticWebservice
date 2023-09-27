package ru.example.ExchangeInfoStaticWebservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.example.ExchangeInfoStaticWebservice.entity.SecuritiesDataHub;
import ru.example.ExchangeInfoStaticWebservice.repository.SecuritiesDataHubRepository;

@Service
public class SecuritiesDataHubService {

    @Autowired
    private final SecuritiesDataHubRepository securitiesDataHubRepository;

    @Autowired
    SecuritiesDataHubService(SecuritiesDataHubRepository securitiesDataHubRepository) {
        this.securitiesDataHubRepository = securitiesDataHubRepository;
    }

    public Iterable<SecuritiesDataHub> getAllSecurities() {

        return securitiesDataHubRepository.findAll();
    }

    public SecuritiesDataHub getById(Long id) {

        return securitiesDataHubRepository.getById(id);
    }

    public void delete(SecuritiesDataHub securitiesDataHub) {
        securitiesDataHubRepository.delete(securitiesDataHub);
    }

    public void updateSecurities(Long id, SecuritiesDataHub securitiesDataHub) {
        SecuritiesDataHub existingSecurities = securitiesDataHubRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ценные бумаги с id " + id + " не найдены"));
        existingSecurities.setRegnumber(securitiesDataHub.getRegnumber());
        existingSecurities.setEmitentTitle(securitiesDataHub.getEmitentTitle());

        securitiesDataHubRepository.save(existingSecurities);
    }

    public SecuritiesDataHub getSecuritiesById(Long id) {

        return securitiesDataHubRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ценные бумаги с id " + id + " не найдены"));
    }
}

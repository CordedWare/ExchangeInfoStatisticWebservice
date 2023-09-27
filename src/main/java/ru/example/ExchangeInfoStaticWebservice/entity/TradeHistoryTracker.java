package ru.example.ExchangeInfoStaticWebservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class TradeHistoryTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "secid_id", referencedColumnName = "id", nullable = false)
    private SecuritiesDataHub secid;
    @Column(name = "trade_date", columnDefinition = "DATE")
    private LocalDate tradedate;
    private String numtrades;
    private Double open;
    private Double close;
}

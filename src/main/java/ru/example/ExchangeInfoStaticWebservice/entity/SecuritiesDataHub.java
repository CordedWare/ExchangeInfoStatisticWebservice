package ru.example.ExchangeInfoStaticWebservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class SecuritiesDataHub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String secid;
    private String regnumber;
    @Column(name = "shortname")
    private String name;
    @Column(name = "emitent_title")
    private String emitentTitle;
}

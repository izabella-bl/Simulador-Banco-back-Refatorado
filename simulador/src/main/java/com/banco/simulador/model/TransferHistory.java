package com.banco.simulador.model;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "history",schema = "izabella_barros_lopes")
public class TransferHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime created_At;
    private LocalDateTime updated_At;
    private String description;
    private BigDecimal value;
    private BigDecimal balance;
    private int type;

    @ManyToOne
    private Users users;

}

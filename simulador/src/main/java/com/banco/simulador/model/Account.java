package com.banco.simulador.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


@Setter
@Getter
@Entity
@Table(name = "accounts",schema = "izabella_barros_lopes")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
    private BigDecimal balance;


}

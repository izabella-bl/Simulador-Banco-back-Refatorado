package com.banco.simulador.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@Entity
@Table(name = "transactions",schema = "izabella_barros_lopes")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long originId;
	private String recipientCpf;
	private double value;
	private char type;
	private String description;
	private LocalDate createdAt;
	private LocalDateTime updated_At;
	private byte status;


	public Transaction(Long originId, String recipientCpf, double transferValue,char type, byte status,String description) {
		this.originId = originId;
		this.recipientCpf = recipientCpf;
		this.value = transferValue;
		this.type = type;
		this.status = status;
		this.createdAt = LocalDate.now();
		this.updated_At = LocalDateTime.now();
		
	}

	public Transaction (){

	}

}

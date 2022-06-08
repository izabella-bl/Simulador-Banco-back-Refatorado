package com.banco.simulador.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banco.simulador.model.TransferHistory;
import com.banco.simulador.model.Users;
import com.banco.simulador.repository.HistoryRepository;

@Service
public class HistoryService {

	@Autowired
	private HistoryRepository historyRepository;

	public void saveHistory(Users originUser, Users destinyUser, BigDecimal transferValue) {
		TransferHistory originHistory = new TransferHistory();
		TransferHistory destinyHistory = new TransferHistory();
		try {

			LocalDateTime dataAtual = LocalDateTime.now();
			originHistory.setCreated_At(dataAtual);
			originHistory.setDescription(
					"Tranferencia para:" + destinyUser.getFirstName() + " " + destinyUser.getLastName());
			originHistory.setBalance(originUser.getAccount().getBalance());
			originHistory.setUsers(originUser);
			originHistory.setValue(transferValue);
			originHistory.setType(1);

			destinyHistory.setCreated_At(dataAtual);
			destinyHistory.setDescription("Recebido de:" + originUser.getFirstName() + " " + originUser.getLastName());
			destinyHistory.setBalance(destinyUser.getAccount().getBalance());
			destinyHistory.setUsers(destinyUser);
			destinyHistory.setValue(transferValue);
			destinyHistory.setType(2);

			historyRepository.save(originHistory);
			historyRepository.save(destinyHistory);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public List<TransferHistory> searchMonthHistory(Integer month, Long userId) {

		return historyRepository.findByMonth(month, userId);

	}
}

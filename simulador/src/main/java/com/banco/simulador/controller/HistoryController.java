package com.banco.simulador.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banco.simulador.model.TransferHistory;
import com.banco.simulador.repository.HistoryRepository;
import com.banco.simulador.services.HistoryService;

@RestController
public class HistoryController {

    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private HistoryService historicService;



    @GetMapping("/history")
    public List<TransferHistory> history(@RequestParam Long userId) {
        return (List<TransferHistory>) historyRepository.findByUsers_Id(userId);
    }

    @GetMapping("/history/month")
    public List<TransferHistory> monthHistory(@RequestParam int month, @RequestParam Long userId) {
        return  historicService.searchMonthHistory(month, userId);
    }
}

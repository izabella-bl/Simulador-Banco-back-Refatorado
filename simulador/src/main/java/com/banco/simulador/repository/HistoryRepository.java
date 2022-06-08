package com.banco.simulador.repository;

import com.banco.simulador.model.TransferHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface HistoryRepository extends PagingAndSortingRepository<TransferHistory,Long> {

    List<TransferHistory> findByUsers_Id(Long userId);

    @Query("SELECT h FROM TransferHistory h  WHERE MONTH(h.created_At) = ?1 AND h.users.id = ?2")
    List<TransferHistory> findByMonth(Integer month, Long userId);
}

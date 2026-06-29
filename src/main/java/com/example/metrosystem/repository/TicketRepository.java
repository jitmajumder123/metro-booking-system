package com.example.metrosystem.repository;

import com.example.metrosystem.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUserIdOrderByBookedAtDesc(Long userId);
}

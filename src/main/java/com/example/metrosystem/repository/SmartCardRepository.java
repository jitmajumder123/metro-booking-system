package com.example.metrosystem.repository;

import com.example.metrosystem.entity.SmartCard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SmartCardRepository extends JpaRepository<SmartCard, Long> {
    Optional<SmartCard> findByUserId(Long userId);
}

package com.example.metrosystem.repository;

import com.example.metrosystem.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long> {
}

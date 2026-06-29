package com.example.metrosystem.repository;

import com.example.metrosystem.entity.MetroRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MetroRouteRepository extends JpaRepository<MetroRoute, Long> {
    List<MetroRoute> findBySourceIdAndDestinationId(Long sourceId, Long destinationId);
    boolean existsBySourceIdOrDestinationId(Long sourceId, Long destinationId);
}

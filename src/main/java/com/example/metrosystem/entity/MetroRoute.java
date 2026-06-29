package com.example.metrosystem.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class MetroRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Station source;

    @ManyToOne(optional = false)
    private Station destination;

    private int distanceKm;
    private int travelMinutes;
    private BigDecimal fare;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Station getSource() { return source; }
    public void setSource(Station source) { this.source = source; }
    public Station getDestination() { return destination; }
    public void setDestination(Station destination) { this.destination = destination; }
    public int getDistanceKm() { return distanceKm; }
    public void setDistanceKm(int distanceKm) { this.distanceKm = distanceKm; }
    public int getTravelMinutes() { return travelMinutes; }
    public void setTravelMinutes(int travelMinutes) { this.travelMinutes = travelMinutes; }
    public BigDecimal getFare() { return fare; }
    public void setFare(BigDecimal fare) { this.fare = fare; }
}

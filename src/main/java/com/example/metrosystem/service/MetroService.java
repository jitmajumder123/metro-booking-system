package com.example.metrosystem.service;

import com.example.metrosystem.entity.*;
import com.example.metrosystem.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MetroService {
    private final StationRepository stationRepository;
    private final MetroRouteRepository routeRepository;
    private final TicketRepository ticketRepository;
    private final SmartCardRepository smartCardRepository;

    public MetroService(
            StationRepository stationRepository,
            MetroRouteRepository routeRepository,
            TicketRepository ticketRepository,
            SmartCardRepository smartCardRepository
    ) {
        this.stationRepository = stationRepository;
        this.routeRepository = routeRepository;
        this.ticketRepository = ticketRepository;
        this.smartCardRepository = smartCardRepository;
    }

    public List<Station> stations() {
        return stationRepository.findAll();
    }

    public List<MetroRoute> routes() {
        return routeRepository.findAll();
    }

    public List<MetroRoute> searchRoutes(Long sourceId, Long destinationId) {
        return routeRepository.findBySourceIdAndDestinationId(sourceId, destinationId);
    }

    public Ticket bookTicket(User user, Long routeId) {
        MetroRoute route = routeRepository.findById(routeId)
                .orElseThrow(() -> new IllegalArgumentException("Route not found"));

        SmartCard card = getOrCreateCard(user);
        if (card.getBalance().compareTo(route.getFare()) < 0) {
            throw new IllegalArgumentException("Insufficient smart card balance");
        }

        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setRoute(route);
        ticket.setFare(route.getFare());
        ticket.setBookedAt(LocalDateTime.now());

        card.setBalance(card.getBalance().subtract(route.getFare()));
        smartCardRepository.save(card);

        return ticketRepository.save(ticket);
    }

    public List<Ticket> tickets(Long userId) {
        return ticketRepository.findByUserIdOrderByBookedAtDesc(userId);
    }

    public SmartCard getOrCreateCard(User user) {
        return smartCardRepository.findByUserId(user.getId()).orElseGet(() -> {
            SmartCard card = new SmartCard();
            card.setUser(user);
            card.setCardNumber("MC" + System.currentTimeMillis());
            card.setBalance(BigDecimal.ZERO);
            return smartCardRepository.save(card);
        });
    }

    public SmartCard recharge(User user, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        SmartCard card = getOrCreateCard(user);
        card.setBalance(card.getBalance().add(amount));
        return smartCardRepository.save(card);
    }
}
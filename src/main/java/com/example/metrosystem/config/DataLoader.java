package com.example.metrosystem.config;

import com.example.metrosystem.entity.MetroRoute;
import com.example.metrosystem.entity.Station;
import com.example.metrosystem.entity.User;
import com.example.metrosystem.repository.MetroRouteRepository;
import com.example.metrosystem.repository.StationRepository;
import com.example.metrosystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {
    private final StationRepository stationRepository;
    private final MetroRouteRepository routeRepository;
    private final UserRepository userRepository;

    public DataLoader(StationRepository stationRepository, MetroRouteRepository routeRepository, UserRepository userRepository) {
        this.stationRepository = stationRepository;
        this.routeRepository = routeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (stationRepository.count() > 0) {
            return;
        }

        Station central = station("Central", "Blue Line");
        Station cityPark = station("City Park", "Blue Line");
        Station airport = station("Airport", "Airport Line");
        Station university = station("University", "Green Line");

        stationRepository.save(central);
        stationRepository.save(cityPark);
        stationRepository.save(airport);
        stationRepository.save(university);

        routeRepository.save(route(central, cityPark, 6, 12, "20"));
        routeRepository.save(route(cityPark, airport, 18, 35, "55"));
        routeRepository.save(route(central, airport, 24, 42, "70"));
        routeRepository.save(route(central, university, 10, 20, "30"));

        User admin = new User();
        admin.setName("Admin");
        admin.setEmail("admin@metro.com");
        admin.setPassword("admin123");
        admin.setRole("ADMIN");
        userRepository.save(admin);
    }

    private Station station(String name, String line) {
        Station station = new Station();
        station.setName(name);
        station.setLineName(line);
        return station;
    }

    private MetroRoute route(Station source, Station destination, int distance, int minutes, String fare) {
        MetroRoute route = new MetroRoute();
        route.setSource(source);
        route.setDestination(destination);
        route.setDistanceKm(distance);
        route.setTravelMinutes(minutes);
        route.setFare(new BigDecimal(fare));
        return route;
    }
}

package com.example.metrosystem.controller;


import com.example.metrosystem.entity.MetroRoute;
import com.example.metrosystem.entity.Station;
import com.example.metrosystem.entity.User;
import com.example.metrosystem.repository.MetroRouteRepository;
import com.example.metrosystem.repository.StationRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final StationRepository stationRepository;
    private final MetroRouteRepository routeRepository;

    public AdminController(StationRepository stationRepository, MetroRouteRepository routeRepository) {
        this.stationRepository = stationRepository;
        this.routeRepository = routeRepository;
    }

    @GetMapping
    public String admin(HttpSession session, Model model) {
        String redirect = requireAdmin(session);
        if (redirect != null) {
            return redirect;
        }

    }



    @PostMapping("/stations/{id}/update")
    public String updateStation(@PathVariable Long id, @RequestParam String name, @RequestParam String lineName, HttpSession session, RedirectAttributes redirectAttributes) {
        String redirect = requireAdmin(session);
        if (redirect != null) {
            return redirect;
        }
        Station station = stationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Station not found"));
        station.setName(name);
        station.setLineName(lineName);
        stationRepository.save(station);
        redirectAttributes.addFlashAttribute("success", "Station updated successfully");
        return "redirect:/admin";
    }

    @PostMapping("/stations/{id}/delete")
    public String deleteStation(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        String redirect = requireAdmin(session);
        if (redirect != null) {
            return redirect;
        }
        if (routeRepository.existsBySourceIdOrDestinationId(id, id)) {
            redirectAttributes.addFlashAttribute("error", "Delete linked routes before deleting this station");
            return "redirect:/admin";
        }
       

    @PostMapping("/routes")
    public String addRoute(@RequestParam Long sourceId, @RequestParam Long destinationId, @RequestParam int distanceKm, @RequestParam int travelMinutes, @RequestParam BigDecimal fare, HttpSession session) {
        String redirect = requireAdmin(session);
        if (redirect != null) {
            return redirect;
        }
        Station source = stationRepository.findById(sourceId).orElseThrow(() -> new IllegalArgumentException("Source station not found"));
        Station destination = stationRepository.findById(destinationId).orElseThrow(() -> new IllegalArgumentException("Destination station not found"));
        MetroRoute route = new MetroRoute();
        route.setSource(source);
        route.setDestination(destination);
        route.setDistanceKm(distanceKm);
        route.setTravelMinutes(travelMinutes);
        route.setFare(fare);
        routeRepository.save(route);
        return "redirect:/admin";
    }

    @PostMapping("/routes/{id}/update")
    public String updateRoute(
            @PathVariable Long id,
            @RequestParam Long sourceId,
          
    ) {
        String redirect = requireAdmin(session);
        if (redirect != null) {
            return redirect;
        }
        MetroRoute route = routeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Route not found"));
        route.setSource(stationRepository.findById(sourceId).orElseThrow(() -> new IllegalArgumentException("Source station not found")));
        route.setDestination(stationRepository.findById(destinationId).orElseThrow(() -> new IllegalArgumentException("Destination station not found")));
        route.setDistanceKm(distanceKm);
        route.setTravelMinutes(travelMinutes);
        route.setFare(fare);
        routeRepository.save(route);
        redirectAttributes.addFlashAttribute("success", "Route updated successfully");
        return "redirect:/admin";
    }

    @PostMapping("/routes/{id}/delete")
    public String deleteRoute(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        String redirect = requireAdmin(session);
        if (redirect != null) {
            return redirect;
        }
        try {
            routeRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Route deleted successfully");
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", "This route is used in ticket history and cannot be deleted");
        }
        return "redirect:/admin";
    }

    private String requireAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
       
    }
}

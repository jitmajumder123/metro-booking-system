package com.example.metrosystem.controller;

import com.example.metrosystem.entity.User;
import com.example.metrosystem.service.MetroService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    private final MetroService metroService;

    public DashboardController(MetroService metroService) {
        this.metroService = metroService;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = requireUser(session);
        model.addAttribute("user", user);
        model.addAttribute("tickets", metroService.tickets(user.getId()));
        model.addAttribute("card", metroService.getOrCreateCard(user));
        return "dashboard";
    }

    private User requireUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new IllegalStateException("Please login first");
        }
        return user;
    }
}

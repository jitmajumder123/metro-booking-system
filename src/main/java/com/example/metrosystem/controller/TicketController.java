package com.example.metrosystem.controller;

import com.example.metrosystem.entity.User;
import com.example.metrosystem.service.MetroService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final MetroService metroService;

    public TicketController(MetroService metroService) {
        this.metroService = metroService;
    }

    @PostMapping("/book")
    public String book(@RequestParam Long routeId, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        try {
            metroService.bookTicket(user, routeId);
            redirectAttributes.addFlashAttribute("success", "Ticket booked successfully");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/dashboard";
    }
}

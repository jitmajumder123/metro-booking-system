package com.example.metrosystem.controller;

import com.example.metrosystem.entity.User;
import com.example.metrosystem.service.AuthService;
import com.example.metrosystem.service.MetroService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    private final AuthService authService;
    private final MetroService metroService;

    public HomeController(AuthService authService, MetroService metroService) {
        this.authService = authService;
        this.metroService = metroService;
    }

    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("stations", metroService.stations());
        model.addAttribute("routes", metroService.routes());
        return "index";
    }

    @GetMapping("/register")
    public String registerPage(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/dashboard";
        }
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String name, @RequestParam String email, @RequestParam String password, HttpSession session) {
        User user = authService.register(name, email, password);
        session.setAttribute("user", user);
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/dashboard";
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        try {
            User user = authService.login(email, password);
            session.setAttribute("user", user);
            return "redirect:/dashboard";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}

package com.example.metrosystem.controller;

import com.example.metrosystem.entity.User;
import com.example.metrosystem.service.MetroService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/smartcard")
public class SmartCardController {
    private final MetroService metroService;

    public SmartCardController(MetroService metroService) {
        this.metroService = metroService;
    }

    @PostMapping("/recharge")
    public String recharge(@RequestParam BigDecimal amount, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        try {
            metroService.recharge(user, amount);
            redirectAttributes.addFlashAttribute("success", "Smart card recharged successfully");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/dashboard";
    }
}

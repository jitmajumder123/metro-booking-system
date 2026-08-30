package com.example.metrosystem.controller;

import com.example.metrosystem.service.MetroService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/routes")
public class RouteController {
    private final MetroService metroService;

    public RouteController(MetroService metroService) {
        this.metroService = metroService;
    }


    
}

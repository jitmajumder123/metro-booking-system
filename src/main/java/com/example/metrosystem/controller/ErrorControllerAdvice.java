package com.example.metrosystem.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorControllerAdvice {
    @ExceptionHandler(IllegalStateException.class)
    public String handleLoginRequired(IllegalStateException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "login";
    }
}

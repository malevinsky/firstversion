package com.example.alpha.bank.controllers;

import com.example.alpha.bank.BankApplication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("/")
    public String greeting(Model model) {
        //BankApplication app = new BankApplication();
        model.addAttribute("name", "Teos main page");
        return "home";
    }
    @GetMapping("/win")
    public String rich(Model model) {
        model.addAttribute("name", "Teos main page");
        return "rich";
    }

    @GetMapping("/lose")
    public String poor(Model model) {
        model.addAttribute("name", "Teos main page");
        return "poor";
    }
}

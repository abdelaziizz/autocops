package com.mdp.autocops.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SecurityController {

    @GetMapping("/login")
    public String loginPage() {
        return "views/login";
    }
}

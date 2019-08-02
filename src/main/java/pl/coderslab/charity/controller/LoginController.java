package pl.coderslab.charity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String logged() {
        return "redirect:/login?success";
    }

    @GetMapping("/logout")
    public String logoutProceed() {
        return "user/login";
    }

    @PostMapping("/logout")
    public String logoutProceeded() {
        return "redirect:/login?logout";
    }

}

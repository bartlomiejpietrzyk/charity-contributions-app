package pl.coderslab.charity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String logged() {
        return "user/login";
    }

    @GetMapping("/logout")
    public String logoutProceed() {
        return "login";
    }

    @PostMapping("/logout")
    public String logoutProceeded() {
        return "redirect:/login?logout";
    }

}

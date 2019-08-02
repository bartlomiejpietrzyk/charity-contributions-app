package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.repository.UserRepository;

@Controller
@RequestMapping("/login")
public class LoginController {
    private UserRepository userRepository;

    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

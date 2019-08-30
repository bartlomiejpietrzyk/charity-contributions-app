package pl.bartlomiejpietrzyk.charity.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String showLoginForm() {
        return "user/login";
    }

    @GetMapping("/logout")
    public String logoutProceed() {
        return "redirect:/login?logout";
    }
}

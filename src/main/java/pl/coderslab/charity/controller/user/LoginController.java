package pl.coderslab.charity.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.repository.UserRepository;

@Controller
public class LoginController {
    private final UserRepository userRepository;

    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String logged(@ModelAttribute("currentUser") User user) {
        if (!user.getEnabled()) {
            return "redirect:/login?disable";
        }
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

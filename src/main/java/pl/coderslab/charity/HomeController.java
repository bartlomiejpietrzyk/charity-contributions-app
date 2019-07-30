package pl.coderslab.charity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {

    @RequestMapping("/")
    public String homeAction(Model model) {
        return "index";
    }

    @RequestMapping("/login")
    public String showLoginForm(Model model) {
        return "login";
    }

    @RequestMapping("/registration")
    public String showRegistrationForm(Model model) {
        return "registration";
    }

    @RequestMapping("/form")
    public String showForm(Model model) {
        return "form";
    }
}

package pl.coderslab.charity.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.coderslab.charity.repository.UserRepository;

@Controller
public class HomeController {
    private UserRepository userRepository;

    @Autowired
    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String homeAction() {
        return "user/index";
    }

    @GetMapping("/403")
    public String error403() {
        return "user/403";
    }

    @GetMapping("/404")
    public String error404() {
        return "user/404";
    }

    @GetMapping("/500")
    public String error500() {
        return "user/500";
    }
}

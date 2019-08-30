package pl.bartlomiejpietrzyk.charity.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.bartlomiejpietrzyk.charity.repository.UserRepository;
import pl.bartlomiejpietrzyk.charity.service.MessageService;

@Controller
public class HomeController {
    private final UserRepository userRepository;
    private final MessageService messageService;

    @Autowired
    public HomeController(UserRepository userRepository, MessageService messageService) {
        this.userRepository = userRepository;
        this.messageService = messageService;
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

//    @GetMapping("/500")
//    public String error500() {
//        return "user/500";
//    }
}

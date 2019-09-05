package pl.bartlomiejpietrzyk.charity.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.bartlomiejpietrzyk.charity.service.UserService;

@Controller
public class ActivationController {
    private final UserService userService;

    @Autowired
    public ActivationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/account")
    public String enableUser(@RequestParam String uuid) {
        userService.enableUser(uuid);
        return "redirect:/login?active";
    }

}

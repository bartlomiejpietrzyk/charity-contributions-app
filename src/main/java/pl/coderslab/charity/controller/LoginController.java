package pl.coderslab.charity.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping
    public String showLoginForm() {
        return "login";
    }

    @PostMapping
    public String logged() {
        return "user/login";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String userInfo(@AuthenticationPrincipal UserDetails customUser) {
        System.out.println("customUsSer class {} " + customUser.getClass());
        return "You are logged as " + customUser;
    }

}

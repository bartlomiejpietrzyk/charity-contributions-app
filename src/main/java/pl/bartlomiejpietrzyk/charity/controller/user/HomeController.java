package pl.bartlomiejpietrzyk.charity.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
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

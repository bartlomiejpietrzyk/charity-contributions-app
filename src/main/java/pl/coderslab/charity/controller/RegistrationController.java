package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.service.UserServiceImpl;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserServiceImpl userService;


    @GetMapping
    public String userRegistration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping
    public String userRegistration(@ModelAttribute("user") @Valid User user,
                                   BindingResult result) {

        if (userService.findByEmail(user.getEmail()) != null) {
            return "redirect:/registration?exist";
        }
        if (result.hasErrors()) {
            return "redirect:/registration?failed";
        }
        userService.saveUser(user);
        return "redirect:/registration?success";
    }
}

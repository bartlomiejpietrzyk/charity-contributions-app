package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.dto.UserRegistrationDto;
import pl.coderslab.charity.repository.UserRepository;
import pl.coderslab.charity.service.UserRegistrationService;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private UserRepository userRepository;
    private final UserRegistrationService userRegistrationService;

    @Autowired
    public RegistrationController(UserRepository userRepository, UserRegistrationService userRegistrationService) {
        this.userRepository = userRepository;
        this.userRegistrationService = userRegistrationService;
    }

    @GetMapping
    public String userRegistration(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "user/registration";
    }

    @PostMapping
    public String userRegistration(@ModelAttribute("user") @Valid UserRegistrationDto user,
                                   BindingResult result) {
//        if (user.password != user.passwordConfirm) {
//            return "redirect:/registration?notMatch";
//        }
        if (userRegistrationService.findByEmail(user.getEmail()) != null) {
            return "redirect:/registration?exist";
        }
        if (result.hasErrors()) {
            return "redirect:/registration?failed";
        }

        userRegistrationService.saveUser(user);
        return "redirect:/registration?success";
    }

}

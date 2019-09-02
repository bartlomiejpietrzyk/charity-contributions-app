package pl.bartlomiejpietrzyk.charity.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bartlomiejpietrzyk.charity.dto.UserRegistrationDto;
import pl.bartlomiejpietrzyk.charity.service.UserRegistrationService;
import pl.bartlomiejpietrzyk.charity.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;
    private final UserRegistrationService userRegistrationService;

    @Autowired
    public RegistrationController(UserService userService, UserRegistrationService userRegistrationService) {
        this.userService = userService;
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
        if (!user.password.equals(user.passwordConfirm)) {
            return "redirect:/registration?notMatch";
        }
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

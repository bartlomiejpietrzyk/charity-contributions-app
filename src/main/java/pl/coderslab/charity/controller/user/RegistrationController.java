package pl.coderslab.charity.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.dto.UserRegistrationDto;
import pl.coderslab.charity.service.UserRegistrationService;
import pl.coderslab.charity.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private UserService userService;
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

    @GetMapping("/{uuid}/enable")
    public String enableUser(@PathVariable String uuid) {
        userService.enableUser(uuid);
        return "redirect:/login?uuid=" + uuid + "&active";
    }
}
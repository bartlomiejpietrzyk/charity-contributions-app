package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.repository.RoleRepository;
import pl.coderslab.charity.repository.UserRepository;
import pl.coderslab.charity.service.UserRegistrationService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/admin/administrators")
public class AdminAdministratorController {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private final UserRegistrationService userRegistrationService;

    @Autowired
    public AdminAdministratorController(UserRepository userRepository, RoleRepository roleRepository, UserRegistrationService userRegistrationService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRegistrationService = userRegistrationService;
    }

    @GetMapping
    public String showAdministratorsList() {
        return "admin/administratorsList";
    }

    @ModelAttribute("administratorList")
    public List<User> administratorList() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRoles().contains(roleRepository.getOne(2)))
                .collect(Collectors.toList());
    }

    @GetMapping("/add")
    public String userRegistration(Model model) {
        model.addAttribute("administrator", new User());
        return "admin/administratorsAdd";
    }

    @PostMapping("/add")
    public String adminRegistration(@ModelAttribute("user") @Valid User user,
                                   BindingResult result) {
        if (userRegistrationService.findByEmail(user.getEmail()) != null) {
            return "redirect:/admin/administrators/add?exist";
        }
        if (result.hasErrors()) {
            return "redirect:/admin/administrators/add?failed";
        }

        userRegistrationService.saveAdmin(user);
        return "redirect:/admin/administrators?addSuccess";
    }

    @GetMapping("/edit")
    public String showAdministratorEditForm(@RequestParam Long id, Model model) {
        model.addAttribute("administrator", userRepository.getOne(id));
        return "admin/administratorsEdit";
    }

    @PostMapping("/edit")
    public String proceedAdministratorEditForm(@ModelAttribute("administrator") @Valid User user,
                                               BindingResult result) {

        if (result.hasErrors()) {
            return "redirect:/admin/administrators/edit?id=" + user.getId() + "&?failed";
        }
        userRepository.save(user);
        return "redirect:/admin/administrators?edited";
        //TOOD UPDATE ADMIN
    }
}

package pl.bartlomiejpietrzyk.charity.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.bartlomiejpietrzyk.charity.entity.User;
import pl.bartlomiejpietrzyk.charity.repository.UserRepository;
import pl.bartlomiejpietrzyk.charity.service.UserRegistrationService;

import javax.validation.Valid;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/admin/users")
public class AdminUserController {
    private final UserRepository userRepository;
    private final UserRegistrationService userRegistrationService;

    @Autowired
    public AdminUserController(UserRepository userRepository,
                               UserRegistrationService userRegistrationService) {
        this.userRepository = userRepository;
        this.userRegistrationService = userRegistrationService;
    }

    @GetMapping
    public String showUsersList(Model model, @RequestParam(defaultValue = "0") int page) {
        model.addAttribute("userList", userRepository
                .findAll(new PageRequest(page, 10)));
        model.addAttribute("currentPage", page);
        return "admin/usersList";
    }

    @GetMapping("/add")
    public String userRegistration(Model model) {
        model.addAttribute("user", new User());
        return "admin/usersAdd";
    }

    @PostMapping("/add")
    public String userRegistration(@ModelAttribute("user") @Valid User user,
                                   BindingResult result) {

        if (userRegistrationService.findByEmail(user.getEmail()) != null) {
            return "redirect:/admin/users/add?exist";
        }
        if (result.hasErrors()) {
            return "redirect:/admin/users/add?addFailed";
        }

        userRegistrationService.saveUser(user);
        return "redirect:/admin/users?addSuccess";
    }

    @GetMapping("/details")
    public String showUserDetails(@RequestParam Long id, Model model) {
        model.addAttribute("user", userRepository.getOne(id));
        return "admin/usersDetails";
    }

    @GetMapping("/edit")
    public String showUserEditForm(@RequestParam Long id, Model model) {
        model.addAttribute("user", userRepository.getOne(id));
        return "admin/usersEdit";
    }

    @PostMapping("/edit")
    public String proceedUserEditForm(@ModelAttribute("user") @Valid User user,
                                      BindingResult result) {

        if (result.hasErrors()) {
            return "redirect:/admin/users/edit?id=" + user.getId() + "&failed";
        }
        userRegistrationService.saveAdmin(user);
        return "redirect:/admin/users/edit?id=" + user.getId() + "&success";
    }

    @RequestMapping("/delete")
    public String deleteUser(@RequestParam Long id,
                             @ModelAttribute("currentUser") User user,
                             BindingResult result) {
        if (result.hasErrors() || userRepository.getOne(id).equals(user)) {
            return "redirect:/admin/users?delete?failed";
        }
        userRepository.deleteById(id);
        return "redirect:/admin/users?delete?success";
    }
}

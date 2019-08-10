package pl.coderslab.charity.controller.admin;

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

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/admin/users")
public class AdminUserController {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private final UserRegistrationService userRegistrationService;

    @Autowired
    public AdminUserController(UserRepository userRepository, RoleRepository roleRepository,
                               UserRegistrationService userRegistrationService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRegistrationService = userRegistrationService;
    }

    @GetMapping
    public String showUsersList() {
        return "admin/usersList";
    }

    @ModelAttribute("userList")
    public List<User> userList() {
        return userRepository.findAll();
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

    @GetMapping("/edit")
    public String showAdministratorEditForm(@RequestParam Long id, Model model) {
        model.addAttribute("user", userRepository.getOne(id));
        return "admin/usersEdit";
    }

    @PostMapping("/edit")
    public String proceedAdministratorEditForm(@ModelAttribute("user") @Valid User user,
                                               BindingResult result) {

        if (result.hasErrors()) {
            return "redirect:/admin/users/edit?id=" + user.getId() + "&failed";
        }
        userRegistrationService.saveAdmin(user);
        return "redirect:/admin/users/edit?id=" + user.getId() + "&success";
    }

    @RequestMapping("/delete")
    public String deleteAdministrator(@RequestParam Long id,
                                      @ModelAttribute("currentUser") User user) {
        if (id == user.getId()) {
            return "redirect:/admin/users?delete?id=" + id + "&failed";
        }
        userRepository.deleteById(id);
        return "redirect:/admin/users?delete?id=" + id + "&success";
    }
}

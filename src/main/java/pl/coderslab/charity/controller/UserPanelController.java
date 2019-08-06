package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.repository.DonationRepository;
import pl.coderslab.charity.repository.UserRepository;
import pl.coderslab.charity.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@Secured("ROLE_USER")
@RequestMapping("/user")
public class UserPanelController {
    private UserRepository userRepository;
    private UserService userService;
    private DonationRepository donationRepository;

    @Autowired
    public UserPanelController(UserRepository userRepository, UserService userService, DonationRepository donationRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.donationRepository = donationRepository;
    }

    @GetMapping
    public String showLoggedUserPage() {
        return "user/userPage";
    }

    @GetMapping("/profile")
    public String showUserProfileSite() {
        return "user/userProfile";
    }

    @GetMapping("/edit")
    public String showUserEditForm(@RequestParam Long id, Model model) {
        model.addAttribute("toEdit", userRepository.getOne(id));
        return "user/userEdit";
    }

    @PostMapping("/edit")
    public String proceedUserEditForm(@ModelAttribute("toEdit") @Valid User user,
                                      BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/user/edit?id=" + user.getId() + "&failed";
        }
        userService.updateUser(user);
        return "redirect:/user/edit?id=" + user.getId() + "&success";
    }

    @GetMapping("/donations")
    public String showDonationsList(Model model) {
        return "user/userDonations";
    }

    @PostMapping("/donations")
    public String proceedDonationList() {
        return "redirect:/user/donations";
    }

    @ModelAttribute("donationsList")
    public List<Donation> getDonationsList() {
        return donationRepository.findAll();
    }
}

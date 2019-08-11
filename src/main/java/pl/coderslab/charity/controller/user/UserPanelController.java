package pl.coderslab.charity.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.dto.UserChangePasswordDto;
import pl.coderslab.charity.dto.UserEditDto;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.DonationStatus;
import pl.coderslab.charity.repository.DonationRepository;
import pl.coderslab.charity.repository.DonationStatusRepository;
import pl.coderslab.charity.repository.UserRepository;
import pl.coderslab.charity.service.UserService;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

@Controller
@Secured("ROLE_USER")
@RequestMapping("/user")
public class UserPanelController {
    private UserRepository userRepository;
    private UserService userService;
    private DonationRepository donationRepository;
    private DonationStatusRepository donationStatusRepository;

    @Autowired
    public UserPanelController(UserRepository userRepository, UserService userService, DonationRepository donationRepository, DonationStatusRepository donationStatusRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.donationRepository = donationRepository;
        this.donationStatusRepository = donationStatusRepository;
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
        model.addAttribute("toEdit", new UserEditDto(userRepository.getOne(id)));
        return "user/userEdit";
    }

    @PostMapping("/edit")
    public String proceedUserEditForm(@ModelAttribute("toEdit") @Valid UserEditDto user,
                                      BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/user/edit?id=" + user.getId() + "&failed";
        }
        if (!userService.passwordMatches(user)) {
            return "redirect:/user/edit?id=" + user.getId() + "&invalid";
        }
        userService.updateUser(user);
        return "redirect:/user/edit?id=" + user.getId() + "&success";
    }

    @GetMapping("/changePassword")
    public String showUserPasswordForm(@RequestParam("id") String id, Model model) {
        UserChangePasswordDto passwordDto = new UserChangePasswordDto();
        passwordDto.setId(id);
        model.addAttribute("password", passwordDto);
        return "user/changePassword";
    }

    @PostMapping("/changePassword")
    public String proceedUserPasswordForm(@ModelAttribute("password") @Valid UserChangePasswordDto passwordDto,
                                          BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/user/changePassword?id=" + passwordDto.getId() + "&failed";
        }
        userService.changePassword(passwordDto);
        return "redirect:/user/changePassword?id=" + passwordDto.getId() + "&success";
    }

    @GetMapping("/donations")
    public String showDonationsList() {
        return "user/userDonations";
    }

    @PostMapping("/donations")
    public String proceedDonationList(@ModelAttribute("donation") Donation donation,
                                      BindingResult result) {

        if (result.hasErrors()) {
            return "redirect:/user/donations?failed";
        }

        donationRepository.save(donation);
        return "redirect:/user/donations?success";
    }

    @GetMapping("/donations/details")
    public String showUserDonationUpdateSite(@RequestParam Long id, Model model) {
        model.addAttribute("donation", donationRepository.getOne(id));
        return "user/userDonationDetails";
    }

    @PostMapping("/donations/details")
    public String proceedUserDonationSingleUpdate(@ModelAttribute("donation") Donation donation,
                                                  BindingResult result) {

        if (result.hasErrors()) {
            return "redirect:/user/donations/details?id=" + donation.getId() + "&failed";

        }
        userService.updateUserDonation(donation);
        return "redirect:/user/donations/details?id=" + donation.getId() + "&success";
    }

    @ModelAttribute("donationsList")
    public List<Donation> getDonationsList() {
        List<Donation> sorted = donationRepository.findAll();
        sorted.sort(
                Comparator.comparing(Donation::getDate).reversed()
                        .thenComparing((Donation donation) -> donation.getStatus().getId())
                        .reversed()
                        .thenComparing(Donation::getDonationCreated)
                        .reversed());
        return sorted;
    }

    @ModelAttribute("donationStatusList")
    public List<DonationStatus> getDonationsStatus() {
        return donationStatusRepository.findAll();
    }
}

package pl.bartlomiejpietrzyk.charity.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.bartlomiejpietrzyk.charity.dto.UserChangePasswordDto;
import pl.bartlomiejpietrzyk.charity.dto.UserEditDto;
import pl.bartlomiejpietrzyk.charity.entity.Donation;
import pl.bartlomiejpietrzyk.charity.entity.DonationStatus;
import pl.bartlomiejpietrzyk.charity.entity.User;
import pl.bartlomiejpietrzyk.charity.repository.DonationRepository;
import pl.bartlomiejpietrzyk.charity.repository.DonationStatusRepository;
import pl.bartlomiejpietrzyk.charity.repository.UserRepository;
import pl.bartlomiejpietrzyk.charity.service.UserService;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Secured("ROLE_USER")
@RequestMapping("/user")
public class UserPanelController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final DonationRepository donationRepository;
    private final DonationStatusRepository donationStatusRepository;

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
    public String showUserProfileSite(@ModelAttribute("currentUser") User user,
                                      Model model) {
        model.addAttribute("userDonationCount", donationRepository.countAllByUser(user));
        model.addAttribute("userDonationCountGiven",
                donationRepository.countUserDonationsByStatusNameEqualsAndUserId("Odebrane", user.getId()));
        model.addAttribute("userDonationCountToGive",
                donationRepository.countUserDonationsByStatusNameEqualsAndUserId("Nieodebrane", user.getId()));
        model.addAttribute("userDonationCountBags",
                donationRepository.countUserQuantityDonationsByStatusNameEqualsAndUserId("Odebrane", user.getId()));
        model.addAttribute("userToDonationCountBags",
                donationRepository.countUserQuantityDonationsByStatusNameEqualsAndUserId("Nieodebrane", user.getId()));
        model.addAttribute("userDonationInstitutions", donationRepository
                .findAllByUser(user)
                .stream()
                .map(Donation::getInstitution)
                .collect(Collectors.toSet())
                .size());
        return "user/userProfile";
    }

    @GetMapping("/settings")
    public String showUserSettingsSite() {
        return "user/userSettings";
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
    public String showDonationsList(Model model,
                                    @ModelAttribute("currentUser") User user,
                                    @RequestParam(defaultValue = "0") int page) {
        Page<Donation> sorted = donationRepository.findAllByUser(user, new PageRequest(page, 5));
        sorted
                .stream()
                .sorted(
                        Comparator.comparing(Donation::getDate).reversed()
                                .thenComparing((Donation donation) -> donation.getStatus().getId())
                                .reversed()
                                .thenComparing(Donation::getDonationCreated)
                                .reversed())
                .collect(Collectors.toList());
        model.addAttribute("donationsList", sorted);
        model.addAttribute("currentPage", page);
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

    @GetMapping("/{uuid}/enable")
    public String enableUser(@PathVariable String uuid) {
        userService.enableUser(uuid);
        return "redirect:/login?uuid=" + uuid + "&active";
    }

    @ModelAttribute("donationStatusList")
    public List<DonationStatus> getDonationsStatus() {
        return donationStatusRepository.findAll();
    }
}

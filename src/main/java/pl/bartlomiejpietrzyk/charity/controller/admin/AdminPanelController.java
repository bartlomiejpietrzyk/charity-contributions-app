package pl.bartlomiejpietrzyk.charity.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bartlomiejpietrzyk.charity.entity.Donation;
import pl.bartlomiejpietrzyk.charity.entity.User;
import pl.bartlomiejpietrzyk.charity.repository.DonationRepository;
import pl.bartlomiejpietrzyk.charity.repository.UserRepository;

import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/admin")
public class AdminPanelController {
    private final DonationRepository donationRepository;
    private final UserRepository userRepository;

    @Autowired
    public AdminPanelController(DonationRepository donationRepository, UserRepository userRepository) {
        this.donationRepository = donationRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String showAdminPanelDashboard(Model model) {

        model.addAttribute("lastDonations", donationRepository
                .findAll()
                .stream().sorted(Comparator.comparing(Donation::getId).reversed())
                .limit(5)
                .collect(Collectors.toList()));
        model.addAttribute("lastUsers", userRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(User::getId).reversed())
                .limit(5)
                .collect(Collectors.toList()));
        model.addAttribute("givenDonations");
        model.addAttribute("toGiveDonations");

        return "admin/index";
    }
}

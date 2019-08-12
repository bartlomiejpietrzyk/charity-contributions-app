package pl.coderslab.charity.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.repository.DonationRepository;

@Controller
@RequestMapping("/admin/donations")
public class AdminDonationController {
    private DonationRepository donationRepository;

    @Autowired
    public AdminDonationController(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    @GetMapping
    public String showDonationList(Model model) {
        model.addAttribute("donationList", donationRepository.findAll());
        return "admin/donationsList";
    }

    @GetMapping("/archive")
    public String showDonationArchiveList() {
        return "admin/donationsListArchive";
    }
}

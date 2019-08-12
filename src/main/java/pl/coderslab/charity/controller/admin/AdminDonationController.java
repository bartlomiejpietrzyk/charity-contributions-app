package pl.coderslab.charity.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.repository.DonationRepository;

import javax.validation.Valid;
import java.util.stream.Collectors;

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

    @GetMapping("/details")
    public String showDetailsDonationSite(@RequestParam Long id, Model model) {
        model.addAttribute("donation", donationRepository.getOne(id));
        return "admin/donationDetails";
    }

    @GetMapping("/edit")
    public String showEditDonationForm(@RequestParam Long id, Model model) {
        model.addAttribute("donation", donationRepository.getOne(id));
        return "admin/donationEdit";
    }

    @PostMapping("/edit")
    public String proceedEditInstitutionForm(@ModelAttribute("donation") @Valid Donation donation,
                                             BindingResult result) {

        if (result.hasErrors()) {
            return "redirect:/admin/donations/edit?id=" + donation.getId() + "&failed";
        }
        donationRepository.save(donation);
        return "redirect:/admin/donations/edit?id=" + donation.getId() + "&success";
    }

    @RequestMapping("/delete")
    public String deleteDonation(@RequestParam Long id) {
        donationRepository.deleteById(id);
        return "redirect:/admin/donationss?deletesuccess";
    }


    @GetMapping("/archive")
    public String showDonationArchiveList(Model model) {
        model.addAttribute("archived", donationRepository
                .findAll()
                .stream()
                .filter(donation -> donation.getStatus().getId() == 2)
                .collect(Collectors.toList()));
        return "admin/donationsListArchive";
    }
}

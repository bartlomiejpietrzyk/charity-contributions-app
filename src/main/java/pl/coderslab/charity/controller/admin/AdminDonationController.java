package pl.coderslab.charity.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.repository.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/donations")
public class AdminDonationController {
    private final DonationRepository donationRepository;
    private final InstitutionRepository institutionRepository;
    private final CategoryRepository categoryRepository;
    private final DonationStatusRepository donationStatusRepository;
    private final UserRepository userRepository;

    @Autowired
    public AdminDonationController(DonationRepository donationRepository, InstitutionRepository institutionRepository, CategoryRepository categoryRepository, DonationStatusRepository donationStatusRepository, UserRepository userRepository) {
        this.donationRepository = donationRepository;
        this.institutionRepository = institutionRepository;
        this.categoryRepository = categoryRepository;
        this.donationStatusRepository = donationStatusRepository;
        this.userRepository = userRepository;
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
        model.addAttribute("institutions", institutionRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("statusList", donationStatusRepository.findAll());
        model.addAttribute("userList", userRepository.findAll());
        return "admin/donationsEdit";
    }

    @PostMapping("/edit")
    public String proceedEditInstitutionForm(@ModelAttribute("donation") @Valid Donation donation,
                                             Model model,
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
        return "redirect:/admin/donations?deletesuccess";
    }


    @GetMapping("/archive")
    public String showDonationArchiveList(Model model) {
        model.addAttribute("archivedList", donationRepository
                .findAll()
                .stream()
                .filter(donation -> donation.getStatus().getId() == 2)
                .collect(Collectors.toList()));
        return "admin/donationsListArchive";
    }
}

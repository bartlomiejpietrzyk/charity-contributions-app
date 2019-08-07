package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.repository.CategoryRepository;
import pl.coderslab.charity.repository.DonationRepository;
import pl.coderslab.charity.repository.DonationStatusRepository;
import pl.coderslab.charity.repository.InstitutionRepository;


@Controller
@RequestMapping
public class DonationController {

    private DonationStatusRepository donationStatusRepository;
    private CategoryRepository categoryRepository;
    private DonationRepository donationRepository;
    private InstitutionRepository institutionRepository;

    @Autowired
    public DonationController(DonationStatusRepository donationStatusRepository, CategoryRepository categoryRepository, DonationRepository donationRepository, InstitutionRepository institutionRepository) {
        this.donationStatusRepository = donationStatusRepository;
        this.categoryRepository = categoryRepository;
        this.donationRepository = donationRepository;
        this.institutionRepository = institutionRepository;
    }

    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("donation", new Donation());
        model.addAttribute("categoriesList", categoryRepository.findAll());
        model.addAttribute("institutions", institutionRepository.findAll());
        return "user/form";
    }

    @PostMapping("/form")
    public String proceedForm(@ModelAttribute("donation") Donation donation,
                              @ModelAttribute("currentUser") User user) {
        donation.setUser(user);
        donation.setStatus(donationStatusRepository.getOne(1L));
        donationRepository.save(donation);
        return "user/formConfirm";
    }

    @GetMapping("/rest/institution/getTitle/{id}")
    @ResponseBody
    public String getInstitutionName(@PathVariable(name = "id") Long id) {
        return institutionRepository.getOne(id).getName();
    }

    @GetMapping("/rest/category/getTitle/{id}")
    @ResponseBody
    public String getCategoryName(@PathVariable(name = "id") Long id) {
        return categoryRepository.getOne(id).getName();
    }

}

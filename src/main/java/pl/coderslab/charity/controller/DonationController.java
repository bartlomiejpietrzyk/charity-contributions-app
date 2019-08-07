package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.repository.CategoryRepository;
import pl.coderslab.charity.repository.InstitutionRepository;
import pl.coderslab.charity.service.DonationService;


@Controller
@RequestMapping
public class DonationController {

    private CategoryRepository categoryRepository;
    private InstitutionRepository institutionRepository;
    private DonationService donationService;

    @Autowired
    public DonationController(CategoryRepository categoryRepository, InstitutionRepository institutionRepository, DonationService donationService) {
        this.categoryRepository = categoryRepository;
        this.institutionRepository = institutionRepository;
        this.donationService = donationService;
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
        donationService.donationCreate(donation, user);
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

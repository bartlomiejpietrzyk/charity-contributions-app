package pl.bartlomiejpietrzyk.charity.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.bartlomiejpietrzyk.charity.entity.Donation;
import pl.bartlomiejpietrzyk.charity.entity.User;
import pl.bartlomiejpietrzyk.charity.repository.CategoryRepository;
import pl.bartlomiejpietrzyk.charity.repository.InstitutionRepository;
import pl.bartlomiejpietrzyk.charity.service.DonationService;

import javax.validation.Valid;


@Controller
@RequestMapping
public class DonationController {
    private final CategoryRepository categoryRepository;
    private final InstitutionRepository institutionRepository;
    private final DonationService donationService;

    @Autowired
    public DonationController(CategoryRepository categoryRepository,
                              InstitutionRepository institutionRepository,
                              DonationService donationService) {
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
    public String proceedForm(@Valid @ModelAttribute("donation") Donation donation,
                              @ModelAttribute("currentUser") User user,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/form?error";
        }
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

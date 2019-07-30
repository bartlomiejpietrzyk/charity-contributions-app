package pl.coderslab.charity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.repository.DonationRepository;
import pl.coderslab.charity.repository.InstitutionRepository;

import java.util.List;
import java.util.stream.Collectors;


@Controller
public class HomeController {

    private InstitutionRepository institutionRepository;
    private DonationRepository donationRepository;

    @Autowired
    public HomeController(InstitutionRepository institutionRepository, DonationRepository donationRepository) {
        this.institutionRepository = institutionRepository;
        this.donationRepository = donationRepository;
    }

    @RequestMapping("/")
    public String homeAction(Model model) {
        model.addAttribute("institution", institutionRepository.findAll());
        return "index";
    }

    @RequestMapping("/login")
    public String showLoginForm(Model model) {
        return "login";
    }

    @RequestMapping("/registration")
    public String showRegistrationForm(Model model) {
        return "registration";
    }

    @RequestMapping("/form")
    public String showForm(Model model) {
        return "form";
    }

    @ModelAttribute(name = "institutionList")
    public List<Institution> institutionList() {
        return institutionRepository.findAll();
    }

    @ModelAttribute(name = "bags")
    public Long bags() {
        return donationRepository
                .findAll()
                .stream()
                .map(Donation::getQuantity)
                .collect(Collectors.counting());
    }


}

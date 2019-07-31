package pl.coderslab.charity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.repository.DonationRepository;
import pl.coderslab.charity.repository.InstitutionRepository;

import java.util.List;

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
        model.addAttribute("institutions", institutionRepository.findAll());
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

    @ModelAttribute(name = "bags")
    public Long showQuantityOfGivenBags() {
        List<Donation> collect = donationRepository.findAll();
        Long sum = 0l;
        for (int i = 0; i < collect.size(); i++) {
            sum += collect.get(i).getQuantity();
        }
        return sum;
    }

    @ModelAttribute(name = "charityOrg")
    public Long charityOrganisationsQuantity() {
        return institutionRepository
                .count();
    }


}

package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.repository.DonationRepository;
import pl.coderslab.charity.repository.InstitutionRepository;
import pl.coderslab.charity.repository.UserRepository;

import java.security.Principal;
import java.util.List;

@ControllerAdvice
public class GlobalDataControllerAdvice {

    private final UserRepository userRepository;
    private InstitutionRepository institutionRepository;
    private DonationRepository donationRepository;

    @Autowired
    public GlobalDataControllerAdvice(UserRepository userRepository, InstitutionRepository institutionRepository, DonationRepository donationRepository) {
        this.userRepository = userRepository;
        this.institutionRepository = institutionRepository;
        this.donationRepository = donationRepository;
    }

    @ModelAttribute("currentUserName")
    public String currentUserName(Principal principal) {
        if (principal == null) {
            return "";
        }
        User loggedUser = userRepository.findByEmail(principal.getName());

        return loggedUser.getFirstName() + " " + loggedUser.getLastName();
    }

    @ModelAttribute("currentUser")
    public User currentUser(Principal principal) {
        if (principal == null) {
            User user = new User();
            user.setFirstName("Dobroczyńco");
            return user;
        }
        return userRepository.findByEmail(principal.getName());
    }


    @ModelAttribute(name = "charityOrgQuantity")
    public Long charityOrganisationsQuantity() {
        return institutionRepository
                .count();
    }

    @ModelAttribute(name = "givenBagsQuantity")
    public Long showQuantityOfGivenBags() {
        List<Donation> collect = donationRepository.findAll();
        Long sum = 0L;
        for (Donation donation : collect) {
            sum += donation.getQuantity();
        }
        return sum;
    }

    @ModelAttribute("trustedInstitutions")
    public List<Institution> institutionsList() {
        return institutionRepository.findAll();
    }

}

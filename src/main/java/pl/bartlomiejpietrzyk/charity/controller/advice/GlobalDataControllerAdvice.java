package pl.bartlomiejpietrzyk.charity.controller.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.bartlomiejpietrzyk.charity.entity.Donation;
import pl.bartlomiejpietrzyk.charity.entity.Institution;
import pl.bartlomiejpietrzyk.charity.repository.DonationRepository;
import pl.bartlomiejpietrzyk.charity.repository.InstitutionRepository;
import pl.bartlomiejpietrzyk.charity.repository.UserRepository;

import java.util.List;

@ControllerAdvice
public class GlobalDataControllerAdvice {

    private final InstitutionRepository institutionRepository;
    private final DonationRepository donationRepository;
    private final UserRepository userRepository;

    @Autowired
    public GlobalDataControllerAdvice(InstitutionRepository institutionRepository, DonationRepository donationRepository, UserRepository userRepository) {
        this.institutionRepository = institutionRepository;
        this.donationRepository = donationRepository;
        this.userRepository = userRepository;
    }

    @ModelAttribute(name = "charityOrgQuantity")
    public Long charityOrganisationsQuantity() {
        return institutionRepository
                .count();
    }

    @ModelAttribute(name = "registeredUsersCount")
    public Long registeredUsers() {
        return userRepository.count();
    }

    @ModelAttribute(name = "givenBagsQuantity")
    public Long showQuantityOfGivenBags() {
        return donationRepository
                .findAll()
                .stream()
                .map(Donation::getQuantity)
                .mapToLong(Long::longValue).sum();
    }

    @ModelAttribute("trustedInstitutions")
    public List<Institution> institutionsList() {
        return institutionRepository.findAll();
    }

}

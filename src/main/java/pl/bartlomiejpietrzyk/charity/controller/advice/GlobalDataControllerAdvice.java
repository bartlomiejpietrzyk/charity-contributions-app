package pl.bartlomiejpietrzyk.charity.controller.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.bartlomiejpietrzyk.charity.entity.Donation;
import pl.bartlomiejpietrzyk.charity.entity.Institution;
import pl.bartlomiejpietrzyk.charity.repository.*;

import java.util.List;

@ControllerAdvice
public class GlobalDataControllerAdvice {

    private final InstitutionRepository institutionRepository;
    private final DonationRepository donationRepository;
    private final DonationStatusRepository statusRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public GlobalDataControllerAdvice(InstitutionRepository institutionRepository, DonationRepository donationRepository, DonationStatusRepository statusRepository, UserRepository userRepository, MessageRepository messageRepository) {
        this.institutionRepository = institutionRepository;
        this.donationRepository = donationRepository;
        this.statusRepository = statusRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
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
                .filter(donation -> donation.getStatus()
                        .equals(statusRepository.findByName("Odebrane")))
                .map(Donation::getQuantity)
                .mapToLong(Long::longValue).sum();
    }

    @ModelAttribute(name = "donationToGive")
    public Long showQuantityOfDonationsToGive() {
        return donationRepository
                .countUserDonationsByStatusNameEquals("Nieodebrane");
    }

    @ModelAttribute(name = "donationGiven")
    public Long showQuantityOfGivenDonations() {
        return donationRepository
                .countUserDonationsByStatusNameEquals("Odebrane");
    }

    @ModelAttribute("trustedInstitutions")
    public List<Institution> institutionsList() {
        return institutionRepository.findAll();
    }

    @ModelAttribute("newMessages")
    public Long showNewMessagesCount() {
        return messageRepository.countAllByMessageOpenEquals(false);
    }

}

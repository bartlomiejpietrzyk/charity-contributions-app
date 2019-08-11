package pl.coderslab.charity.controller.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.repository.DonationRepository;
import pl.coderslab.charity.repository.InstitutionRepository;

import java.util.List;

@ControllerAdvice
public class GlobalDataControllerAdvice {

    private InstitutionRepository institutionRepository;
    private DonationRepository donationRepository;

    @Autowired
    public GlobalDataControllerAdvice(InstitutionRepository institutionRepository, DonationRepository donationRepository) {
        this.institutionRepository = institutionRepository;
        this.donationRepository = donationRepository;
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

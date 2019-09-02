package pl.bartlomiejpietrzyk.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.bartlomiejpietrzyk.charity.email.EmailServiceImpl;
import pl.bartlomiejpietrzyk.charity.entity.Donation;
import pl.bartlomiejpietrzyk.charity.entity.User;
import pl.bartlomiejpietrzyk.charity.repository.DonationRepository;
import pl.bartlomiejpietrzyk.charity.repository.DonationStatusRepository;

import java.time.LocalDate;

@Service
@Transactional
public class DonationService {
    private final DonationStatusRepository donationStatusRepository;
    private final DonationRepository donationRepository;
    private final EmailServiceImpl emailService;
    @Autowired
    public DonationService(DonationStatusRepository donationStatusRepository, DonationRepository donationRepository, EmailServiceImpl emailService) {
        this.donationStatusRepository = donationStatusRepository;
        this.donationRepository = donationRepository;
        this.emailService = emailService;
    }

    public void donationCreate(Donation donation, User user) {
        donation.setUser(user);
        donation.setDonationCreated(LocalDate.now());
        donation.setStatus(donationStatusRepository.findByName("Nieodebrane"));
        emailService.sendSimpleMessage(user.getEmail(),
                "Zgłoszenie przekazania darów przebiegło pomyślnie!",
                "Witaj " + user.getFirstName() + "!\nPrzekazanie darów przebiegło pomyślnie!\nPrzekazałeś/aś dary dla:\n" + donation.getInstitution() +
                        " w kategoriach: " + donation.getCategories() + " w ilości " + donation.getQuantity() + " worków!\nDziękujemy!");
        donationRepository.save(donation);

    }
}

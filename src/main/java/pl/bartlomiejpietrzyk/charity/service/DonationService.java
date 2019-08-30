package pl.bartlomiejpietrzyk.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.bartlomiejpietrzyk.charity.entity.Donation;
import pl.bartlomiejpietrzyk.charity.entity.User;
import pl.bartlomiejpietrzyk.charity.repository.DonationRepository;
import pl.bartlomiejpietrzyk.charity.repository.DonationStatusRepository;

@Service
@Transactional
public class DonationService {
    private final DonationStatusRepository donationStatusRepository;
    private final DonationRepository donationRepository;

    @Autowired
    public DonationService(DonationStatusRepository donationStatusRepository, DonationRepository donationRepository) {
        this.donationStatusRepository = donationStatusRepository;
        this.donationRepository = donationRepository;
    }

    public void donationCreate(Donation donation, User user) {
        donation.setUser(user);
        donation.setStatus(donationStatusRepository.getOne(1L));
        donationRepository.save(donation);

    }
}

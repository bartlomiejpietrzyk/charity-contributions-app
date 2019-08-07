package pl.coderslab.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.repository.DonationRepository;
import pl.coderslab.charity.repository.UserRepository;

import java.time.LocalDate;

@Service
@Transactional
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private DonationRepository donationRepository;

    @Autowired
    public UserService(UserRepository userRepository, DonationRepository donationRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.donationRepository = donationRepository;
    }

    public void updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(userRepository.getOne(user.getId()).getEnabled());
        user.setRoles(user.getRoles());
        userRepository.save(user);
    }

    public void updateUserDonation(Donation donation) {
        Donation existing = donationRepository.getOne(donation.getId());
        if (donation.getStatus().getId() == 2) {
            existing.setDate(LocalDate.now());
        }
        existing.setStatus(donation.getStatus());
        donationRepository.save(existing);
    }
}

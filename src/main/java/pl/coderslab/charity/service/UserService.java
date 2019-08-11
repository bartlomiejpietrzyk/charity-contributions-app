package pl.coderslab.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.dto.UserEditDto;
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
        user.setEnabled(userRepository.getOne(user.getId()).getEnabled());
        user.setRoles(user.getRoles());
        userRepository.save(user);
    }

    public void updateUserDonation(Donation donation) {
        Donation existing = donationRepository.getOne(donation.getId());
        if (donation.getStatus().getId() == 2) {
            existing.setDonationPicked(LocalDate.now());
        }
        existing.setStatus(donation.getStatus());
        donationRepository.save(existing);
    }

    public void enableUser(String uuid) {
        User user = userRepository.findOneByUuid(uuid);
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void updateUser(UserEditDto user) {
        User one = userRepository.getOne(Long.valueOf(user.getId()));
        one.setFirstName(user.getFirstName());
        one.setLastName(user.getLastName());
        one.setEmail(user.getEmail());
        if (passwordEncoder.matches(user.getPassword(), one.getPassword())) {
            userRepository.save(one);
        }
    }

    public boolean passwordMatches(UserEditDto user) {
        User existing = userRepository.getOne(Long.valueOf(user.getId()));
        return passwordEncoder.matches(user.getPassword(), existing.getPassword());
    }
}
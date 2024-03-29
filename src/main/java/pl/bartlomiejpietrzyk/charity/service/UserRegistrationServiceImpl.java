package pl.bartlomiejpietrzyk.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.bartlomiejpietrzyk.charity.dto.UserRegistrationDto;
import pl.bartlomiejpietrzyk.charity.email.EmailServiceImpl;
import pl.bartlomiejpietrzyk.charity.entity.Role;
import pl.bartlomiejpietrzyk.charity.entity.User;
import pl.bartlomiejpietrzyk.charity.repository.RoleRepository;
import pl.bartlomiejpietrzyk.charity.repository.UserRepository;

import java.util.Arrays;
import java.util.HashSet;

@Service
@Transactional
public class UserRegistrationServiceImpl implements UserRegistrationService {
    private final EmailServiceImpl emailService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserRegistrationServiceImpl(EmailServiceImpl emailService, UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false);
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);
        emailService.sendSimpleMessage(user.getEmail(), "Klinkij w link aktywacyjny by aktywowac konto:",
                "To activate proceed: http://localhost:8080/user/" + user.getUuid() + "/enable");
    }

    public void saveUser(UserRegistrationDto registrationDto) {
        User user = new User();
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getFirstName());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setEnabled(false);
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);
        emailService.sendSimpleMessage(user.getEmail(), "Klinkij w link aktywacyjny by aktywowac konto:",
                "Hello " + user.getFirstName() + " " + user.getLastName()
                        + "! Przejdź by aktywować konto: http://localhost:8080/user/" + user.getUuid() + "/enable");

    }

    @Override
    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

}
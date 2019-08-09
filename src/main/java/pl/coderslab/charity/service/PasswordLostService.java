package pl.coderslab.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.entity.BaseEntity;
import pl.coderslab.charity.entity.PasswordToken;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.repository.PasswordTokenRepository;
import pl.coderslab.charity.repository.UserRepository;

@Service
@Transactional
public class PasswordLostService extends BaseEntity {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private PasswordTokenRepository passwordTokenRepository;

    @Autowired
    public PasswordLostService(UserRepository userRepository, PasswordEncoder passwordEncoder, PasswordTokenRepository passwordTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordTokenRepository = passwordTokenRepository;
    }

    public void lostPassword(String email) {
        User byEmail = userRepository.findByEmail(email);
        if (byEmail != null) {
            System.out.println(getUuid());
        }
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordToken myToken = new PasswordToken();
        myToken.setUser(user);
        myToken.setToken(token);
        passwordTokenRepository.save(myToken);
    }

}

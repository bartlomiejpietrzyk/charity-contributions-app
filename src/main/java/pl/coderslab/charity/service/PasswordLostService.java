package pl.coderslab.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.entity.PasswordToken;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.repository.PasswordTokenRepository;
import pl.coderslab.charity.repository.UserRepository;

import java.time.LocalDate;
import java.util.Arrays;

@Service
@Transactional
public class PasswordLostService {
    private final UserRepository userRepository;
    private final PasswordTokenRepository passwordTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordLostService(UserRepository userRepository, PasswordTokenRepository passwordTokenRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordTokenRepository = passwordTokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordToken myToken = new PasswordToken();
        myToken.setUser(user);
        myToken.setToken(token);
        passwordTokenRepository.save(myToken);
    }

    public SimpleMailMessage constructResetTokenEmail(String contextPath, String token, User user) {
        String url = contextPath + "/resetPassword?id=" +
                user.getId() + "&token=" + token;
        String message = "Witaj " + user.getFirstName() + "!\nBy zresetować hasło kliknij w poniższy link!\n";
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    public SimpleMailMessage constructEmail(String subject, String body,
                                            User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        return email;
    }

    public String validatePasswordResetToken(Long id, String token) {
        PasswordToken passToken =
                passwordTokenRepository.findByToken(token);
        if ((passToken == null) || (passToken.getUser()
                .getId() != id)) {
            return "invalidToken";
        }

        User user = passToken.getUser();
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, null, Arrays.asList(
                new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return null;
    }

    public void changeUserLostPassword(String id, String password, String token) {
        User existing = userRepository.getOne(Long.valueOf(id));
        existing.setPassword(passwordEncoder.encode(password));
        userRepository.save(existing);
        useToken(token);
    }

    void useToken(String token) {
        PasswordToken byToken = passwordTokenRepository.findByToken(token);
        byToken.setUsedDate(LocalDate.now());
        passwordTokenRepository.save(byToken);
    }
}

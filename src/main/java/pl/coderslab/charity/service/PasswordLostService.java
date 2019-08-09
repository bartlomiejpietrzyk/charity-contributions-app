package pl.coderslab.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.entity.PasswordToken;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.repository.PasswordTokenRepository;

@Service
@Transactional
public class PasswordLostService {
    private PasswordTokenRepository passwordTokenRepository;

    @Autowired
    public PasswordLostService(PasswordTokenRepository passwordTokenRepository) {
        this.passwordTokenRepository = passwordTokenRepository;
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
        String message = "Witaj " + user.getFirstName() + "!\nBy zresetować hasło kliknij w poniższy link!";
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body,
                                             User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom("email@email.com");
        return email;
    }
}

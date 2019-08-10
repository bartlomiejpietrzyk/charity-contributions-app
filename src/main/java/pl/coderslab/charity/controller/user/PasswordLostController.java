package pl.coderslab.charity.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.charity.dto.UserChangePasswordDto;
import pl.coderslab.charity.dto.UserLostPasswordDto;
import pl.coderslab.charity.email.EmailServiceImpl;
import pl.coderslab.charity.entity.PasswordToken;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.repository.PasswordTokenRepository;
import pl.coderslab.charity.repository.UserRepository;
import pl.coderslab.charity.service.PasswordLostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.UUID;

@Controller
public class PasswordLostController {
    private UserRepository userRepository;
    private PasswordLostService passwordLostService;
    private PasswordTokenRepository passwordTokenRepository;
    private EmailServiceImpl emailService;

    @Autowired
    public PasswordLostController(UserRepository userRepository, PasswordLostService passwordLostService, PasswordTokenRepository passwordTokenRepository, EmailServiceImpl emailService) {
        this.userRepository = userRepository;
        this.passwordLostService = passwordLostService;
        this.passwordTokenRepository = passwordTokenRepository;
        this.emailService = emailService;
    }

    @GetMapping("/lostPassword")
    public String showLostPasswordForm(Model model) {
        model.addAttribute("user", new User());
        return "user/lostPassword";
    }

    @PostMapping("/lostPassword")
    public String proceedLostPasswordForm(@ModelAttribute("user") @Valid UserLostPasswordDto user,
                                          BindingResult result) {
        User byEmail = userRepository.findByEmail(user.getEmail());
        if (result.hasErrors()) {
            return "redirect:/lostPassword?error";
        }
        if (byEmail == null) {
            return "redirect:/lostPassword?notExist";
        }
        if (!byEmail.getEnabled()) {
            return "redirect:/lostPassword?disabled";
        }
        String token = UUID.randomUUID().toString();
        passwordLostService.createPasswordResetTokenForUser(byEmail, token);
        SimpleMailMessage simpleMailMessage =
                passwordLostService.constructResetTokenEmail("http://localhost:8080", token, byEmail);
        emailService.sendSimpleMessage(byEmail.getEmail(), simpleMailMessage.getSubject(), simpleMailMessage.getText());
        return "redirect:/lostPassword?sent";
    }

    @GetMapping("/resetPassword")
    public String showPasswordResetSite(@RequestParam Long id,
                                        @RequestParam String token, Model model) {
        String result = passwordLostService.validatePasswordResetToken(id, token);
        PasswordToken byToken = passwordTokenRepository.findByToken(token);
        if (byToken == null) {
            return "redirect:/lostPassword?notoken";
        } else if (byToken.getId() != null && byToken.getUsedDate() != null) {
            return "redirect:/lostPassword?usedtoken";
        }
        if (result != null) {
            return "redirect:/lostPassword?error";
        }

        UserChangePasswordDto userChangePasswordDto = new UserChangePasswordDto(userRepository.getOne(id));
        userChangePasswordDto.setToken(token);
        model.addAttribute("reset", userChangePasswordDto);
        return "user/resetPassword";
    }

    @PostMapping("/resetPassword")
    public String proceedPasswordResetSite(@ModelAttribute("reset") @Valid UserChangePasswordDto passwordDto,
                                           HttpSession session,
                                           BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/resetPassword?id=" + passwordDto.getId() + "&token=" + passwordDto.getToken() + "&failed";
        }

        User one = userRepository.getOne(Long.valueOf(passwordDto.getId()));
        passwordLostService.changeUserPassword(one, passwordDto.getPassword(), passwordDto.getToken());
        return "redirect:/savePassword?success";
    }

    @GetMapping("/savePassword")
    public String savePasswordResetSite(Model model,
                                        HttpSession session) {

        model.addAttribute("msg", "Zmiana hasła przebiegła pomyślnie");
        session.invalidate();
        return "user/savePassword";
    }

    @PostMapping("/savePassword")
    public String redirectPasswordResetSite() {
        return "redirect:/login?passChanged";
    }
}

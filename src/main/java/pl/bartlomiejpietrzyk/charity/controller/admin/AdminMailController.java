package pl.bartlomiejpietrzyk.charity.controller.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bartlomiejpietrzyk.charity.email.EmailServiceImpl;
import pl.bartlomiejpietrzyk.charity.email.MailObject;
import pl.bartlomiejpietrzyk.charity.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin/mail")
public class AdminMailController {
    private final EmailServiceImpl emailService;
    private final UserRepository userRepository;

    @Autowired
    public AdminMailController(EmailServiceImpl emailService, UserRepository userRepository) {
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String showEmailsPage() {
        return "redirect:/admin/main/send";
    }

    @GetMapping("/user")
    public String createMail(Model model,
                             HttpServletRequest request) {
        request.getRequestURL().substring(
                request.getRequestURL().lastIndexOf("/") + 1);
        model.addAttribute("mailObject", new MailObject());
        model.addAttribute("usersList", userRepository.findAll());
        return "admin/emailForm";
    }

    @PostMapping("/send")
    public String createMail(@ModelAttribute("mailObject") @Valid MailObject mailObject,
                             Errors errors) {
        if (errors.hasErrors()) {
            return "redirect:/admin/main/send?failed";
        }
        emailService.sendSimpleMessage(mailObject.getTo(),
                mailObject.getSubject(), mailObject.getText());

        return "redirect:/admin/mail/user?success";
    }
}
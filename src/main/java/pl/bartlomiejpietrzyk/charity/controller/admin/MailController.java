package pl.bartlomiejpietrzyk.charity.controller.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bartlomiejpietrzyk.charity.email.EmailServiceImpl;
import pl.bartlomiejpietrzyk.charity.email.MailObject;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin/mail")
public class MailController {
    private EmailServiceImpl emailService;
    public SimpleMailMessage template;

    @Autowired
    public MailController(EmailServiceImpl emailService, SimpleMailMessage template) {
        this.emailService = emailService;
        this.template = template;
    }

    @GetMapping
    public String showEmailsPage() {
        return "redirect:/admin/main/send";
    }

    @GetMapping("/send")
    public String createMail(Model model,
                             HttpServletRequest request) {
        request.getRequestURL().substring(
                request.getRequestURL().lastIndexOf("/") + 1);
        model.addAttribute("mailObject", new MailObject());
        return "admin/emailSend";
    }

    @PostMapping("/send")
    public String createMail(@ModelAttribute("mailObject") @Valid MailObject mailObject,
                             Errors errors) {
        if (errors.hasErrors()) {
            return "redirect:/admin/main/send?failed";
        }
        emailService.sendSimpleMessage(mailObject.getTo(),
                mailObject.getSubject(), mailObject.getText());

        return "redirect:/admin/mail/send?success";
    }
}
package pl.coderslab.charity.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.coderslab.charity.email.EmailServiceImpl;
import pl.coderslab.charity.email.MailObject;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/admin/mail")
public class MailController {
    @Autowired
    private EmailServiceImpl emailService;

    //    public MailController(EmailServiceImpl emailService) {
//        this.emailService = emailService;
//    }
//
    private String attachmentPath;

    @Autowired
    public SimpleMailMessage template;

    private static final Map<String, Map<String, String>> labels;

    static {
        labels = new HashMap<>();

        //Simple email
        Map<String, String> props = new HashMap<>();
        props.put("headerText", "Send Simple Email");
        props.put("messageLabel", "Message");
        props.put("additionalInfo", "");
        labels.put("send", props);

        //Email with template
        props = new HashMap<>();
        props.put("headerText", "Send Email Using Template");
        props.put("messageLabel", "Template Parameter");
        props.put("additionalInfo",
                "The parameter value will be added to the following message template:<br>" +
                        "<b>This is the test email template for your email:<br>'Template Parameter'</b>"
        );
        labels.put("sendTemplate", props);

        //Email with attachment
        props = new HashMap<>();
        props.put("headerText", "Send Email With Attachment");
        props.put("messageLabel", "Message");
        props.put("additionalInfo", "To make sure that you send an attachment with this email, change the value for the 'attachment.invoice' in the application.properties file to the path to the attachment.");
        labels.put("sendAttachment", props);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showEmailsPage() {
        return "redirect:/admin/main/send";
    }

    @RequestMapping(value = {"/send", "/sendTemplate", "/sendAttachment"}, method = RequestMethod.GET)
    public String createMail(Model model,
                             HttpServletRequest request) {
        String action = request.getRequestURL().substring(
                request.getRequestURL().lastIndexOf("/") + 1
        );
        Map<String, String> props = labels.get(action);
        Set<String> keys = props.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            model.addAttribute(key, props.get(key));
        }

        model.addAttribute("mailObject", new MailObject());
        return "admin/emailSend";
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public String createMail(Model model,
                             @ModelAttribute("mailObject") @Valid MailObject mailObject,
                             Errors errors) {
        if (errors.hasErrors()) {
            return "redirect:/admin/main/send?failed";
        }
        emailService.sendSimpleMessage(mailObject.getTo(),
                mailObject.getSubject(), mailObject.getText());

        return "redirect:/admin/mail/send?success";
    }

    @RequestMapping(value = "/sendTemplate", method = RequestMethod.POST)
    public String createMailWithTemplate(Model model,
                                         @ModelAttribute("mailObject") @Valid MailObject mailObject,
                                         Errors errors) {
        if (errors.hasErrors()) {
            return "redirect:/admin/main/send?failed";
        }
        emailService.sendSimpleMessageUsingTemplate(mailObject.getTo(),
                mailObject.getSubject(),
                template,
                mailObject.getText());

        return "redirect:/admin/main/sendTemplate?success";
    }

    @RequestMapping(value = "/sendAttachment", method = RequestMethod.POST)
    public String createMailWithAttachment(Model model,
                                           @ModelAttribute("mailObject") @Valid MailObject mailObject,
                                           Errors errors) {
        if (errors.hasErrors()) {
            return "redirect:/admin/main/send?failed";
        }
        emailService.sendMessageWithAttachment(
                mailObject.getTo(),
                mailObject.getSubject(),
                mailObject.getText(),
                attachmentPath
        );

        return "redirect:/admin/main/sendAttachment?success";
    }
}
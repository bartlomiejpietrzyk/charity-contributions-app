package pl.bartlomiejpietrzyk.charity.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.bartlomiejpietrzyk.charity.email.EmailServiceImpl;
import pl.bartlomiejpietrzyk.charity.email.MailObject;
import pl.bartlomiejpietrzyk.charity.entity.Message;
import pl.bartlomiejpietrzyk.charity.repository.MessageRepository;

@Controller
@RequestMapping("/admin/messages")
public class AdminMessageController {

    private MessageRepository messageRepository;
    private EmailServiceImpl emailService;

    @Autowired
    public AdminMessageController(MessageRepository messageRepository, EmailServiceImpl emailService) {
        this.messageRepository = messageRepository;
        this.emailService = emailService;
    }

    @GetMapping
    public String showMessagesList(Model model,
                                   @RequestParam(defaultValue = "0") int page) {
        model.addAttribute("messages", messageRepository
                .findAll(new PageRequest(page, 10)));
        model.addAttribute("currentPage", page);
        return "admin/messagesList";
    }

    @GetMapping("/details")
    public String showMessage(Model model,
                              @RequestParam("id") Long id) {
        Message message = messageRepository.getOne(id);
        model.addAttribute("message", message);
        MailObject mailObject = new MailObject();
        model.addAttribute("answer", mailObject);
        return "admin/messageDetails";
    }

    @PostMapping("/details")
    public String sendMessage(@ModelAttribute("answer") MailObject mailObject) {
        emailService.sendSimpleMessage(mailObject.getTo(), mailObject.getSubject(), mailObject.getText());
        return "redirect:/admin/messages?messageSent";
    }
}

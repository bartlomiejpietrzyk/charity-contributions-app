package pl.bartlomiejpietrzyk.charity.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.bartlomiejpietrzyk.charity.dto.UserContactDto;
import pl.bartlomiejpietrzyk.charity.email.EmailServiceImpl;
import pl.bartlomiejpietrzyk.charity.entity.Message;
import pl.bartlomiejpietrzyk.charity.service.MessageService;

@Controller
public class ContactMessageController {

    private final MessageService messageService;
    private final EmailServiceImpl emailService;

    @Autowired
    public ContactMessageController(MessageService messageService, EmailServiceImpl emailService) {
        this.messageService = messageService;
        this.emailService = emailService;
    }

    @ModelAttribute("contactMessage")
    public UserContactDto contactMessage() {
        return new UserContactDto();
    }

    @PostMapping("/sendMessage")
    public String contactMessageForm(@ModelAttribute("contactMessage") UserContactDto userContactDto, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/?messageNotSend";
        }
        Message message = messageService.sendMessage(userContactDto);
        emailService.sendSimpleMessage(userContactDto.getContactEmail(), userContactDto.getContactTitle(),
                "Charity Contributions" +
                        "\nPotwierdzenie wysłania wiadomości." +
                        "\nID: " + message.getId() +
                        "\nTytuł: " + userContactDto.getContactTitle() +
                        "\nWiadomość: " + userContactDto.getContactMessage() +
                        "\nW przeciągu 48h odpowiemy na Twoje zgłoszenie!");
        return "redirect:/?messageSent";

    }
}

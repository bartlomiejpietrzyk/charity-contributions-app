package pl.bartlomiejpietrzyk.charity.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.bartlomiejpietrzyk.charity.dto.UserContactDto;
import pl.bartlomiejpietrzyk.charity.service.MessageService;

@Controller
public class ContactMessageController {

    private final MessageService messageService;

    @Autowired
    public ContactMessageController(MessageService messageService) {
        this.messageService = messageService;
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
        messageService.sendMessage(userContactDto);
        return "redirect:/?messageSent";

    }
}

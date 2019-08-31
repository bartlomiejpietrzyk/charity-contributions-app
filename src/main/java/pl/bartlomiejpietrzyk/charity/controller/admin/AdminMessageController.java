package pl.bartlomiejpietrzyk.charity.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.bartlomiejpietrzyk.charity.repository.MessageRepository;

@Controller
@RequestMapping("/admin/messages")
public class AdminMessageController {

    private MessageRepository messageRepository;

    @Autowired
    public AdminMessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping
    public String showMessagesList(Model model,
                                   @RequestParam(defaultValue = "0") int page) {
        model.addAttribute("messages", messageRepository
                .findAll(new PageRequest(page, 10)));
        model.addAttribute("currentPage", page);
        return "admin/messagesList";
    }
}

package pl.coderslab.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.dto.UserContactDto;
import pl.coderslab.charity.entity.Message;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.repository.MessageRepository;
import pl.coderslab.charity.repository.UserRepository;

@Service
@Transactional
public class MessageService {
    private UserRepository userRepository;
    private MessageRepository messageRepository;

    @Autowired
    public MessageService(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    public void sendMessage(UserContactDto contactDto) {
        Message message = new Message();
        User one = userRepository.getOne(Long.valueOf(contactDto.getUserId()));
        message.setUserId(one.getId());
        message.setContactFirstName(contactDto.getContactFirstName());
        message.setContactLastName(contactDto.getContactLastName());
        message.setUserMessage(contactDto.getUserMessage());
        messageRepository.save(message);
    }

}

package pl.bartlomiejpietrzyk.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bartlomiejpietrzyk.charity.dto.UserContactDto;
import pl.bartlomiejpietrzyk.charity.entity.Message;
import pl.bartlomiejpietrzyk.charity.repository.MessageRepository;
import pl.bartlomiejpietrzyk.charity.repository.UserRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class MessageService {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    public Message sendMessage(UserContactDto contactDto) throws NullPointerException {
        Message message = new Message();
        if (contactDto.getUserId() != null) {
            message.setUserId(userRepository.getOne(contactDto.getUserId()).getId());
        }
        message.setContactFirstName(contactDto.getContactFirstName());
        message.setContactLastName(contactDto.getContactLastName());
        message.setContactEmail(contactDto.getContactEmail());
        message.setContactTitle(contactDto.getContactTitle());
        message.setContactMessage(contactDto.getContactMessage());
        return messageRepository.save(message);
    }

}

package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.charity.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}

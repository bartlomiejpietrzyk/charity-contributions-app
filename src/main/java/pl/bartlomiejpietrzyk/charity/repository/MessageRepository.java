package pl.bartlomiejpietrzyk.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bartlomiejpietrzyk.charity.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}

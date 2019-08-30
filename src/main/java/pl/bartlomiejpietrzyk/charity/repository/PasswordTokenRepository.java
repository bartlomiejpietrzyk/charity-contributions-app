package pl.bartlomiejpietrzyk.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bartlomiejpietrzyk.charity.entity.PasswordToken;

@Repository
public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Long> {
    PasswordToken findByToken(String token);
}

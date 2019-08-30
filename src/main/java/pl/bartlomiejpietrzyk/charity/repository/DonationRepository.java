package pl.bartlomiejpietrzyk.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bartlomiejpietrzyk.charity.entity.Donation;
import pl.bartlomiejpietrzyk.charity.entity.User;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    Long countAllByUser(User user);

    List<Donation> findAllByUser(User user);
}
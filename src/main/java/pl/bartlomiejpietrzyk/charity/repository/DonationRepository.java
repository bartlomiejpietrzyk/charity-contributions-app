package pl.bartlomiejpietrzyk.charity.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bartlomiejpietrzyk.charity.entity.Donation;
import pl.bartlomiejpietrzyk.charity.entity.User;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    Long countAllByUser(User user);

    Page<Donation> findAllByUser(User user, Pageable pageable);

    Long countUserDonationsByStatusNameEqualsAndUserId(String status, Long id);

    Long countUserQuantityDonationsByStatusNameEqualsAndUserId(String status, Long id);

    List<Donation> findAllByUser(User user);
}
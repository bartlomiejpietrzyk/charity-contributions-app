package pl.bartlomiejpietrzyk.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bartlomiejpietrzyk.charity.entity.DonationStatus;

import java.util.List;

@Repository
public interface DonationStatusRepository extends JpaRepository<DonationStatus, Long> {
    List<DonationStatus> findAll();

    DonationStatus findByName(String name);
}

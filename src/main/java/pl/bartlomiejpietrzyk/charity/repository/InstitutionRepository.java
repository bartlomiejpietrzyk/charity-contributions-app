package pl.bartlomiejpietrzyk.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bartlomiejpietrzyk.charity.entity.Institution;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {
}

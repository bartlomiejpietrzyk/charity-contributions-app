package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.charity.entity.Donation;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    @Override
    List<Donation> findAll();

    @Override
    Donation getOne(Long aLong);

    @Override
    <S extends Donation> S save(S s);

    @Override
    long count();

    @Override
    void delete(Donation donation);

}

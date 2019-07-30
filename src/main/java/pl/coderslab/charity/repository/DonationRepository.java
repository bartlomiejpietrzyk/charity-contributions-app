package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.charity.entity.Donation;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Long, Donation> {
    @Override
    List<Long> findAll();

    @Override
    Long getOne(Donation donation);

    @Override
    <S extends Long> S save(S s);

    @Override
    long count();

    @Override
    void delete(Long aLong);
}

package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.charity.entity.Institution;

import java.util.List;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {

    @Override
    List<Institution> findAll();

    @Override
    Institution getOne(Long aLong);

    @Override
    <S extends Institution> S save(S s);

    @Override
    long count();

    @Override
    void deleteById(Long aLong);
}

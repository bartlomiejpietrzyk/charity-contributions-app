package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.charity.entity.Institution;

import java.util.List;

public interface InstitutionRepository extends JpaRepository<Long, Institution> {

    @Override
    List<Long> findAll();

    @Override
    Long getOne(Institution institution);

    @Override
    <S extends Long> S save(S s);

    @Override
    long count();

    @Override
    void deleteById(Institution institution);
}

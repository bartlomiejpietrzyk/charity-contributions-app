package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.charity.entity.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Long, Category> {
    @Override
    List<Long> findAll();

    @Override
    Long getOne(Category category);

    @Override
    <S extends Long> S save(S s);

    @Override
    long count();

    @Override
    void delete(Long aLong);
}

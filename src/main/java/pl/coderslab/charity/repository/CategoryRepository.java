package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.charity.entity.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Override
    List<Category> findAll();

    @Override
    Category getOne(Long aLong);

    @Override
    <S extends Category> S save(S s);

    @Override
    void delete(Category category);
}

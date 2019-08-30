package pl.bartlomiejpietrzyk.charity.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bartlomiejpietrzyk.charity.entity.Role;
import pl.bartlomiejpietrzyk.charity.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findOneByUuid(String uuid);

    List<User> findAll();

    List<User> findUsersByRolesEquals(Role role, Pageable pageable);
}
package pl.bartlomiejpietrzyk.charity.service;

import pl.bartlomiejpietrzyk.charity.dto.UserRegistrationDto;
import pl.bartlomiejpietrzyk.charity.entity.User;

public interface UserRegistrationService {
    User findByEmail(String email);

    void saveUser(User user);

    void saveUser(UserRegistrationDto user);

    void saveAdmin(User user);
}

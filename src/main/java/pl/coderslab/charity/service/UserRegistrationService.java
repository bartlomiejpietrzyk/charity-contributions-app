package pl.coderslab.charity.service;

import pl.coderslab.charity.dto.UserRegistrationDto;
import pl.coderslab.charity.entity.User;

public interface UserRegistrationService {
    User findByEmail(String email);
    void saveUser(User user);

    void saveUser(UserRegistrationDto user);

    void saveAdmin(User user);
}

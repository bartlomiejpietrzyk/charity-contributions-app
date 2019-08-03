package pl.coderslab.charity.service;

import pl.coderslab.charity.entity.User;

public interface UserRegistrationService {
    User findByEmail(String email);
    void saveUser(User user);

    void saveAdmin(User user);
}

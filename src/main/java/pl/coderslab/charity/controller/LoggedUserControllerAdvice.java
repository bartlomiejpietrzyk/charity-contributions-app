package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.repository.UserRepository;

import java.security.Principal;

@ControllerAdvice
public class LoggedUserControllerAdvice {

    private final UserRepository userRepository;

    @Autowired
    public LoggedUserControllerAdvice(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ModelAttribute("loggedUser")
    public String currentUserName(Principal principal) {
        if (principal == null) {
            return "";
        }
        User loggedUser = userRepository.findByEmail(principal.getName());

        return loggedUser.getFirstName() + " " + loggedUser.getLastName();
    }
}

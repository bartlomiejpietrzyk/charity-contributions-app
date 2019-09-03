package pl.bartlomiejpietrzyk.charity.controller.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.bartlomiejpietrzyk.charity.entity.User;
import pl.bartlomiejpietrzyk.charity.repository.UserRepository;

import java.security.Principal;

@ControllerAdvice
public class LoggedUserControllerAdvice {
    @Autowired
    private UserRepository userRepository;

    @ModelAttribute("currentUser")
    public User currentUser(Principal principal) {
        if (principal != null) {
            SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            return userRepository.findByEmail(principal.getName());

        }
        User user = new User();
        user.setFirstName("Dobroczyńco");
        return user;
    }
}

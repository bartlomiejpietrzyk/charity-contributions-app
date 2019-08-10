package pl.coderslab.charity.controller.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.repository.UserRepository;

import java.security.Principal;

@ControllerAdvice
public class LoggedUserControllerAdvice {
    @Autowired
    private UserRepository userRepository;

    @ModelAttribute("currentUser")
    public User currentUser(Principal principal) throws NullPointerException {
        if (principal != null) {
            SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            return userRepository.findByEmail(principal.getName());

        }
        User user = new User();
        user.setFirstName("Dobroczyńco");
        return user;
    }
}

package pl.bartlomiejpietrzyk.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.bartlomiejpietrzyk.charity.entity.User;
import pl.bartlomiejpietrzyk.charity.security.CurrentUser;

import java.util.HashSet;
import java.util.Set;

public class SpringDataUserDetailsService implements UserDetailsService {
    private UserRegistrationService userRegistrationService;

    @Autowired
    public void setUserRepository(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRegistrationService.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        if (!user.getEnabled()) {
            throw new DisabledException("Account not active!");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        user.getRoles().forEach(r ->
                grantedAuthorities.add(new SimpleGrantedAuthority(r.getName())));
        return new CurrentUser(user.getEmail(), user.getPassword(),
                grantedAuthorities, user, user.getEnabled());
    }
}
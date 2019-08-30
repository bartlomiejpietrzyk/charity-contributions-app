package pl.bartlomiejpietrzyk.charity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import pl.bartlomiejpietrzyk.charity.entity.User;

import java.util.Collection;

public class CurrentUser extends org.springframework.security.core.userdetails.User {
    @Autowired
    private final User user;
    private final Boolean enabled;

    public CurrentUser(String username, String password, Collection<? extends GrantedAuthority> authorities, User user, Boolean enabled) {
        super(username, password, authorities);
        this.user = user;
        this.enabled = enabled;
    }

    public User getUser() {
        return user;
    }
}
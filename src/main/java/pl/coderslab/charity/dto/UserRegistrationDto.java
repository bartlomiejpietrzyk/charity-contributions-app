package pl.coderslab.charity.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import pl.coderslab.charity.entity.Role;
import pl.coderslab.charity.validator.FieldMatch;
import pl.coderslab.charity.validator.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Setter
@Getter
@FieldMatch.List({
        @FieldMatch(first = "password", second = "passwordConfirm", message = "The password fields must match"),
})
public class UserRegistrationDto {
    @Autowired
    public UserRegistrationDto() {
    }

    @NotEmpty
    private String uuid;
    @NotEmpty
    @Length(min = 1)
    private String firstName;
    @NotEmpty
    @Length(min = 1)
    public String lastName;
    @Email
    @NotEmpty
    public String email;
    @Length(min = 8, max = 32)
    @NotEmpty
    @ValidPassword
    public String password;
    @Length(min = 8, max = 32)
    @NotEmpty
    public String passwordConfirm;
    @NotEmpty
    private String enabled;
    @NotEmpty
    private Set<Role> roles;

}

package pl.coderslab.charity.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.validator.FieldMatch;
import pl.coderslab.charity.validator.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "passwordConfirm", message = "The password fields must match"),
})
@Setter
@Getter
public class UserEditDto {
    private String id;
    @NotEmpty
    @Length(min = 1)
    private String firstName;
    @NotEmpty
    @Length(min = 1)
    private String lastName;
    @Email
    @NotEmpty
    private String email;
    @ValidPassword
    private String password;
    private String passwordConfirm;

    @Autowired
    public UserEditDto() {
    }

    public UserEditDto(User that) {
        this.id = String.valueOf(that.getId());
        this.firstName = that.getFirstName();
        this.lastName = that.getLastName();
        this.email = that.getEmail();
        this.password = that.getPassword();
    }
}

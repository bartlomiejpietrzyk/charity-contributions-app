package pl.coderslab.charity.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.validator.FieldMatch;
import pl.coderslab.charity.validator.ValidPassword;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@FieldMatch.List({
        @FieldMatch(first = "password", second = "passwordConfirm", message = "The password fields must match"),
})
public class UserChangePasswordDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    @Length(min = 8, max = 32)
    @NotEmpty
    @ValidPassword
    private String password;
    @Length(min = 8, max = 32)
    @NotEmpty
    private String passwordConfirm;

    @Autowired
    public UserChangePasswordDto() {
    }

    public UserChangePasswordDto(User that) {
        this.id = String.valueOf(that.getId());
        this.firstName = that.getFirstName();
        this.lastName = that.getLastName();
        this.email = that.getEmail();
        this.password = that.getPassword();
    }
}

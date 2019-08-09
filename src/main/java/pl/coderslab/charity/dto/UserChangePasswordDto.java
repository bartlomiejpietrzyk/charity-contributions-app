package pl.coderslab.charity.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.validator.FieldMatch;
import pl.coderslab.charity.validator.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@ToString
@Setter
@Getter
@FieldMatch.List({
        @FieldMatch(first = "password", second = "passwordConfirm", message = "The password fields must match"),
})
public class UserChangePasswordDto {
    @NotEmpty
    private String id;
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
    public String token;
    @Autowired
    public UserChangePasswordDto() {
    }

    public UserChangePasswordDto(User that) {
        this.id = String.valueOf(that.getId());
        this.firstName = that.getFirstName();
        this.lastName = that.getLastName();
        this.email = that.getEmail();
        this.password = that.getPassword();
        this.token = getToken();
    }
}

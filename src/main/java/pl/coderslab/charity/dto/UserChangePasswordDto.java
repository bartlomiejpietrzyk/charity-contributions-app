package pl.coderslab.charity.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import pl.coderslab.charity.validator.FieldMatch;

@Setter
@Getter
@FieldMatch.List({
        @FieldMatch(first = "password", second = "passwordConfirm", message = "The password fields must match"),
})
public class UserChangePasswordDto {
    private String id;
    private String password;
    private String passwordConfirm;

    @Autowired
    public UserChangePasswordDto() {
    }
}

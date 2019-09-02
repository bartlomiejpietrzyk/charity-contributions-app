package pl.bartlomiejpietrzyk.charity.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import pl.bartlomiejpietrzyk.charity.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

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
    @NotEmpty
//    @ValidPassword
    //todo uncomment validation
    private String password;

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

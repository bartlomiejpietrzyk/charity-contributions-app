package pl.bartlomiejpietrzyk.charity.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class UserLostPasswordDto {

    @Autowired
    public UserLostPasswordDto() {
    }

    @Email
    @NotEmpty
    public String email;
}

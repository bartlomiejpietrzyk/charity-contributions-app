package pl.bartlomiejpietrzyk.charity.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserContactDto {
    private Long userId;
    private String contactFirstName;
    private String contactLastName;
    private String contactEmail;
    private String contactMessage;
    private String contactTitle;
}

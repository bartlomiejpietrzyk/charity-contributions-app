package pl.bartlomiejpietrzyk.charity.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @NotEmpty
    @Length(min = 3)
    private String contactFirstName;
    @NotEmpty
    @Length(min = 3)
    private String contactLastName;
    @NotEmpty
    @Length(min = 5)
    private String contactEmail;
    @NotEmpty
    @Length(min = 3)
    private String contactTitle;
    @NotEmpty
    @Length(min = 25, max = 255)
    private String contactMessage;
    private String contactDateTime;
    @ManyToMany
    @JoinTable(name = "user_message",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();
}

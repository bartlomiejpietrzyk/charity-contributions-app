package pl.coderslab.charity.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long quantity;
    @ManyToMany
    private List<Category> categories;
    @ManyToOne
    private Institution institution;
    @NotNull
    private String mobile;
    @NotEmpty
    private String street;
    @NotEmpty
    private String city;
    @NotEmpty
    private String zipCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String time;
    private String comment;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_donations",
            joinColumns = @JoinColumn(name = "donation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;
    @ManyToOne
    @JoinColumn(name = "status")
    private DonationStatus status;
}

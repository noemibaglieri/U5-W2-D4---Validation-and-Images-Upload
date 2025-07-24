package noemibaglieri.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "authors")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    private String avatar;

    public Author(String firstName, String lastName, String email, String dateOfBirth, String avatar) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.avatar = avatar;
    }
}

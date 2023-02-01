package africa.semicolon.unicoin.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    @Email(message = "This field require a valid email address")
    private String emailAddress;
    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    private String firstName;
    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    private String lastName;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private Boolean isDisabled;

    public User( String emailAddress, String firstName, String lastName, String password, UserRole userRole) {

        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userRole = userRole;

    }
}

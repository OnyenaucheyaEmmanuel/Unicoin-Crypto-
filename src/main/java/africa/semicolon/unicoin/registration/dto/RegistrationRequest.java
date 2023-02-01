package africa.semicolon.unicoin.registration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;

}

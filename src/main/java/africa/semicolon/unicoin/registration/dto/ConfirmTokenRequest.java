package africa.semicolon.unicoin.registration.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConfirmTokenRequest {
    @NotNull
    private String token;
    @NotNull
    private String emailAddress;
}

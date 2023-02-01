package africa.semicolon.unicoin.registration.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordUpdateResponse {
    @NotNull
    private String message;

}

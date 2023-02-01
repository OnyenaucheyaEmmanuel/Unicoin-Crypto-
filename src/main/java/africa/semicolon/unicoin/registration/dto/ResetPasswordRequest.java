package africa.semicolon.unicoin.registration.dto;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String email;
    private String newPassword;
}

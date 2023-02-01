package africa.semicolon.unicoin.user;

import africa.semicolon.unicoin.registration.dto.*;
import africa.semicolon.unicoin.registration.resetToken.ResetPasswordToken;
import jakarta.mail.MessagingException;

public interface UserService {
    public String createAccount(User user);
    void enableUser(String emailAddress);

    String resetPassword(ResetPasswordRequest resetPasswordRequest);
    String forgetPassword(ForgetPasswordRequest forgetPasswordRequest) throws MessagingException;
    String generateToken(String email);
    String confirmResetPasswordToken(ConfirmTokenRequest confirmTokenRequest);
    User getUserByEmailAddress(String email);
    String login(LoginRequest loginRequest);
    String deleteAccountByEmail(String email, PasswordRequest passwordRequest);


}

package africa.semicolon.unicoin.user;

import africa.semicolon.unicoin.email.EmailSender;
import africa.semicolon.unicoin.exceptions.GenericHandler;
import africa.semicolon.unicoin.exceptions.RegistrationException;
import africa.semicolon.unicoin.registration.dto.*;
import africa.semicolon.unicoin.registration.resetToken.ResetPasswordToken;
import africa.semicolon.unicoin.registration.resetToken.ResetPasswordTokenService;
import africa.semicolon.unicoin.registration.token.ConfirmationToken;
import africa.semicolon.unicoin.registration.token.ConfirmationTokenService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    @Autowired
    private ResetPasswordTokenService resetPasswordTokenService;
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String createAccount(User user) {
       User savedUser = userRepository.save(user);
       return generateToken(savedUser);
    }

    private String generateToken(User savedUser) {
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10),
                savedUser
        );
        confirmationTokenService.savedConfirmationToken(confirmationToken);
        return token;
    }

    @Override
    public void enableUser(String emailAddress) {
        userRepository.enable(emailAddress);

    }

    @Override
    public String resetPassword(ResetPasswordRequest resetPasswordRequest) {
        User user = userRepository.findByEmailAddressIgnoreCase(resetPasswordRequest.getEmail())
                .orElseThrow(()->new IllegalStateException("This email cannot be found"));
        user.setPassword(resetPasswordRequest.getNewPassword());
        userRepository.save(user);
        return "Your password has been changed successfully";
    }

    @Override
    public String forgetPassword(ForgetPasswordRequest forgetPasswordRequest) throws MessagingException {
        User forgetUser = userRepository.findByEmailAddressIgnoreCase(forgetPasswordRequest.getEmail())
                .orElseThrow(()->new IllegalStateException("This email is not registered"));
        String token = UUID.randomUUID().toString();

        ResetPasswordToken resetPasswordToken =
                new ResetPasswordToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(10), forgetUser);
        resetPasswordTokenService.saveResetPasswordToken(resetPasswordToken);
        emailSender.sendEmail(forgetPasswordRequest.getEmail(), token);

        return "We have sent a reset password link to your email. Please check.";
    }

    @Override
    public String generateToken(String email) {
        var foundUser = userRepository.findByEmailAddressIgnoreCase(email)
                .orElseThrow(()-> new GenericHandler(String.format("%s does not exist in User Service!!", email)));
        return generateToken(foundUser);
    }

    @Override
    public String confirmResetPasswordToken(ConfirmTokenRequest confirmTokenRequest) {
        ResetPasswordToken resetPasswordToken = resetPasswordTokenService.getResetPasswordToken(confirmTokenRequest.getToken())
                .orElseThrow(()->new RuntimeException("Token does not exist"));

        if (resetPasswordToken.getExpiredAt().isBefore(LocalDateTime.now()))
            throw new RuntimeException("Token has expired");

        if (resetPasswordToken.getConfirmedAt() != null)
            throw  new RuntimeException("Token has been used");
        return "confirmed";
    }

    @Override
    public User getUserByEmailAddress(String email) {
        return userRepository.
                findByEmailAddressIgnoreCase(email).orElseThrow(()->new RegistrationException(String.format("%s does not exist in regististration service", email)));
    }

    @Override
    public String login(LoginRequest loginRequest) {
        User user = getUserByEmailAddress(loginRequest.getEmail());
        if (user.getIsDisabled()) throw new GenericHandler("User hasn't been verified");
        if (!user.getPassword().equals(loginRequest.getPassword()))throw new GenericHandler("Incorrect password");
        return "Login Successful";
    }

    @Override
    public String deleteAccountByEmail(String email, PasswordRequest passwordRequest) {
        String token = UUID.randomUUID().toString();
        String tokenEncrypt = passwordEncoder.encode(token);
        User user = getUserByEmailAddress(email);

        if (!user.getPassword().equals(passwordRequest.getPassword())) throw new GenericHandler("Invalid password");
        String userEmail = user.getEmailAddress();
        String deletedEmail = "Deleted" +  userEmail + tokenEncrypt;
        user.setEmailAddress(deletedEmail);
        userRepository.save(user);
        return "Account delete successfully";
    }

}










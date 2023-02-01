package africa.semicolon.unicoin.mockUtils;

import africa.semicolon.unicoin.email.EmailSender;
import africa.semicolon.unicoin.registration.RegistrationService;
import africa.semicolon.unicoin.registration.resetToken.ResetPasswordTokenRepository;
import africa.semicolon.unicoin.registration.resetToken.ResetPasswordTokenService;
import africa.semicolon.unicoin.registration.token.ConfirmationTokenRepository;
import africa.semicolon.unicoin.registration.token.ConfirmationTokenService;
import africa.semicolon.unicoin.user.UserRepository;
import africa.semicolon.unicoin.user.UserService;
import africa.semicolon.unicoin.user.UserServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.mock;

public class MockUtils {
    public static final UserRepository userRepositoryMock = mock(UserRepository.class);
    public static final EmailSender emailSenderMock = mock(EmailSender.class);
    public static final PasswordEncoder passwordEncoderMock = mock(PasswordEncoder.class);
    public static final ResetPasswordTokenService resetPasswordServiceMock = mock(ResetPasswordTokenService.class);
    public static final ConfirmationTokenRepository tokenRepositoryMock = mock(ConfirmationTokenRepository.class);
    public static final UserService userServiceMock = mock(UserService.class);
    public static final ResetPasswordTokenRepository resetPasswordTokenRepositoryMock = mock(ResetPasswordTokenRepository.class);

    public static UserService userService() {
        return new UserServiceImpl(userRepositoryMock,
                confirmationTokenMock(),
                resetPasswordServiceMock,
                emailSenderMock,
                passwordEncoderMock
        );
    }

    public static ConfirmationTokenService confirmationTokenMock() {
        return new ConfirmationTokenService(tokenRepositoryMock);
    }

    public static RegistrationService registrationServiceMock() {
        return new RegistrationService(
                userRepositoryMock,
                userServiceMock,
                emailSenderMock,
                MockUtils.confirmationTokenMock()

        );
    }
    public static ResetPasswordTokenService ResetPasswordServiceMock(){
        return new ResetPasswordTokenService(resetPasswordTokenRepositoryMock);
    }
}
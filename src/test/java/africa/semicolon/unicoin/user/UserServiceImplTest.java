package africa.semicolon.unicoin.user;

import africa.semicolon.unicoin.mockUtils.*;
import org.junit.jupiter.api.Test;

import static africa.semicolon.unicoin.mockUtils.MockUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    private final UserServiceImpl userService = new UserServiceImpl(
            userRepositoryMock,
            confirmationTokenMock(),
            resetPasswordServiceMock,
            emailSenderMock,
            passwordEncoderMock
    );

    @Test
    void createAccount() {

    }

    @Test
    void enableUser() {
    }

    @Test
    void generateToken() {
    }

    @Test
    void forgotPassword() {
    }

    @Test
    void confirmResetPasswordToken() {
    }

    @Test
    void resetPassword() {
    }

    @Test
    void getUserByEmailAddress() {
    }

    @Test
    void login() {
    }

    @Test
    void deleteAccountByEmail() {
    }
}
package africa.semicolon.unicoin.registration;


import africa.semicolon.unicoin.email.EmailSender;
import africa.semicolon.unicoin.exceptions.RegistrationException;
import africa.semicolon.unicoin.mockUtils.MockUtils;
import africa.semicolon.unicoin.registration.dto.ConfirmTokenRequest;
import africa.semicolon.unicoin.registration.dto.RegistrationRequest;
import africa.semicolon.unicoin.registration.token.ConfirmationToken;
import africa.semicolon.unicoin.registration.token.ConfirmationTokenService;
import africa.semicolon.unicoin.user.User;
import africa.semicolon.unicoin.user.UserRepository;
import africa.semicolon.unicoin.user.UserService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static africa.semicolon.unicoin.mockUtils.MockUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class RegistrationMockTest {
    private final UserService userServiceMock = spy(MockUtils.userService());
    private final ConfirmationTokenService confirmationTokenService = spy(MockUtils.confirmationTokenMock());
    private final RegistrationService registrationService = new RegistrationService(
            userRepositoryMock,
             userServiceMock,
             emailSenderMock,
             confirmationTokenMock()
    );

    @Test public void testRegister() throws MessagingException{
        RegistrationRequest registrationRequest = new RegistrationRequest(
                "kenny",
                "ibrahim",
                "ibrahimkenny@testing.com",
                "part1234"
        );
        doReturn("94b199ea-614e-4a16-9f50-f94f5611bae9")
                .when(userServiceMock).createAccount(any(User.class));

        assertEquals(registrationService.register(registrationRequest), "94b199ea-614e-4a16-9f50-f94f5611bae9");
    }

    @Test public void testResendToken() throws MessagingException {
        Optional<User> user = Optional.of(new User());
        doReturn(user).when(userRepositoryMock).findByEmailAddressIgnoreCase("12374@gmail.com");
        doReturn("85656674-1488-4d64-aca6-e78ff6d757fc")
                .when(userServiceMock).generateToken(any(String.class));
//        assertEquals("Token sent!!!", registrationService.resendToken("12374@gmail.com"));
    }

    @Test void testConfirmToken(){
        ConfirmTokenRequest tokenRequest = new ConfirmTokenRequest();
        tokenRequest.setEmailAddress("jas@gmail.com");
        tokenRequest.setToken("85656674-1488-4d64-aca6-e78ff6d757fc");

        Optional<ConfirmationToken> confirmationToken = Optional.of(new ConfirmationToken());
        confirmationToken.get().setToken(tokenRequest.getToken());
        confirmationToken.get().setCreateAt(LocalDateTime.now());
        confirmationToken.get().setExpireAt(LocalDateTime.now().plusMinutes(10));
        doReturn(confirmationToken).when(tokenRepositoryMock).findByToken(tokenRequest.getToken());

        assertEquals("confirmed", registrationService.confirmToken(tokenRequest));
    }

    @Test void testConfirmTokenThrowsException(){
        ConfirmTokenRequest tokenRequest = new ConfirmTokenRequest();
        tokenRequest.setToken("lakes@gmail.com");
        tokenRequest.setToken("85656674-1488-4d64-aca6-e78ff6d757fc");

        Optional<ConfirmationToken> confirmationToken = Optional.of(new ConfirmationToken());
        confirmationToken.get().setToken(tokenRequest.getToken());
        confirmationToken.get().setCreateAt(LocalDateTime.now());
        confirmationToken.get().setExpireAt(LocalDateTime.now().plusSeconds(0));
        doReturn(confirmationToken).when(tokenRepositoryMock).findByToken(tokenRequest.getToken());

        assertThrows(RegistrationException.class, ()-> registrationService.confirmToken(tokenRequest));
    }
}

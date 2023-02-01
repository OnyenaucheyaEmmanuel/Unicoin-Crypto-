package africa.semicolon.unicoin.registration;

import africa.semicolon.unicoin.email.EmailSender;
import africa.semicolon.unicoin.registration.dto.ConfirmTokenRequest;
import africa.semicolon.unicoin.registration.dto.RegistrationRequest;
import africa.semicolon.unicoin.registration.dto.ResendTokenRequest;
import africa.semicolon.unicoin.registration.token.ConfirmationTokenService;
import africa.semicolon.unicoin.user.User;
import africa.semicolon.unicoin.user.UserRepository;
import africa.semicolon.unicoin.user.UserRole;
import africa.semicolon.unicoin.user.UserService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegistrationService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    EmailSender emailSender;

    @Autowired
    ConfirmationTokenService confirmationTokenService;

    public String register(RegistrationRequest registrationRequest) throws MessagingException {
        Boolean emailExists = userRepository.
                findByEmailAddressIgnoreCase(registrationRequest.getEmailAddress()).
                isPresent();
        if (emailExists) throw new IllegalStateException("Email Address already exist");
        String token = userService.createAccount(new User(
                registrationRequest.getEmailAddress(),
                registrationRequest.getFirstName(),
                registrationRequest.getLastName(),
                registrationRequest.getPassword(),
                UserRole.USER));
        emailSender.send(registrationRequest.getEmailAddress(), buildEmail(registrationRequest.getFirstName(), token));
        return token;
    }
    private void emailSender(String emailAddress, String firstName, String token) throws MessagingException {
        emailSender.send(emailAddress, buildEmail(firstName, token));
    }

    public String resendToken(ResendTokenRequest tokenResendRequest) throws MessagingException {
        User user = userService.getUserByEmailAddress(tokenResendRequest.getEmailAddress());
        String token = UUID.randomUUID().toString();
        emailSender(user.getEmailAddress(), user.getFirstName(), token);
        return "Resend Successful";
    }

    public String confirmToken(ConfirmTokenRequest confirmationToken) {
        var token = confirmationTokenService.getConfirmationToken(confirmationToken.getToken()).orElseThrow(() -> new IllegalStateException("Token does not exist"));
        if (token.getExpireAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token has expired");
        }
        if (token.getConfirmedAt() != null) {
            throw new IllegalStateException("Token has been used");
        }
        confirmationTokenService.setConfirmedAt(token.getToken());
        userService.enableUser(confirmationToken.getEmailAddress());
        return "confirmed";
    }


    public String buildEmail(String name, String link) {

        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

}

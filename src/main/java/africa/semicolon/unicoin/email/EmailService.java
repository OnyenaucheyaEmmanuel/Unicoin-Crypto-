package africa.semicolon.unicoin.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
@RequiredArgsConstructor
public class EmailService implements EmailSender {
    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    @Override
    public void send (String to, String email) throws MessagingException {
        try {
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailMessage, "utf-8");
            mimeMessageHelper.setSubject("Confirm your email address");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom("kenny@gmail.com");
            mimeMessageHelper.setText(email, true);
            javaMailSender.send(mailMessage);
        } catch (MessagingException e) {
            log.info("Problem 1: " + e.getMessage());
            throw new RuntimeException(e);
        }catch (MailException e){
            log.info("Problem 2: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendEmail(String recipientEmail, String link) throws MessagingException {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom("kenny@gmail.com");
            helper.setTo(recipientEmail);
            String subject = "Here's the link to reset your password";

            String content = "<p>Hello,</p>"
                    + "<p>You have requested to reset your password.</p>"
                    + "<p>Click the link below to change your password:</p>"
                    + "<p><a href=\"" + link + "\">Change my password</a></p>"
                    + "<br>"
                    + "<p>Ignore this email if you do remember your password, "
                    + "or you have not made the request.</p>";

            helper.setSubject(subject);

            helper.setText(content, true);

            javaMailSender.send(message);
        }
}


package africa.semicolon.unicoin.registration.token;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class DeleteExpiredTokenScheduler {
    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Scheduled(cron = "0 43 11 * * *")
    public void deleteExpiredToken(){
        System.out.println("Deleted");
        confirmationTokenService.deleteExpiredToken();

    }
}

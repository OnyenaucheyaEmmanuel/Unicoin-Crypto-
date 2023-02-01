package africa.semicolon.unicoin.registration.token;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    public void savedConfirmationToken(ConfirmationToken confirmationToken){
        confirmationTokenRepository.save(confirmationToken);

    }

    public Optional<ConfirmationToken> getConfirmationToken(String token){
        return confirmationTokenRepository.findByToken(token);

    }

//    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredToken(){
        confirmationTokenRepository.deleteConfirmationTokensByExpireAtBefore(LocalDateTime.now());
    }
    public void setConfirmedAt(String token){
        confirmationTokenRepository.setConfirmAt(LocalDateTime.now(), token);

    }

}

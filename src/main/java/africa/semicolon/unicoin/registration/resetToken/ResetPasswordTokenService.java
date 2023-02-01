package africa.semicolon.unicoin.registration.resetToken;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ResetPasswordTokenService {
    @Autowired
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    public void saveResetPasswordToken(ResetPasswordToken resetPasswordToken){
        resetPasswordTokenRepository.save(resetPasswordToken);
    }

    public Optional<ResetPasswordToken> getResetPasswordToken(String token){
       return resetPasswordTokenRepository.findByToken(token);
    }
    public void deleteExpiredToken(){
        resetPasswordTokenRepository.deleteResetPasswordTokensByExpiredAtBefore(LocalDateTime.now());
    }
    public void setConfirmedAt(ResetPasswordToken token){
        resetPasswordTokenRepository.setConfirmedAt(token);
    }
}

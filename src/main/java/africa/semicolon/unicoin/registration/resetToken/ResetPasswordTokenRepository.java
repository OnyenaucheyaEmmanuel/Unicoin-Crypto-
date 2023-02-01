package africa.semicolon.unicoin.registration.resetToken;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Long> {
    Optional<ResetPasswordToken> findByToken(String token);

    @Transactional
    void deleteResetPasswordTokensByExpiredAtBefore(LocalDateTime currentTime);

    @Transactional
    @Modifying
    @Query("UPDATE ResetPasswordToken resetPasswordToken " +
            "SET resetPasswordToken.confirmedAt= ?1 " +
            "WHERE resetPasswordToken.confirmedAt = ?2")
    void setConfirmedAt(ResetPasswordToken token);
}

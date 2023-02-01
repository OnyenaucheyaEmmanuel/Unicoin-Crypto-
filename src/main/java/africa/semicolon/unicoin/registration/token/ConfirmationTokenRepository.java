package africa.semicolon.unicoin.registration.token;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findByToken(String token);
    @Transactional
    void deleteConfirmationTokensByExpireAtBefore(LocalDateTime currentTime);
    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken  confirmationToken SET " +
            "confirmationToken.confirmedAt = ?1 " +
            "WHERE confirmationToken.token = ?2")
    void setConfirmAt(LocalDateTime now, String confirmationToken);
}

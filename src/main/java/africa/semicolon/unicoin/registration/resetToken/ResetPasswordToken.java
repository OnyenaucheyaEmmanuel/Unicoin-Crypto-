package africa.semicolon.unicoin.registration.resetToken;

import africa.semicolon.unicoin.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@RequiredArgsConstructor
public class ResetPasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private LocalDateTime confirmedAt;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public ResetPasswordToken(String token, LocalDateTime createdAt, LocalDateTime expiredAt, User user){
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.user = user;
    }
}

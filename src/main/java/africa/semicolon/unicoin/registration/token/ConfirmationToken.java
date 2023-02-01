package africa.semicolon.unicoin.registration.token;

import africa.semicolon.unicoin.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private LocalDateTime createAt;
    @NotNull
    private LocalDateTime expireAt;
    private LocalDateTime confirmedAt;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public  ConfirmationToken(String token, LocalDateTime createAt, LocalDateTime expireAt, User user){
        this.token = token;
        this.createAt = createAt;
        this.expireAt = expireAt;
        this.user = user;

    }

}

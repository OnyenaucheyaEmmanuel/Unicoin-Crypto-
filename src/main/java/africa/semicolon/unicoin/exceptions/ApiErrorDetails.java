package africa.semicolon.unicoin.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorDetails {

    private String message;
    private String details;
    private Instant timeStamp;

    public ApiErrorDetails(String message, String details){
            this.message = message;
            this.details = details;
            this.timeStamp = Instant.now();
    }
}

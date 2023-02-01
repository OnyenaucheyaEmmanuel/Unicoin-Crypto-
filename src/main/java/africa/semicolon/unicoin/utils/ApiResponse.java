package africa.semicolon.unicoin.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@Builder
public class ApiResponse {

    private ZonedDateTime timeStamp;
    private int statusCode;
    private String path;
    private Object data;
    private Boolean isSuccessful;


}

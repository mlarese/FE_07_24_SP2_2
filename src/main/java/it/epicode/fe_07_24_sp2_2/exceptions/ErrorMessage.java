package it.epicode.fe_07_24_sp2_2.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private String message;
    private HttpStatus statusCode;
    private String user;
    private String roles;
    private String url;
    private String method;
}
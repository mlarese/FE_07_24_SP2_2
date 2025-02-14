package it.epicode.fe_07_24_sp2_2.security;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AppUserRequest {

    @NotEmpty(message = "{required.field}")
    private String name;

    @NotEmpty(message = "{required.field}")
    private String email;

    @NotEmpty(message = "{required.field}")
    private String password;

    @NotEmpty(message = "{required.field}")
    private String role;

}

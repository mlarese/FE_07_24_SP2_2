package it.epicode.fe_07_24_sp2_2.security;

import lombok.Data;

@Data
public class AppUserResponse {

    private Integer id;

    private String name;

    private String email;

    private String Role;

}

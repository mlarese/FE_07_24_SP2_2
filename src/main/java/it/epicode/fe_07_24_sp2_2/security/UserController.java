package it.epicode.fe_07_24_sp2_2.security;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/auth/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @GetMapping
    public ResponseEntity<List<AppUser>> getUsers() {
        List<AppUser> users = userService.find();
        return ResponseEntity.ok(users);
    }

    @PostMapping// needs to enable 'EnableGlobalMethodSecurity' at security class to work
    public ResponseEntity<AppUserResponse> createUser(@RequestBody @Valid AppUserRequest request) {

        AppUserResponse resp = userService.save(request);
        return ResponseEntity.created(URI.create(resp.getId().toString())).body(resp);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest request) {
        var token = authenticationService.authenticate(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

}

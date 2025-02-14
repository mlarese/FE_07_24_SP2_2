package it.epicode.fe_07_24_sp2_2.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public AppUserResponse save(AppUserRequest request) {
        if (userRepo.existsByEmail(request.getEmail()))
            throw new ResourceAlreadyExistsException("User", request.getEmail());

        AppUser user = new AppUser();
        user.setEmail(request.getEmail());
        user.setRole( Role.valueOf( request.getRole()));
        user.setName(request.getName());

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepo.save(user);

        AppUserResponse response = new AppUserResponse();
        BeanUtils.copyProperties(user, response);

        return response;

    }

    public List<AppUser> find() {
        return userRepo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
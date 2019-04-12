package dev.phasterinc.tripdat.service;

import dev.phasterinc.tripdat.model.TripdatUser;
import dev.phasterinc.tripdat.model.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService {
    TripdatUser findByEmail(String email);
    TripdatUser save(UserRegistrationDto registrationDto);
    TripdatUser findByLogin(String login);
}

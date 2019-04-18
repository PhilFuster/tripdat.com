package dev.phasterinc.tripdat.service;

import dev.phasterinc.tripdat.model.Role;
import dev.phasterinc.tripdat.model.TripdatUser;
import dev.phasterinc.tripdat.model.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    // == fields ==

    private TripdatUserService tripdatUserService;


    private RoleService roleService;


    private BCryptPasswordEncoder passwordEncoder;

    // == constructors ==
    @Autowired
    public CustomUserDetailsServiceImpl(TripdatUserService tripdatUserService, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.tripdatUserService = tripdatUserService;
        this.roleService = roleService;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        TripdatUser user = tripdatUserService.findByLogin(login);

        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }


        return new org.springframework.security.core.userdetails.User(user.getUserLogin(),
                user.getUserPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public TripdatUser findByEmail(String email) {
        return tripdatUserService.findByEmail(email);

    }

    @Override
    public TripdatUser findByLogin(String login) {
        return tripdatUserService.findByLogin(login);
    }

    @Override
    public TripdatUser save(UserRegistrationDto registrationDto) {
        TripdatUser user = new TripdatUser();
        user.setUserFirstName(registrationDto.getFirstName());
        user.setUserLastName(registrationDto.getLastName());
        user.setUserEmail(registrationDto.getEmail());
        user.setUserPassword(passwordEncoder.encode( registrationDto.getPassword()));
        user.setUserLogin(registrationDto.getLogin());
        Role role = roleService.findByName("ROLE_USER");
        System.out.println(role.toString());
        user.addRole(role);
        tripdatUserService.create(user);
        return user;

    }
}

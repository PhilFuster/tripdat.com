package dev.phasterinc.tripdat.service;

import dev.phasterinc.tripdat.model.Role;
import dev.phasterinc.tripdat.model.TripdatUser;
import dev.phasterinc.tripdat.model.TripdatUserPrincipal;
import dev.phasterinc.tripdat.model.dto.ChangeEmailDto;
import dev.phasterinc.tripdat.model.dto.ChangePasswordDto;
import dev.phasterinc.tripdat.model.dto.UserRegistrationDto;
import dev.phasterinc.tripdat.model.dto.UserSettingsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: CustomUserDetailServiceImpl
 * Purpose: A CustomUserDetailsService used to get a user's information when needed
 *          in the application
 */

@Slf4j
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
    /**
     * Name: loadUserByUserName
     * Purpose: To find a user by their login and return a TripdatUserPrincipal object
     * @param login - String, login of the user.
     * @return TripdatUserPrincipal Object.
     * @throws UsernameNotFoundException - an exception when the user login is not found in the db
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        TripdatUser user = tripdatUserService.findByLogin(login);
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new TripdatUserPrincipal(user);
    }

    /**
     * Name: loadUserByUserId
     * Purpose: To find a user by id and return a TripdatUserPrincipal object
     * @param id - Long, id of the user.
     * @return TripdatUserPrincipal Object.
     * @throws UsernameNotFoundException - an exception when the user id is not found in the db
     */
    public UserDetails loadUserByUserId(final Long id) throws UsernameNotFoundException {
        TripdatUser user = tripdatUserService.findOne(id);

        if(user == null) {
            throw new UsernameNotFoundException("Invalid userId");
        }
        return new TripdatUserPrincipal(user);
    }

    /**
     * Name: mapRolesToAuthorities
     * Purpose: Maps the Roles to the SimpleGrantedAuthority. Granted Authority
     *          represents an authority granted to an authentication object.
     * @param roles - A collection of roles the user is granted
     * @return a Collection of objects that extend GrantedAuthority
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    /**
     * Name: findByEmail
     * Purpose: Queries DB for the email entered in the form & returns the TripdatUser.
     *          Used for checking if an email is still available.
     * @param email - email the user entered in the form
     * @return TripdatUser found by querying the db for the parameter email.
     */
    @Override
    public TripdatUser findByEmail(String email) {
        return tripdatUserService.findByEmail(email);

    }

    /**
     * Name: findByLogin
     * Purpose: Queries DB for the login entered in the form. & returns the User.
     *          Used when checking to see if a login has been taken.
     * @param login - login entered in the form
     * @return TripdatUser, the user the found by this login.
     */
    @Override
    public TripdatUser findByLogin(String login) {
        return tripdatUserService.findByLogin(login);
    }

    /**
     * Name: createUser
     * Purpose: registers a new user account
     * @param registrationDto - contains initial required user information.
     * @return returns the newly created user.
     */
    @Override
    public TripdatUser createUser(UserRegistrationDto registrationDto) {
        TripdatUser user = new TripdatUser();
        user.setUserFirstName(registrationDto.getFirstName());
        user.setUserLastName(registrationDto.getLastName());
        user.setUserEmail(registrationDto.getEmail());
        user.setUserPassword(passwordEncoder.encode( registrationDto.getPassword()));
        user.setUserLogin(registrationDto.getLogin());
        Role role = roleService.findByName("ROLE_USER");
        user.addRole(role);
        tripdatUserService.create(user);
        return user;
    }

    /**
     * Name: updateUserInformation
     * Purpose: updates a user's information from the settings form.
     * @param settingsDto - settings from the user entered from the form.
     * @return
     */
    @Override
    public TripdatUser updateUserInformation(UserSettingsDto settingsDto) {
        TripdatUserPrincipal principal = (TripdatUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TripdatUser user = principal.getTripdatUser();
        user.setUserFirstName(settingsDto.getFirstName());
        user.setUserLastName(settingsDto.getLastName());
        user.setUserLogin(settingsDto.getLogin());
        user.setUserCity(settingsDto.getCity());
        user.setUserDisplayName(settingsDto.getDisplayName());
        user.setUserHomeAirport(settingsDto.getHomeAirport());
        log.info("User to be updated: ", user);
        tripdatUserService.update(user);
        return user;
    }

    /**
     * Name: validatePassword
     * Purpose: To validate the user's entered password with the encoded password stored
     *          in the Database.
     * Synopsis: Get the User Entity from the db, and BCryptPasswordEncoder matches
     *           method to validate what the user entered with what is stored in the DB
     *
     * @param password - password user entered in the form
     * @return Boolean, True if the password matches false otherwise.
     */
    @Override
    public Boolean validatePassword(String password) {
        TripdatUserPrincipal userPrincipal = (TripdatUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TripdatUser userEntity = userPrincipal.getTripdatUser();
        Boolean pwMatches = passwordEncoder.matches(password,userEntity.getUserPassword());
        return (pwMatches);
    }

    /**
     * Name: changeUserPassword
     * Purpose: To change a user's password
     *
     * Synopsis: Changes the user's password based on the passed passwordDto
     * @param passwordDto, contains the new password to set the user's password to
     */
    @Override
    public void changeUserPassword(ChangePasswordDto passwordDto) {
        TripdatUserPrincipal userPrincipal = (TripdatUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TripdatUser userEntity = userPrincipal.getTripdatUser();
        userEntity.setUserPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        tripdatUserService.update(userEntity);
    }

    /**
     * Name: changeUserEmail
     * Purpose: To change a user's email.
     * Synopsis: Retrieves the currently logged in user's information, and changes the email
     */
    @Override
    public void changeUserEmail(ChangeEmailDto emailDto) {
        TripdatUserPrincipal userPrincipal = (TripdatUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TripdatUser userEntity = userPrincipal.getTripdatUser();
        userEntity.setUserEmail(emailDto.getNewEmail());
        tripdatUserService.update(userEntity);
    }
}

package dev.phasterinc.tripdat.model;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * Name: TripdatUserPrincipal
 * Purpose: Grants access to the logged in User's details and handles Roll security
 */

public class TripdatUserPrincipal implements UserDetails {
    // == fields ==
    private TripdatUser user;

    /**
     * Name: TripdatUserPrincipal
     * Purpose: Constructor
     *
     * @param user - TripdatUser object referring to the user that is logged in
     */
    public TripdatUserPrincipal(TripdatUser user) {
        this.user = user;
    }

    /**
     * Name: getAuthorities
     * Purpose: Retrieve the granted Authorities used to make access decisions for
     * different end points.
     *
     * @return - List of GrantedAuthority objects that hold a string representation of the
     * authority to be used when making access decisions.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        return authorities;
    }

    /**
     * Name: getPassword
     * Purpose: Get the user's password.
     *
     * @return String: the user's encrypted password.
     */
    @Override
    public String getPassword() {
        return user.getUserPassword();
    }

    /**
     * Name: getUsername
     * Purpose: Get the user's login
     *
     * @return String, user's login
     */
    @Override
    public String getUsername() {
        return user.getUserLogin();
    }

    /**
     * Name: getUserId
     * Purpose: To get the user's id
     *
     * @return Long, user's id
     */
    public Long getUserId() {
        return user.getUserId();
    }

    /**
     * Name: isAccountNonExpired
     * Purpose: Indicates if the user's account is not expired
     *
     * @return boolean, true, no feature for account expiration currently.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Name: isAccountNonLocked
     * Purpose: Indicates if an account is not locked. A locked account prevents authentication
     *
     * @return: boolean, true, no feature for account locking currently.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Name: isCredentialsNonExpired
     * Purpose: Indicates whether a user's credentials(password) has expired. Expired credential
     * prevent authentication
     *
     * @return boolean, true, no feature for credential expiration implemented.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Name: isEnabled
     * Purpose: Indicates whether an account is enable or disabled. A disabled account cannot
     * be authenticated.
     *
     * @return boolean, true, no feature for disabling accounts currently.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Name: getTripdatUser
     * Purpose: Gets the currently loggin user's information and returns it.
     *
     * @return TripdatUser, a tripdatUser object containing the logged in user's information.
     */
    public TripdatUser getTripdatUser() {
        return user;
    }
}

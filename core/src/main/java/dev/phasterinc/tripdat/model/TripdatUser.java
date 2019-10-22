package dev.phasterinc.tripdat.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/


/**
 * Name: TripdatUser - model the database table tripdat_user
 * Purpose:
 */

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name="TripdatUser")
@Table(name="tripdat_user")
public class TripdatUser implements Serializable {
    // == fields ==
    @Id
    @SequenceGenerator(name = "user_generator", sequenceName = "tripdat_user_user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "user_generator")
    @Column(name = "user_id", columnDefinition = "BIGINT")
    private Long userId;

    @Column(name = "user_first_name", columnDefinition = "TEXT")
    @NotEmpty(message="#{firstName.notEmpty}")
    private String userFirstName;

    @Column(name = "user_last_name", columnDefinition = "TEXT")
    @NotEmpty(message="#{firstName.notEmpty}")
    private String userLastName;

    @Column(name = "user_city", columnDefinition = "TEXT")
    private String userCity;

    @Column(name = "user_email", columnDefinition = "TEXT", unique = true, nullable = false)
    @Email(message = "Create user email invalid message")
    @NotEmpty(message="#{firstName.notEmpty}")
    private String userEmail;

    @Column(name = "user_home_airport", columnDefinition = "TEXT")
    private String userHomeAirport;

    @Column(name = "user_login", columnDefinition = "TEXT", unique = true)
    private String userLogin;

    @Column(name = "user_password", columnDefinition = "TEXT")
    private String userPassword;

    @Column(name = "user_display_name", columnDefinition = "TEXT")
    private String userDisplayName;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id"))
    private Set<Role> roles = new HashSet<>();



    // == constructors ==

    public TripdatUser(String userFirstName, String userLastName, String userEmail, String userPassword) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public TripdatUser(String userFirstName, String userLastName, String userEmail, String userPassword, Set<Role> roles ) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.roles = roles;
    }

    // == public methods ==
    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }



    public void removeRole(Role role) {
        roles.remove(role);
        role.getUsers().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TripdatUser)) return false;
        return userId != null && userId.equals(((TripdatUser) o).getUserId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "[UserId: " + userId + ", userFirstName: " + userFirstName + ", userLastName: " + userLastName
                + ", userCity: " + userCity + ", userEmail: " + userEmail + ", userHomeAirport: " + userHomeAirport
                + ", userLogin: " + userLogin + ", userPassword: " + userPassword + ", userDisplayName: " + userDisplayName
                + ", roles: " + roles + "]";
    }



}

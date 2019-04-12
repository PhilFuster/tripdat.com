package dev.phasterinc.tripdat.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Collection;


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

@Data
@EqualsAndHashCode(of = "userId")
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "role_id"))
    private Collection<Role> roles;

    // == constructors ==

    public TripdatUser(){}

    public TripdatUser(String userFirstName, String userLastName, String userEmail, String userPassword) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public TripdatUser(String userFirstName, String userLastName, String userEmail, String userPassword, Collection<Role> roles ) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.roles = roles;
    }



}

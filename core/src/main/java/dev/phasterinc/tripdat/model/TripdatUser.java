package dev.phasterinc.tripdat.model;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


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
@Entity
@Table(name="tripdat_user")
public class TripdatUser {

    @Id
    @Column(name = "user_id", columnDefinition = "BIGSERIAL")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "user_first_name", columnDefinition = "TEXT")
    @NotEmpty(message="#{firstName.notEmpty}")
    private String userFirstName;

    @Column(name = "user_last_name", columnDefinition = "TEXT")
    @NotEmpty(message="#{firstName.notEmpty}")
    private String userLastName;

    @Column(name = "user_city", columnDefinition = "TEXT")
    private String userCity;

    @Column(name = "user_email", columnDefinition = "TEXT", unique = true)
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


}

package dev.phasterinc.tripdat.util;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: ViewNames
 * Purpose: Maintain constants for all the View Names for TripdatTrip
 */
public final class ViewNames {
    // == constants ==
    public static final String HOME = "home";
    public static final String REGISTRATION = "/registration";
    public static final String REGISTRATION_SUCCESS = "/registration?success";
    public static final String SIGN_IN = "sign-in";
    public static final String INDEX = "/index";
    public static final String USER_INDEX = "/user/index";
    public static final String LOGIN = "/login";
    public static final String SETTINGS = "/user/settings";
    public static final String SETTINGS_SUCCESS = "/user/settings?success";
    public static final String CHANGE_PW = "/user/change-pw";
    public static final String CHANGE_PW_SUCCESS = "/user/change-pw?success";
    public static final String CHANGE_EMAIL = "/user/change-email";
    public static final String CHANGE_EMAIL_SUCCESS = "/user/change-email?success";
    public static final String ACCESS_DENIED = "/error/access-denied";
    public static final String USER_TRIPS = "/user/trip/show/trips";
    public static final String TRIP_DETAILS = "/user/trip/show/trip-details";
    public static final String EDIT_TRIP = "/user/trip/edit/edit-trip";
    public static final String CREATE_TRIP = "/user/trip/create/create-trip";
    public static final String EDIT_FLIGHT = "/user/trip-item/edit/edit-flight";
    public static final String CREATE_FLIGHT = "/user/trip-item/create/create-flight";
    public static final String EDIT_CAR_RENTAL = "/user/trip-item/edit/edit-car-rental";
    public static final String CREATE_CAR_RENTAL = "/user/trip-item/create/create-car-rental";

    // == constructors ==
    private ViewNames() {
    }
}

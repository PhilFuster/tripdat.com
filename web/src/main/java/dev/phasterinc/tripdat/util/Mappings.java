package dev.phasterinc.tripdat.util;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: Mappings
 * Purpose: Maintain Mappings for the application in one location
 */
public final class Mappings {

    // == constants ==
    public static final String HOME = "/";
    public static final String REGISTRATION = "/registration";
    public static final String USER_INDEX = "/user/index";
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";
    public static final String SETTINGS = "/user/settings";
    public static final String CHANGE_PW = "/user/change-pw";
    public static final String CHANGE_EMAIL = "/user/change-email";
    public static final String ACCESS_DENIED = "/access-denied";
    public static final String HTTP_404 = "/404";
    public static final String USER_TRIPS = "/user/trip/show/trips";
    public static final String TRIP_DETAILS = "/user/trip/show/trip-details";
    public static final String EDIT_TRIP = "/user/trip/edit/edit-trip";
    public static final String CREATE_TRIP = "/user/trip/create/create-trip";
    public static final String DELETE_TRIP = "/user/trip/delete";
    public static final String EDIT_FLIGHT = "/user/trip-item/flight/edit";
    public static final String CREATE_FLIGHT = "/user/trip-item/flight/create";
    public static final String DELETE_FLIGHT = "/user/trip-item/flight/delete";
    public static final String EDIT_CAR_RENTAL = "/user/trip-item/car/edit";
    public static final String CREATE_CAR_RENTAL = "/user/trip-item/car/create";
    public static final String DELETE_CAR_RENTAL = "/user/trip-item/car/delete";

    // == private constructors ==

    /**
     * Name: Mappings
     * Purpose: Constructor
     * Synopsis: private constructor so only one instance of Mappings is created.
     * <p>
     */
    private Mappings() {
    }
}

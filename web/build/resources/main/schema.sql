-- CREATE TABLE TEMPLATE --
/*
CREATE TABLE IF NOT EXISTS (
	PRIMARY KEY(),
	CONSTRAINT
	FOREIGN KEY() REFERENCES ()
	MATCH
	ON DELETE
	ON UPDATE
);
*/
CREATE TABLE IF NOT EXISTS tripdat_user (
                                            user_id BIGSERIAL,
                                            user_first_name TEXT,
                                            user_last_name TEXT,
                                            user_city TEXT,
                                            user_email TEXT NOT NULL,
                                            user_home_airport TEXT,
                                            user_login TEXT NOT NULL,
                                            user_password TEXT NOT NULL,
                                            user_display_name TEXT,
                                            PRIMARY KEY(user_id),
                                            CONSTRAINT unique_userEmail
                                                UNIQUE(user_email)
);

CREATE TABLE IF NOT EXISTS role (
                                    role_id BIGSERIAL,
                                    role_name TEXT,
                                    PRIMARY KEY(role_id)
);
INSERT INTO role(role_id, role_name)
    VALUES(1, 'ROLE_USER') ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS users_roles (
                                           role_id BIGINT REFERENCES role(role_id) ON UPDATE CASCADE,
                                           user_id BIGINT REFERENCES tripdat_user(user_id) ON UPDATE CASCADE ON DELETE CASCADE,
                                           CONSTRAINT users_roles_pkey PRIMARY KEY (role_id, user_id)
);

CREATE TABLE IF NOT EXISTS tripdat_trip (
                                            trip_id BIGSERIAL,
                                            user_id BIGINT,
                                            trip_name TEXT,
                                            trip_start_date DATE,
                                            trip_end_date DATE,
                                            trip_description TEXT,
                                            trip_image_link TEXT,
                                            trip_destination_city TEXT,
                                            trip_is_user_traveler BOOLEAN,
                                            PRIMARY KEY(trip_id),
                                            CONSTRAINT fk_trip_user
                                                FOREIGN KEY(user_id) REFERENCES tripdat_user(user_id)
                                                    MATCH FULL
                                                    ON DELETE CASCADE
                                                    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS tripdat_trip_item (
                                                 trip_item_id BIGSERIAL,
                                                 trip_id BIGINT,
                                                 user_id BIGINT,
    /*trip_item_start_date DATE, -- Added startDate and endDate to try and bridge gap between segment and tripItem (none of this in production rn)
    trip_item_end_date DATE,   -- experimental tripItem is the CruiseInformation and cruiseStop
    Moved back the start date and times to subclass level. Implementing a wrapper class server side post db query.
    */
                                                 trip_item_note TEXT,
                                                 trip_item_photo_link TEXT,
                                                 PRIMARY KEY(trip_item_id),
                                                 CONSTRAINT fk_tripItem_trip
                                                     FOREIGN KEY(trip_id) REFERENCES tripdat_trip(trip_id)
                                                         MATCH SIMPLE
                                                         ON DELETE SET NULL
                                                         ON UPDATE CASCADE,
                                                 CONSTRAINT fk_tripItem_user
                                                     FOREIGN KEY(user_id) REFERENCES tripdat_user(user_id)
                                                         MATCH FULL
                                                         ON DELETE CASCADE
                                                         ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS travel_agency (
                                             trip_item_id BIGINT,
                                             travel_agency_name TEXT,
                                             travel_agency_url TEXT,
                                             travel_agency_confirmation_number TEXT,
                                             travel_agency_contact_name TEXT,
                                             travel_agency_phone_number TEXT,
                                             travel_agency_email TEXT,
                                             PRIMARY KEY(trip_item_id),
                                             CONSTRAINT fk_travelAgency_tripItem
                                                 FOREIGN KEY(trip_item_id) REFERENCES tripdat_trip_item(trip_item_id)
                                                     MATCH FULL
                                                     ON DELETE CASCADE
                                                     ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS supplier (
                                        trip_item_id BIGINT,
                                        supplier_contact_name TEXT,
                                        supplier_phone_number TEXT,
                                        supplier_email TEXT,
                                        supplier_url TEXT,
                                        PRIMARY KEY(trip_item_id),
                                        CONSTRAINT fk_supplier_tripItem
                                            FOREIGN KEY(trip_item_id) REFERENCES tripdat_trip_item(trip_item_id)
                                                MATCH FULL
                                                ON DELETE CASCADE
                                                ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS booking_detail(
                                             trip_item_id BIGINT,
                                             booking_site_name TEXT,
                                             booking_site_url TEXT,
                                             booking_date DATE,
                                             booking_reference_number TEXT,
                                             booking_site_phone_number TEXT,
                                             booking_site_email TEXT,
                                             booking_total_cost TEXT,
                                             booking_rate TEXT,
                                             booking_is_purchased BOOLEAN,
                                             PRIMARY KEY(trip_item_id),
                                             CONSTRAINT fk_bookingDetail_tripItem
                                                 FOREIGN KEY(trip_item_id) REFERENCES tripdat_trip_item(trip_item_id)
                                                     MATCH FULL
                                                     ON DELETE CASCADE
                                                     ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS attendee(
                                       attendee_id BIGSERIAL,
                                       trip_item_id BIGINT,
                                       attendee_name TEXT,
                                       attendee_loyalty_program_number TEXT,
                                       attendee_ticket_number TEXT,
                                       PRIMARY KEY(attendee_id),
                                       CONSTRAINT fk_attendee_tripItem
                                           FOREIGN KEY(trip_item_id) REFERENCES tripdat_trip_item(trip_item_id)
                                               MATCH FULL
                                               ON DELETE CASCADE
                                               ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS car_rental(
                                         trip_item_id BIGINT,
                                         car_rental_confirmation_number TEXT,
                                         car_rental_pick_up_date DATE,
                                         car_rental_pick_up_time TIME,
                                         car_rental_drop_off_date DATE,
                                         car_rental_drop_off_time TIME,
                                         car_rental_pick_up_location_name TEXT,
                                         car_rental_pick_up_address TEXT,
                                         car_rental_pick_up_hours_of_operation TEXT,
                                         car_rental_phone_number TEXT,
                                         car_rental_is_drop_off_location_same_as_pick_up BOOLEAN,
                                         car_rental_drop_off_location_name TEXT,
                                         car_rental_drop_off_address TEXT,
                                         car_rental_drop_off_hours_of_operation TEXT,
                                         car_rental_drop_off_phone_number TEXT,
                                         PRIMARY KEY(trip_item_id),
                                         CONSTRAINT fk_carRental_tripItem
                                             FOREIGN KEY(trip_item_id) REFERENCES tripdat_trip_item(trip_item_id)
                                                 MATCH FULL
                                                 ON DELETE CASCADE
                                                 ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS cruise_information(
                                                 trip_item_id BIGINT,
                                                 cruise_line_name TEXT,
                                                 cruise_ship_name TEXT,
                                                 cruise_confirmation_number TEXT,
                                                 cruise_start_port_name TEXT,
                                                 cruise_start_port_address TEXT,
                                                 cruise_start_date DATE,
                                                 cruise_start_date DATE,
    /*(4/21/19 - made adjustment to have start & end Date & Time of a tripItem in the base class for ordering purposes
    when querying for base TripItems and retrieving all child classes polymorphicaly.
    For cruiseStops and such going to have to think of a way to include them in the ordering)
    (Have only implemented till this point as of 4.22)
    4.23.2019 Added start date and time back to subclass level. Will be implementing a Wrapper General Trip Item class
    to enable sorting.*/
                                                 cruise_start_time TIME,
                                                 cruise_start_time_zone TEXT,
                                                 cruise_start_time TIME,
                                                 cruise_start_time_zone TEXT,
                                                 cruise_is_start_location_same_as_end BOOLEAN,
                                                 cruise_end_port_name TEXT,
                                                 cruise_end_port_address TEXT,
                                                 cruise_end_date DATE,
                                                 cruise_end_time TIME,
                                                 cruise_end_time_zone TEXT,
                                                 cruise_cabin_number TEXT,
                                                 cruise_dining_information TEXT,
                                                 PRIMARY KEY(trip_item_id),
                                                 CONSTRAINT fk_cruiseInformation_tripItem
                                                     FOREIGN KEY(trip_item_id) REFERENCES tripdat_trip_item(trip_item_id)
                                                         MATCH FULL
                                                         ON DELETE CASCADE
                                                         ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS cruise_stop(
                                          cruise_stop_id BIGSERIAL,
                                          trip_item_id BIGINT,
                                          cruise_stop_port_name TEXT,
                                          cruise_stop_port_address TEXT,
                                          cruise_stop_arrival_date DATE,
                                          cruise_stop_arrival_time TIME,
                                          cruise_stop_arrival_time_zone TEXT,
                                          cruise_stop_departure_date DATE,
                                          cruise_stop_departure_time TIME,
                                          PRIMARY KEY(cruise_stop_id),
                                          CONSTRAINT fk_cruiseStop_tripItem
                                              FOREIGN KEY(trip_item_id) REFERENCES tripdat_trip_item(trip_item_id)
                                                  MATCH FULL
                                                  ON DELETE CASCADE
                                                  ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS parking_information(
                                                  trip_item_id BIGINT,
                                                  parking_supplier_name TEXT,
                                                  parking_confirmation_number TEXT,
                                                  parking_valet_ticket_number TEXT,
                                                  parking_drop_off_location TEXT,
                                                  parking_hours_of_operation TEXT,
                                                  parking_phone_number TEXT,
                                                  parking_pick_up_date DATE,
                                                  parking_pick_up_time TIME,
                                                  PRIMARY KEY(trip_item_id),
                                                  CONSTRAINT fk_parkingInformation_tripItem
                                                      FOREIGN KEY(trip_item_id) REFERENCES tripdat_trip_item(trip_item_id)
                                                          MATCH FULL
                                                          ON DELETE CASCADE
                                                          ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS flight_information(
                                                 trip_item_id BIGINT,
                                                 flight_confirmation_number TEXT,
                                                 PRIMARY KEY(trip_item_id),
                                                 CONSTRAINT fk_flightInformation_tripItem
                                                     FOREIGN KEY(trip_item_id) REFERENCES tripdat_trip_item(trip_item_id)
                                                         MATCH FULL
                                                         ON DELETE CASCADE
                                                         ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS flight_segment(
                                             flight_segment_id BIGSERIAL,
                                             trip_item_id BIGINT,
                                             airline_name TEXT,
                                             flight_number TEXT,
                                             flight_ticket_number TEXT,
                                             flight_departure_airport TEXT,
                                             flight_departure_date DATE,
                                             flight_departure_time TIME,
                                             flight_departure_terminal TEXT,
                                             flight_departure_gate TEXT,
                                             flight_departure_time_zone TEXT,
                                             flight_arrival_airport TEXT,
                                             flight_arrival_date DATE,
                                             flight_arrival_time TIME,
                                             flight_arrival_time_zone TEXT,
                                             flight_arrival_terminal TEXT,
                                             flight_arrivale_gate TEXT,
                                             flight_fare_class TEXT,
                                             flight_meal TEXT,
                                             flight_baggage_claim TEXT,
                                             flight_entertainment TEXT,
                                             flight_on_time_percentage TEXT,
                                             flight_aircraft_type TEXT,
                                             operating_flight_number TEXT,
                                             flight_operated_by TEXT,
                                             flight_stops TEXT,
                                             flight_duration TEXT,
                                             flight_distance TEXT,
                                             flight_segment_notes TEXT,
                                             PRIMARY KEY(flight_segment_id),
                                             CONSTRAINT fk_flightSegment_tripItem
                                                 FOREIGN KEY(trip_item_id) REFERENCES tripdat_trip_item(trip_item_id)
                                                     MATCH FULL
                                                     ON DELETE CASCADE
                                                     ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS rail_information(
                                               trip_item_id BIGINT,
                                               PRIMARY KEY(trip_item_id),
                                               CONSTRAINT fk_railInformation_tripItem
                                                   FOREIGN KEY(trip_item_id) REFERENCES tripdat_trip_item(trip_item_id)
                                                       MATCH FULL
                                                       ON DELETE CASCADE
                                                       ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS rail_segment(
                                           rail_segment_id BIGSERIAL,
                                           trip_item_id BIGINT,
                                           rail_carrier_name TEXT,
                                           rail_confirmation_number TEXT,
                                           rail_departure_location_name TEXT,
                                           rail_departure_address TEXT,
                                           rail_departure_time TIME,
                                           rail_departure_time_zone TEXT,
                                           rail_arrival_location_name TEXT,
                                           rail_arrival_address TEXT,
                                           rail_arrival_date DATE,
                                           rail_arrival_time TIME,
                                           rail_arrival_time_zone TEXT,
                                           rail_train_number TEXT,
                                           rail_train_type TEXT,
                                           rail_coach_number TEXT,
                                           rail_class TEXT,
                                           rail_seat_assignment TEXT,
                                           PRIMARY KEY(rail_segment_id),
                                           CONSTRAINT fk_railSegment_tripItem
                                               FOREIGN KEY(trip_item_id) REFERENCES tripdat_trip_item(trip_item_id)
                                                   MATCH FULL
                                                   ON DELETE CASCADE
                                                   ON UPDATE CASCADE
);
DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_type WHERE pg_type.typname = 'transportation_type') THEN
            CREATE TYPE transportation_type AS ENUM ('Transportation', 'Ground Transportation', 'Ferry');
        END IF;
    END
$$;
CREATE TABLE IF NOT EXISTS general_transportation(
                                                     trip_item_id BIGINT,
                                                     transportation_type transportation_type,
                                                     PRIMARY KEY(trip_item_id),
                                                     CONSTRAINT fk_generalTransportation_tripItem
                                                         FOREIGN KEY(trip_item_id) REFERENCES tripdat_trip_item(trip_item_id)
                                                             MATCH FULL
                                                             ON DELETE CASCADE
                                                             ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS segment_information(
                                                  segment_information_id BIGSERIAL,
                                                  trip_item_id BIGINT,
                                                  segment_carrier_name TEXT,
                                                  segment_confirmation_number TEXT,
                                                  segment_departure_location_name TEXT,
                                                  segment_departure_address TEXT,
                                                  segment_arrival_date DATE,
                                                  segment_departure_time TIME,
                                                  segment_departure_time_zone TEXT,
                                                  segment_arrival_location_name TEXT,
                                                  segment_arrival_address TEXT,
                                                  segment_number_of_passengers TEXT,
                                                  segment_description TEXT,
                                                  PRIMARY KEY(segment_information_id),
                                                  CONSTRAINT fk_segmentInformation_tripItem
                                                      FOREIGN KEY(trip_item_id) REFERENCES tripdat_trip_item(trip_item_id)
                                                          MATCH FULL
                                                          ON DELETE CASCADE
                                                          ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS restaurant_information(
                                                     trip_item_id BIGINT,
                                                     restaurant_name TEXT,
                                                     restaurant_address TEXT,
                                                     restaurant_description TEXT,
                                                     restaurant_date DATE,
                                                     restaurant_time TIME,
                                                     restaurant_time_zone TEXT,
                                                     restaurant_cuisine TEXT,
                                                     restaurant_number_in_party TEXT,
                                                     restaurant_confirmation_number TEXT,
                                                     restaurant_hours_of_operation TEXT,
                                                     restaurant_dress_code TEXT,
                                                     restaurant_price_range TEXT,
                                                     PRIMARY KEY(trip_item_id),
                                                     CONSTRAINT fk_restaurantInformation_tripItem
                                                         FOREIGN KEY(trip_item_id) REFERENCES tripdat_trip_item(trip_item_id)
                                                             MATCH FULL
                                                             ON DELETE CASCADE
                                                             ON UPDATE CASCADE
);
DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_type WHERE pg_type.typname = 'meeting_type') THEN
            CREATE TYPE meeting_type AS ENUM ('Activity', 'Meeting', 'Tour', 'Concert', 'Theatre');
        END IF;
    END
$$;
CREATE TABLE IF NOT EXISTS meeting_information(
                                                  trip_item_id BIGINT,
                                                  type_of_meeting meeting_type,
                                                  confirmation_number TEXT,
                                                  supplier_name TEXT,
                                                  start_time TIME,
                                                  time_zone TEXT,
                                                  end_date DATE,
                                                  end_time TIME,
                                                  location_name TEXT,
                                                  location_address TEXT,
                                                  PRIMARY KEY(trip_item_id),
                                                  CONSTRAINT fk_meetingInformation_tripItem
                                                      FOREIGN KEY(trip_item_id) REFERENCES tripdat_trip_item(trip_item_id)
                                                          MATCH FULL
                                                          ON DELETE CASCADE
                                                          ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS map_information(
                                              trip_item_id BIGINT,
                                              map_name TEXT,
                                              map_address TEXT,
                                              map_date DATE,
                                              map_time TIME,
                                              map_time_zone TEXT,
                                              PRIMARY KEY(trip_item_id),
                                              CONSTRAINT fk_mapInformation_tripItem
                                                  FOREIGN KEY(trip_item_id) REFERENCES tripdat_trip_item(trip_item_id)
                                                      MATCH FULL
                                                      ON DELETE CASCADE
                                                      ON UPDATE CASCADE
);

DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_type WHERE pg_type.typname = 'directions_type') THEN
            CREATE TYPE directions_type AS ENUM ('Bicycling', 'Driving', 'Transit', 'Walking');
        END IF;
    END
$$;

CREATE TABLE IF NOT EXISTS directions(
                                         trip_item_id BIGINT,
                                         directions_name TEXT,
                                         directions_type directions_type,
                                         PRIMARY KEY(trip_item_id),
                                         CONSTRAINT fk_directions_tripItem
                                             FOREIGN KEY(trip_item_id) REFERENCES tripdat_trip_item(trip_item_id)
                                                 MATCH FULL
                                                 ON DELETE CASCADE
                                                 ON UPDATE CASCADE
);







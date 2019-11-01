package dev.phasterinc.tripdat.factory;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.model.Flight;
import dev.phasterinc.tripdat.model.TripdatTripItem;
import dev.phasterinc.tripdat.model.dto.FlightSegmentDto;

import java.time.LocalDate;


/**
 * Name: FlightFactor
 * Purpose: Factory class for creating Flight's
 */

public class FlightFactory extends TripdatTripItemFactory {
    /**
     * Name: createItem
     * Purpose: To call the super class's createItem function
     * Synopsis: calls the super class's createItem method.
     */
    @Override
    public TripdatTripItem createItem(String itemType) {
        return super.createItem(itemType);
    }

    /**
     * Name: getItemType
     * Purpose: To create a Flight trip item
     * Synopsis: User's the builder pattern and returns a Flight object.
     */
    @Override
    public TripdatTripItem getItemType(String itemType) {

        return Flight.builder().build();
    }


    public static FlightSegmentDto createFlightSegmentDto() {
        return FlightSegmentDto.builder().build();
    }

    public static FlightSegmentDto createFlightSegmentDto(
            LocalDate departureDate) {
        return FlightSegmentDto.builder()
                .departureDate(departureDate)
                .build();
    }


}

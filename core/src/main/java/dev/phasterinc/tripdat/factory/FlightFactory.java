package dev.phasterinc.tripdat.factory;

import dev.phasterinc.tripdat.model.Flight;
import dev.phasterinc.tripdat.model.TripdatTripItem;
import dev.phasterinc.tripdat.model.dto.FlightSegmentDto;

import java.time.LocalDate;

public class FlightFactory  extends TripdatTripItemFactory{
    @Override
    public TripdatTripItem createItem(String itemType) {
        return super.createItem(itemType);
    }

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

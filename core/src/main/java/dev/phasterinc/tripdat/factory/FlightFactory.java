package dev.phasterinc.tripdat.factory;

import dev.phasterinc.tripdat.model.Flight;
import dev.phasterinc.tripdat.model.TripdatTripItem;

public class FlightFactory  extends TripdatTripItemFactory{
    @Override
    public TripdatTripItem createItem(String itemType) {
        return super.createItem(itemType);
    }

    @Override
    public TripdatTripItem getItemType(String itemType) {
        return Flight.builder().build();
    }
}

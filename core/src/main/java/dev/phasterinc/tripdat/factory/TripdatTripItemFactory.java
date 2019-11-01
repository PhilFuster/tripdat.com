package dev.phasterinc.tripdat.factory;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.model.TripdatTripItem;


/**
 * Name: TripdatTripItemFactory
 * Purpose: Abstract class for the Factory Design pattern for classes that inherit from
 * TripdatTripItem
 */
public abstract class TripdatTripItemFactory {

    /**
     * Name: createItem
     * Purpose: To create and return an instance of the abstract class TripItem
     * Synopsis: Create's and returns an instance of the abstract class TripItem
     */
    public TripdatTripItem createItem(String itemType) {
        TripdatTripItem tripItem = this.getItemType(itemType);
        return tripItem;
    }

    /**
     * Name: getItemType
     * Purpose: To get an item's type
     * Synopsis: This is a method all classes that extend TripdatTripItemFactory have to implement.
     */
    public abstract TripdatTripItem getItemType(String itemType);
}

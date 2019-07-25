package dev.phasterinc.tripdat.factory;


import dev.phasterinc.tripdat.model.CarRental;
import dev.phasterinc.tripdat.model.Cruise;
import dev.phasterinc.tripdat.model.Flight;
import dev.phasterinc.tripdat.model.TripdatTripItem;

public abstract class TripdatTripItemFactory {

    public  TripdatTripItem createItem(String itemType) {
        TripdatTripItem tripItem = this.getItemType(itemType);
        return tripItem;
    }
    public abstract TripdatTripItem getItemType(String itemType);
}

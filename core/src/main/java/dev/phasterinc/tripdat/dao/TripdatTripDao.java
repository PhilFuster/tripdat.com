package dev.phasterinc.tripdat.dao;

import dev.phasterinc.tripdat.model.TripdatTrip;

import java.util.List;

public interface TripdatTripDao extends IGenericDao<TripdatTrip> {

    List<TripdatTrip> getTripItemsByTripId();

}

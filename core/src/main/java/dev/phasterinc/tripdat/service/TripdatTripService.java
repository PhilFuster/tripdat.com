package dev.phasterinc.tripdat.service;

import dev.phasterinc.tripdat.model.TripdatTrip;

import java.util.List;

public interface TripdatTripService {
    void save(TripdatTrip tripdatTrip);

    List<TripdatTrip> list();

}

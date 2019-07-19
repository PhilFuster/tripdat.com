package dev.phasterinc.tripdat.service;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.model.TripdatTripItem;

import java.util.HashSet;
import java.util.List;

/**
 * Name: TripdatTripItemService - Interface for TripdatTripItemService
 * Purpose: calls dao methods for tripItem entities in db
 */

public interface TripdatTripItemService {
    List<TripdatTripItem> findAll();
    TripdatTripItem findOne(final long id);
    void create(final TripdatTripItem entity);
    TripdatTripItem update(final TripdatTripItem entity);
    void delete(final TripdatTripItem entity);
    void deleteById(final long entityId);
    HashSet<TripdatTripItem> getItemsByTripId(final long  id);
    HashSet<TripdatTripItem> getMax4ItemsById(final long id);
    TripdatTripItem findByItemId(final long id);
}

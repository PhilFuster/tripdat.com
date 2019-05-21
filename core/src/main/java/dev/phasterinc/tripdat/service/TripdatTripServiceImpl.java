package dev.phasterinc.tripdat.service;

import dev.phasterinc.tripdat.dao.TripdatTripDao;
import dev.phasterinc.tripdat.model.TripdatTrip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: TripdatTripServiceImpl
 * Purpose: Implementation to the TripdatTripService interface
 */
@Service
@Transactional
public class TripdatTripServiceImpl implements TripdatTripService{


    TripdatTripDao dao;

    @Autowired
    public void setDao(TripdatTripDao daoToSet) {
        dao = daoToSet;
        dao.setClazz(TripdatTrip.class);
    }

    @Override
    public List<TripdatTrip> findAll() {
        System.out.println("Looking for all trips...");
        return dao.findAll();
    }

    @Override
    public TripdatTrip findOne(Long id) {
        return dao.findOne(id);
    }

    @Override
    public void create(TripdatTrip entity) {
        dao.create(entity);
    }

    @Override
    public TripdatTrip update(TripdatTrip entity) {
        return dao.update(entity);
    }

    @Override
    public void delete(TripdatTrip entity) {
        dao.delete(entity);

    }

    @Override
    public void deleteById(Long entityId) {
        dao.deleteById(entityId);

    }

    @Override
    public TripdatTrip getTripByTripId(final Long id) {

        return dao.getTripByTripId(id);
    }

    @Override
    public List<TripdatTrip> get3UpcomingTripsByUserIdOrderByDateAsc(final Long userId) {
        return dao.get3UpcomingTripsByUserIdOrderByDateAsc(userId);
    }

    @Override
    public Set<TripdatTrip> getTripsByUserId(Long id) {
        return dao.getTripsByUserId(id);
    }

    @Override
    public void createUpcomingAndPastTripsCollections(Set<TripdatTrip> trips, Set<TripdatTrip> upcoming, Set<TripdatTrip> past) {
        // for each trip if the endDat is before today add to past trips collections, else add to upcoming
        trips.forEach((trip)->{
            if(trip.getTripEndDate().isBefore(LocalDate.now())) {
                past.add(trip);
            } else {
                upcoming.add(trip);
            }

        });
    }
}

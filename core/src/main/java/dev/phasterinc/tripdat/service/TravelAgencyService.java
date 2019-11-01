package dev.phasterinc.tripdat.service;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.model.TravelAgency;

import java.util.List;

/**
 * Name: TravelAgencyService - Interface for TravelAgencyService
 * Purpose: Calls the dao methods from GenericHibernateDao
 */
public interface TravelAgencyService {
    List<TravelAgency> findAll();

    TravelAgency findOne(final long id);

    void create(final TravelAgency entity);

    TravelAgency update(final TravelAgency entity);

    void delete(final TravelAgency entity);

    void deleteById(final long entityId);
}

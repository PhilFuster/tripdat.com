package dev.phasterinc.tripdat.service;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.model.BookingDetail;

import java.util.List;

/**
 * Name: BookingDetailService - Interface for BookingDetailService
 * Purpose: Calls the dao methods from GenericHibernateDao
 */
public interface BookingDetailService {
    List<BookingDetail> findAll();
    BookingDetail findOne(final long id);
    void create(final BookingDetail entity);
    BookingDetail update(final BookingDetail entity);
    void delete(final BookingDetail entity);
    void deleteById(final long entityId);
}

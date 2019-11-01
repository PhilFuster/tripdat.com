package dev.phasterinc.tripdat.service;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.model.Supplier;

import java.util.List;

/**
 * Name: SupplierService - Interface for SupplierService
 * Purpose: Calls the dao methods from GenericHibernateDao
 */
public interface SupplierService {
    List<Supplier> findAll();

    Supplier findOne(final long id);

    void create(final Supplier entity);

    Supplier update(final Supplier entity);

    void delete(final Supplier entity);

    void deleteById(final long entityId);
}

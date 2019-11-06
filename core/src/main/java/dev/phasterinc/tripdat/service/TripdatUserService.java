package dev.phasterinc.tripdat.service;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.model.TripdatUser;

import java.util.List;


/**
 * Name: TripdatUserService - Interface for TripdatUserService
 * Purpose: Calls the dao methods from GenericHibernateDao
 */
public interface TripdatUserService {
    List<TripdatUser> findAll();

    TripdatUser findOne(final long id);

    void create(final TripdatUser entity);

    TripdatUser update(final TripdatUser entity);

    void delete(final TripdatUser entity);

    void deleteById(final long entityId);

    TripdatUser findByEmail(String email);

    TripdatUser findByLogin(String login);

}

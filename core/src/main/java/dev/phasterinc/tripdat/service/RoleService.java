package dev.phasterinc.tripdat.service;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.model.Role;


/**
 * Name: RoleService
 * Purpose: Interface for a retrieving Role entities from the database
 */
public interface RoleService {
    Role findOne(final Long id);

    Role findByName(String name);
}

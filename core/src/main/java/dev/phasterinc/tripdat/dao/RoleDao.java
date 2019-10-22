package dev.phasterinc.tripdat.dao;

import dev.phasterinc.tripdat.model.Role;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: RoleDao - dao interface for role table
 * Purpose: Data Access Object interface for the Role class. Which provides user access
 *          permissions by means of user roles.
 */
public interface RoleDao extends IGenericDao<Role> {
    Role findByName(String name);
}

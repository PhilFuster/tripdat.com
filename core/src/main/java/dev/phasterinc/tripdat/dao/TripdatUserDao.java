package dev.phasterinc.tripdat.dao;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.model.TripdatUser;


/**
 * Name: TripdatUserDao
 * Purpose: Interface for TripdatUserDao implementation. Extends IgenericDao interface
 */
public interface TripdatUserDao extends IGenericDao<TripdatUser> {

    TripdatUser findByEmail(String email);

    TripdatUser findByLogin(String loginId);

}

package dev.phasterinc.tripdat.service;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.dao.RoleDao;
import dev.phasterinc.tripdat.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Name: RoleServiceImpl
 * Purpose: Implementation of the RoleService Interface
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDao dao;

    /**
     * Name: findOne
     * Purpose: To find a Role by ID
     * Synopsis: Uses member variable dao's method findOne
     * <p>
     *
     * @param id Long, id of the Role to find.
     * @return Role, Role found by querying the database for the id passed.
     */
    @Override
    public Role findOne(Long id) {
        return dao.findOne(id);
    }

    /**
     * Name: findByName
     * Purpose: To find a Role by its name
     * Synopsis: Uses member variable dao's method findByName
     * <p>
     *
     * @param name - String, the name of the Role to query the database for.
     * @return Role found by querying the database with the name passed.
     */
    @Override
    public Role findByName(String name) {
        return dao.findByName(name);
    }
}

package dev.phasterinc.tripdat.service;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.dao.TripdatUserDao;
import dev.phasterinc.tripdat.model.TripdatUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Name: TripdatUserServiceImpl
 * Purpose: Implements the TripdatUserService interface. Providing CRUD functionality.
 */
@Service
@Transactional
public class TripdatUserServiceImpl implements TripdatUserService {
    @Autowired
    TripdatUserDao dao;

    /**
     * Name: setDao
     * Purpose: Injects the dependency TripdatUserDao into the Service implementation.
     * Synopsis: Sets the class for the Database Access Object that it will be accessing.
     * <p>
     *
     * @param daoToSet The TripdatUserDao object to be used in this service class.
     */
    @Autowired
    public void setDao(TripdatUserDao daoToSet) {
        dao = daoToSet;
        dao.setClazz(TripdatUser.class);
    }

    /**
     * Name: findByEmail
     * Purpose: To retrieve a user by their Email address.
     * Synopsis: Calls the TripdatUserDao method findByEmail with the email passed in.
     * <p>
     *
     * @param email String, the email the user provided.
     * @return The corresponding TripdatUser in the database, if there is one.
     */
    @Override
    public TripdatUser findByEmail(String email) {

        return dao.findByEmail(email);
    }

    /**
     * Name: findAll
     * Purpose: To retrieve all users from the database.
     * Synopsis: Calls the IGenericDao method findAll
     * <p>
     *
     * @return List of the TripdatUsers in the database.
     */
    @Override
    public List<TripdatUser> findAll() {
        return dao.findAll();
    }

    /**
     * Name: findOne
     * Purpose: To interrogate the database for a user with the id passed in.
     * Synopsis: Calls the IGenericDao method findOne.
     * <p>
     *
     * @param id long, the User id to query the database for.
     * @return The TripdatUser with the passed in id, or null if not found.
     */
    @Override
    public TripdatUser findOne(long id) {
        return dao.findOne(id);
    }

    /**
     * Name: create
     * Purpose: To create a new TripdatUser Entity
     * Synopsis: Calls the IGenericDao create method.
     * <p>
     *
     * @param entity, The TripdatUser entity that will be recorded in the database.
     */
    @Override
    public void create(TripdatUser entity) {
        dao.create(entity);

    }

    /**
     * Name: update
     * Purpose: To update an existing TripdatUser record in the database.
     * Synopsis: Calls the IGenericDao update method.
     * <p>
     *
     * @param entity TripdatUser that will be updated in the database.
     * @return The updated TripdatUser entity.
     */
    @Override
    public TripdatUser update(TripdatUser entity) {
        return dao.update(entity);
    }

    /**
     * Name: delete
     * Purpose: To delete a TripdatUser from the system.
     * Synopsis: Calls the IGenericDao delete method.
     * <p>
     *
     * @param entity TripdatUser entity to be deleted from the database.
     */
    @Override
    public void delete(TripdatUser entity) {
        dao.delete(entity);

    }

    /**
     * Name: deleteById
     * Purpose: To delete a TripdatUser by its id.
     * Synopsis: Calls the IGenericDao deleteById method.
     * <p>
     *
     * @param entityId The id of the TripdatUser to be deleted.
     */
    @Override
    public void deleteById(long entityId) {
        dao.deleteById(entityId);
    }

    /**
     * Name: findByLogin
     * Purpose: To retrieve a TripdatUser by their unique login.
     * Synopsis: Calls the findByLogin method of the
     * <p>
     *
     * @param login String, the login to query the database for.
     * @return The TripdatUser that was found, or null if no there is no record in the
     * database with the String passed in.
     */
    @Override
    public TripdatUser findByLogin(String login) {
        return dao.findByLogin(login);
    }
}

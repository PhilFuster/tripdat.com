package dev.phasterinc.tripdat.service;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.dao.IGenericDao;
import dev.phasterinc.tripdat.model.TravelAgency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Name: TravelAgencyServiceImpl
 * Purpose: Implements the TravelAgencyService interface
 */
@Service
public class TravelAgencyServiceImpl implements TravelAgencyService {

    // == fields ==
    IGenericDao<TravelAgency> dao;

    /**
     * Name: setDao
     * Purpose: To inject the database access object for the TravelAgency Entity for use
     * within this Service class.
     * Synopsis: Implements the SupplierService interface & centralizes access to the
     * database for the Supplier entity.
     * <p>
     *
     * @param daoToSet, IGenerericDao for the TravelAgency class, which provides basic CRUD
     *                  features for the travel_agency table in the database.
     */
    @Autowired
    public void setDao(IGenericDao<TravelAgency> daoToSet) {
        dao = daoToSet;
        dao.setClazz(TravelAgency.class);
    }

    /**
     * Name: findAll
     * Purpose: To retrieve all TravelAgency entities in the database
     * Synopsis: Calls the IGenericDao method findAll.
     * <p>
     *
     * @return List of TravelAgency records in the database.
     */
    @Override
    public List<TravelAgency> findAll() {
        return dao.findAll();
    }

    /**
     * Name: findOne
     * Purpose: To retrieve a TravelAgency entity by its id
     * Synopsis: Calls the IGenericDao method findOne
     * <p>
     *
     * @param id long, representing the id of the TravelAgency record in the database.
     * @return TravelAgency with the id passed in.
     */
    @Override
    public TravelAgency findOne(long id) {
        return dao.findOne(id);
    }

    /**
     * Name: create
     * Purpose: To create a TravelAgency entity.
     * Synopsis: Creates a TravelAgency record in the database with the information in the TravelAgency object passed
     * <p>
     *
     * @param entity TravelAgency, the object that will be created in the database
     */
    @Override
    public void create(TravelAgency entity) {
        dao.create(entity);
    }

    /**
     * Name: update
     * Purpose: To update a TravelAgency record in the database.
     * Synopsis: Calls the update method of the IGenericDao class.
     * <p>
     *
     * @param entity TravelAgency, the record in the database that will be updated.
     * @return TravelAgency, record in the database that was updated.
     */
    @Override
    public TravelAgency update(TravelAgency entity) {
        return dao.update(entity);
    }

    /**
     * Name: delete
     * Purpose: To delete the passed in entity from the database.
     * Synopsis: Deletes the TravelAgency record in the database that corresponds to the parameter passed.
     * <p>
     *
     * @param entity TravelAgency record in the database to be deleted.
     */
    @Override
    public void delete(TravelAgency entity) {
        dao.delete(entity);
    }

    /**
     * Name: deleteById
     * Purpose: To delete the TravelAgency with the id that is passed to the function.
     * Synopsis: Calls the deleteById IGenericDao method.
     * <p>
     *
     * @param entityId long, id of the entity to be deleted.
     */
    @Override
    public void deleteById(long entityId) {
        dao.deleteById(entityId);
    }
}

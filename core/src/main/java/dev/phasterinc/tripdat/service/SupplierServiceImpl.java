package dev.phasterinc.tripdat.service;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.dao.IGenericDao;
import dev.phasterinc.tripdat.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Name: SupplierServiceImpl
 * Purpose: Implements the SupplierService interface, which provides functionality to
 * perform actions on a Supplier entity.
 */
@Service
public class SupplierServiceImpl implements SupplierService {

    // == fields ==
    IGenericDao<Supplier> dao;

    /**
     * Name: setDao
     * Purpose: To inject the database access object for the Supplier Entity for use
     * within this Service class.
     * Synopsis: Implements the SupplierService interface & centralizes access to the
     * database for the Supplier entity.
     * <p>
     *
     * @param daoToSet, IGenerericDao for the Supplier class, which provides basic CRUD
     *                  features for the supplier table in the database.
     */
    @Autowired
    public void setDao(IGenericDao<Supplier> daoToSet) {
        dao = daoToSet;
        dao.setClazz(Supplier.class);
    }

    /**
     * Name: findAll
     * Purpose: To find all Supplier records in the database.
     * Synopsis: Calls the IGenericDao findAll method
     * <p>
     *
     * @return List of Supplier records in the database.
     */
    @Override
    public List<Supplier> findAll() {
        return dao.findAll();
    }

    /**
     * Name: findOne
     * Purpose: To find one Supplier record based on the ID passed.
     * Synopsis: Calls IGenericDao findOne method.
     * <p>
     *
     * @param id long, id of the Supplier to locate.
     */
    @Override
    public Supplier findOne(long id) {
        return dao.findOne(id);
    }

    /**
     * Name: create
     * Purpose: To create a new Supplier record in the database.
     * Synopsis: Calls the IGenericDao method create.
     * <p>
     *
     * @param entity Supplier entity to be created.
     */
    @Override
    public void create(Supplier entity) {
        dao.create(entity);

    }

    /**
     * Name: update
     * Purpose: To update the Supplier entity passed in the database.
     * Synopsis: Calls IGenericDao update method.
     * <p>
     *
     * @param entity Supplier entity to be updated in the database.
     * @return Supplier that was updated.
     */
    @Override
    public Supplier update(Supplier entity) {
        return dao.update(entity);
    }

    /**
     * Name: delete
     * Purpose: To delete the Supplier instance passed from the database
     * Synopsis: Calls IGenericDao delete method.
     * <p>
     *
     * @param entity Supplier entity to be deleted from the database.
     */
    @Override
    public void delete(Supplier entity) {
        dao.delete(entity);
    }

    /**
     * Name: deleteById
     * Purpose: To delete a Supplier record from the database with the id passed.
     * Synopsis: Calls the IGenericDao deleteById method.
     * <p>
     *
     * @param entityId long, id of the Supplier entity to be deleted.
     */
    @Override
    public void deleteById(long entityId) {
        dao.deleteById(entityId);
    }
}

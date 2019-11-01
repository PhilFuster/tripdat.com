package dev.phasterinc.tripdat.service;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.dao.TripdatTripItemDao;
import dev.phasterinc.tripdat.model.TripdatTripItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

/**
 * Name: TripdatTripItemServiceImpl - implementation of TripdatTripItemService
 * Purpose: calls dao methods for the TripdatItem table
 */
@Service
@Transactional
public class TripdatTripItemServiceImpl implements TripdatTripItemService {

    // == fields ==
    TripdatTripItemDao dao;

    /**
     * Name: TripdatTripItemServiceImpl constructor
     * Purpose: To inject the dependencies of this class.
     * Synopsis: Injects the Database Access Object pertaining to the TripdatTripItem class.
     * <p>
     */
    @Autowired
    public TripdatTripItemServiceImpl(TripdatTripItemDao dao) {
        this.dao = dao;
    }

    /**
     * Name: findAll
     * Purpose: To retrieve all TripdatTripItems from the database.
     * Synopsis: Calls the IGenericDao method findAll
     * <p>
     *
     * @return List of TripdatTripItem entities.
     */
    @Override
    public List<TripdatTripItem> findAll() {
        return dao.findAll();
    }

    /**
     * Name: findOne
     * Purpose: To find the TripdatTripItem with the id passed in.
     * Synopsis: Calls the IGenericDao method findOne
     * <p>
     *
     * @param id long, the id pertaining to a record in the database.
     * @return TripdatTripItem that has the passed in ID. Or null if none found.
     */
    @Override
    public TripdatTripItem findOne(long id) {
        return dao.findOne(id);
    }

    /**
     * Name: create
     * Purpose: To create a TripdatTripItem entity.
     * Synopsis: Calls the IGenericDao method create.
     * <p>
     *
     * @param entity TripdatTripItem that will be created in the database.
     */
    @Override
    public void create(TripdatTripItem entity) {
        dao.create(entity);
    }

    /**
     * Name: update
     * Purpose: To update a TripdatTripItem Entity
     * Synopsis: Calls the IGenericDao method update.
     * <p>
     *
     * @param entity TripdatTripItem that will be updated.
     * @return TripdatTripItem that was updated.
     */
    @Override
    public TripdatTripItem update(TripdatTripItem entity) {
        return dao.update(entity);
    }

    /**
     * Name: delete
     * Purpose: To delete the entity that was passed from the database.
     * Synopsis: Calls the IGenericDao method delete.
     * <p>
     *
     * @param entity TripdatTripItem that will be deleted from the database.
     */
    @Override
    public void delete(TripdatTripItem entity) {
        dao.delete(entity);

    }

    /**
     * Name: deleteById
     * Purpose: To delete the TripdatTripItem with the corresponding id passed.
     * Synopsis: Calls the IGenericDao deleteById method with the id passed.
     * <p>
     *
     * @param entityId long, the id of the entity to be deleted.
     */
    @Override
    public void deleteById(long entityId) {
        dao.deleteById(entityId);
    }

    /**
     * Name: getItemsByTripId
     * Purpose: To retrieve the TripdatTripItems that belong to the TripdatTrip with the
     * passed id.
     * Synopsis: Calls the findByTripId method implemented in the TripdatTripItemDao class.
     * <p>
     *
     * @param id long, id of the trip whose items will be retrieved.
     * @return HashSet of TripdatTripItems that belong to the TripdatTrip with the id passed.
     */
    @Override
    public HashSet<TripdatTripItem> getItemsByTripId(long id) {
        return dao.findByTripId(id);
    }

    /**
     * Name: getMax4ItemsById
     * Purpose: To retrieve the 4 most recent TripdatTripItems that belong to the Trip
     * that has the passed in id.
     * Synopsis: Calls the findMax4ByTripId method of the TripdatTripItemDao class.
     * <p>
     *
     * @param id long, id of the TripdatTrip that will the items must have.
     * @return HashSet that contains the four most recent TripdatTripItems that belong to
     * the TripdatTrip with the id passed.
     */
    @Override
    public HashSet<TripdatTripItem> getMax4ItemsById(long id) {
        return dao.findMax4ByTripId(id);
    }

    /**
     * Name: findByItemId
     * Purpose: To find the TripdatTripItem with the id passed.
     * Synopsis: Calls the findByItemId method of the TripdatTripItemDao class.
     * <p>
     *
     * @param id id of the TripdatTripItem being searched for.
     * @return TripdatTripItem with the id passed.
     */
    @Override
    public TripdatTripItem findByItemId(long id) {
        return dao.findByItemId(id);
    }
}

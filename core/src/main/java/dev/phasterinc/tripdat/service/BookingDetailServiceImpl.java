package dev.phasterinc.tripdat.service;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.dao.IGenericDao;
import dev.phasterinc.tripdat.model.BookingDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Name: BookingDetailServiceImpl - implements the BookingDetailService interface
 */
@Service
public class BookingDetailServiceImpl implements BookingDetailService {

    // == fields ==
    IGenericDao<BookingDetail> dao;

    /**
     * Name: setDao
     * Purpose: To set the DataAccessObject for this service
     * Synopsis: Sets the member variable dao, to the passed dao.
     * <p>
     */
    @Autowired
    public void setDao(IGenericDao<BookingDetail> daoToSet) {
        dao = daoToSet;
        dao.setClazz(BookingDetail.class);
    }

    /**
     * Name: findAll
     * Purpose: To find all BookingDetails
     * Synopsis: Synopsis of the function
     * <p>
     *
     * @return List of BookingDetail objects found in data base
     */
    @Override
    public List<BookingDetail> findAll() {
        return dao.findAll();
    }

    /**
     * Name: findOne
     * Purpose: To find the BookingDetail with the passed id.\
     * Synopsis: Calls the generic Dao findOne function.
     * <p>
     *
     * @param id long, id of BookingDetail to be found.
     * @return BookingDetail, the bookingDetail with the passed id.
     */
    @Override
    public BookingDetail findOne(long id) {
        return dao.findOne(id);
    }

    /**
     * Name: create
     * Purpose: Creates the BookingDetail entity passed
     * Synopsis: Calls the generic dao create method.
     * <p>
     *
     * @param entity, BookingDetail entity to be created.
     */
    @Override
    public void create(BookingDetail entity) {
        dao.create(entity);

    }

    /**
     * Name: update
     * Purpose: Updates the database entity with the information in the entity passed.
     * Synopsis: Calls the generic update method.
     * <p>
     *
     * @param entity BookingDetail instance that is to be updated in the database.
     * @return the updated BookingDetail entity.
     */
    @Override
    public BookingDetail update(BookingDetail entity) {
        return dao.update(entity);
    }

    /**
     * Name: delete
     * Purpose: Delete the passed booking detail entity
     * Synopsis: Calls the IGenericDao delete method.
     * <p>
     *
     * @param entity BookingDetail entity to be deleted.
     */

    @Override
    public void delete(BookingDetail entity) {
        dao.delete(entity);

    }

    /**
     * Name: deleteById
     * Purpose: To delete a BookingDetail by its id.
     * Synopsis: Uses dao deleteById method to delete the BookingDetail id passed
     * <p>
     */
    @Override
    public void deleteById(long entityId) {
        dao.deleteById(entityId);

    }
}

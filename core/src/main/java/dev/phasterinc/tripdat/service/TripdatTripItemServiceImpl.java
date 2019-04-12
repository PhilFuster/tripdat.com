package dev.phasterinc.tripdat.service;

import dev.phasterinc.tripdat.dao.IGenericDao;
import dev.phasterinc.tripdat.model.TripdatTripItem;
import org.springframework.stereotype.Service;

import java.util.List;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: TripdatTripItemServiceImpl - implementation of TripdatTripItemService
 * Purpose: calls dao methods for the TripdatItem table
 */
@Service
public class TripdatTripItemServiceImpl implements TripdatTripItemService {

    // == fields ==
    IGenericDao<TripdatTripItem> dao;

    @Override
    public List<TripdatTripItem> findAll() {
        return dao.findAll();
    }

    @Override
    public TripdatTripItem findOne(long id) {
        return dao.findOne(id);
    }

    @Override
    public void create(TripdatTripItem entity) {
        dao.create(entity);

    }

    @Override
    public TripdatTripItem update(TripdatTripItem entity) {
        return dao.update(entity);
    }

    @Override
    public void delete(TripdatTripItem entity) {
        dao.delete(entity);

    }

    @Override
    public void deleteById(long entityId) {
        dao.deleteById(entityId);

    }
}

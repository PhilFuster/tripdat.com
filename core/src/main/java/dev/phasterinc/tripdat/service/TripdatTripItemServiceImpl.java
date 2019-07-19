package dev.phasterinc.tripdat.service;

import dev.phasterinc.tripdat.dao.TripdatTripItemDao;
import dev.phasterinc.tripdat.model.TripdatTripItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
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
@Transactional
public class TripdatTripItemServiceImpl implements TripdatTripItemService {

    // == fields ==
    TripdatTripItemDao dao;

    @Autowired
    public TripdatTripItemServiceImpl(TripdatTripItemDao dao) {
        this.dao = dao;
    }

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

    @Override
    public HashSet<TripdatTripItem> getItemsByTripId(long id) {
        return dao.findByTripId(id);
    }

    @Override
    public HashSet<TripdatTripItem> getMax4ItemsById(long id) {
        return dao.findMax4ByTripId(id);
    }

    @Override
    public TripdatTripItem findByItemId(long id) {
        return dao.findByItemId(id);    }
}

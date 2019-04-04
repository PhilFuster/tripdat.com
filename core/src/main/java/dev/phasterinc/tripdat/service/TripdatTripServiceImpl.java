package dev.phasterinc.tripdat.service;

import dev.phasterinc.tripdat.dao.TripdatTripDao;
import dev.phasterinc.tripdat.model.TripdatTrip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripdatTripServiceImpl implements TripdatTripService{

    @Autowired
    TripdatTripDao tripdatTripDao;

    @Override
    public void save(TripdatTrip tripdatTrip) {
        tripdatTripDao.save(tripdatTrip);
    }

    @Override
    public List<TripdatTrip> list() {
        return tripdatTripDao.list();
    }
}

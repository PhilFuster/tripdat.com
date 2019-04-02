package dev.phasterinc.tripdat.service;

import dev.phasterinc.tripdat.dao.TripdatUserDao;
import dev.phasterinc.tripdat.model.TripdatUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripdatUserServiceImpl implements TripdatUserService{

    @Autowired
    TripdatUserDao tripdatUserDao;

    @Override
    public void save(TripdatUser tripdatUser) {
        tripdatUserDao.save(tripdatUser);
    }

    @Override
    public List<TripdatUser> list() {
        return tripdatUserDao.list();
    }
}

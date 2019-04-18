package dev.phasterinc.tripdat.service;

import dev.phasterinc.tripdat.dao.RoleDao;
import dev.phasterinc.tripdat.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDao dao;

    @Override
    public Role findOne(Long id) {
        return dao.findOne(id);
    }

    @Override
    public Role findByName(String name) {
        return dao.findByName(name);
    }
}

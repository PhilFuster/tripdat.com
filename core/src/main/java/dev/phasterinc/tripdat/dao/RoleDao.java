package dev.phasterinc.tripdat.dao;

import dev.phasterinc.tripdat.model.Role;

public interface RoleDao extends IGenericDao<Role> {
    Role findByName(String name);

}

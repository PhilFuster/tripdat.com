package dev.phasterinc.tripdat.service;

import dev.phasterinc.tripdat.model.Role;

public interface RoleService {
    Role findOne(final Long id);
    Role findByName(String name);
}

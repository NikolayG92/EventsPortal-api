package com.example.eventsportal.services;


import com.example.eventsportal.models.entities.Role;

import java.util.Set;

public interface RoleService {

    Role findByAuthority(String authority);

    Set<Role> findAllRoles();

    void seedRolesInDb();
}
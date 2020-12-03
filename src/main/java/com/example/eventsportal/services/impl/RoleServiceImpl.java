package com.example.eventsportal.services.impl;

import com.example.eventsportal.models.entities.Role;
import com.example.eventsportal.repositories.RoleRepository;
import com.example.eventsportal.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Role findByAuthority(String authority) {
        return this.roleRepository
                        .findByAuthority(authority);
    }

    @Override
    public Set<Role> findAllRoles() {
        return this.roleRepository
                .findAll().stream()
                .collect(Collectors.toSet());
    }

    @Override
    public void seedRolesInDb() {
        if (roleRepository.count() == 0) {
            roleRepository.saveAndFlush(new Role("ROLE_ADMIN"));

            roleRepository.saveAndFlush(new Role("ROLE_USER"));
        }
    }
}

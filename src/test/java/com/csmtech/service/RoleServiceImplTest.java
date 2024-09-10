package com.csmtech.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import com.csmtech.model.Role;
import com.csmtech.repository.RoleRepository;

public class RoleServiceImplTest {

    private RoleRepository roleRepository;
    private RoleService roleService;

    @BeforeEach
    public void setUp() {
        roleRepository = mock(RoleRepository.class);
        roleService = new RoleServiceImpl(roleRepository);
    }

    @Test
    public void testSaveRole() {
        Role roleToSave = new Role();
        roleToSave.setRoleId(1);
        roleToSave.setRoleName("Demao");
        
       // Set an example ID for the saved role

        when(roleRepository.save(ArgumentMatchers.any(Role.class))).thenReturn(roleToSave);

        Role actualSavedRole = roleRepository.save(roleToSave);
        System.out.println(actualSavedRole);
        assertEquals(1, actualSavedRole.getRoleId());
        assertEquals("Demao", actualSavedRole.getRoleName());
    }

    
    @Test
    public void testFindById() {
        int roleId = 1;
        Role expectedRole = new Role();
        expectedRole.setRoleId(roleId);
        expectedRole.setRoleName("Admin");

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(expectedRole));

        Role actualRole = roleService.findById(roleId);

        assertNotNull(actualRole);
        assertEquals(expectedRole.getRoleId(), actualRole.getRoleId());
        assertEquals(expectedRole.getRoleName(), actualRole.getRoleName());
    }

	/*
	 * @Test public void testFindAllRole() {
	 * 
	 * 
	 * 
	 * when(roleRepository.findAll()).thenReturn(roleToSave);
	 * 
	 * List<Role> actualRoles = roleService.findAllRole();
	 * 
	 * assertNotNull(actualRoles); assertEquals(1, actualRoles.size()); }
	 */
}

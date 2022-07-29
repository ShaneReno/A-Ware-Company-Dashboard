package io.shane.services;

import java.util.List;

import io.shane.models.RoleModel;


public interface RoleService {
	List<RoleModel> getAllRoles();
	void saveRole(RoleModel roleModel);
}

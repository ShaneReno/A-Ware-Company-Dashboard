package io.shane.services;

import java.util.List;


import io.shane.models.RoleModel;

//An interface for the RoleService. This will determine the behaviours of the employeeModel without specifying
public interface RoleService {
	List<RoleModel> getAllRoles();
	void saveRole(RoleModel roleModel);
	public RoleModel getRoleByUsername(String username);
	public RoleModel getRoleByUsername2(RoleModel role);
}

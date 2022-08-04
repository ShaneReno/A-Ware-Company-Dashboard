package io.shane.services;

import java.util.List;

import io.shane.models.EmployeeModel;
import io.shane.models.RoleModel;


public interface RoleService {
	List<RoleModel> getAllRoles();
	void saveRole(RoleModel roleModel);
	public RoleModel getRoleByUsername(String username);
	public RoleModel getRoleByUsername2(RoleModel role);
}

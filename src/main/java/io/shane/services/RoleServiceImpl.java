package io.shane.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.shane.awareproject.RoleRepository;
import io.shane.models.EmployeeModel;
import io.shane.models.RoleModel;

@Service
public class RoleServiceImpl implements RoleService {
	
	
	@Autowired
	RoleRepository roleRepository;

	public List<RoleModel> getAll() {
		return (List<RoleModel>) roleRepository.findAll();
	}
	
	@Override
	public List<RoleModel> getAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	public void saveRole(RoleModel roleModel) {
		this.roleRepository.save(roleModel);
	}

	
	


}

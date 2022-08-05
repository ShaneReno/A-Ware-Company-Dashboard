package io.shane.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.shane.awareproject.RoleRepository;
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

	@Override
	public RoleModel getRoleByUsername(String username) {
		Optional<RoleModel> optional = roleRepository.findById(username);
		RoleModel role = null;

		if (optional.isPresent()) {
			role = optional.get();
		} else {
			throw new RuntimeException("Employee not found for username :: " + username);

		}

		return role;
	}

	@Override
	public RoleModel getRoleByUsername2(RoleModel model) {
		Optional<RoleModel> optional = roleRepository.findByUsername(model);
		RoleModel role = null;

		if (optional.isPresent()) {
			role = optional.get();
		} else {
			throw new RuntimeException("Employee not found for username :: " + model);

		}

		return role;
	}
	


}

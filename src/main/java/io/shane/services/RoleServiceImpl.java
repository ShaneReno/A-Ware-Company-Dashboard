package io.shane.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.shane.awareproject.RoleRepository;
import io.shane.models.RoleModel;

//Service implementation for the RoleService class. This specifies the actual behaviours and various methods
//Besides from just getters, setters, constructors and toString methods.
@Service
public class RoleServiceImpl implements RoleService {
	
	
	@Autowired
	RoleRepository roleRepository;

	public List<RoleModel> getAll() {
		return (List<RoleModel>) roleRepository.findAll();
	}
	
	//Methods used in MainController class:
	
	//Method for returning all the RoleModels in a list
	@Override
	public List<RoleModel> getAllRoles() {
		return roleRepository.findAll();
	}

	//Save a RoleModel into the respective repository when passed through.
	@Override
	public void saveRole(RoleModel roleModel) {
		this.roleRepository.save(roleModel);
	}

	//When a username is passed, search the roleRepository for this username and pop it into the Optional
	@Override
	public RoleModel getRoleByUsername(String username) {
		Optional<RoleModel> optional = roleRepository.findById(username);
		RoleModel role = null;

		//If its present, return it.
		if (optional.isPresent()) {
			role = optional.get();
		} else {
			throw new RuntimeException("Employee not found for username :: " + username);

		}

		return role;
	}

	//Same as previous method but searching for a specific model object as opposed to by username just.
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

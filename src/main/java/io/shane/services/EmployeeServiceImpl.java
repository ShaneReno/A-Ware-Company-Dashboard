package io.shane.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.shane.awareproject.EmployeeRepository;
import io.shane.models.EmployeeModel;

//Service implementation for the EmployeeService class. This specifies the actual behaviours and various methods
//Besides from just getters, setters, constructors and toString methods.
@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	//Methods used in MainController class:
	
	//Method for returning all the EmployeeModels in a list
	public List<EmployeeModel> getAll() {
		return (List<EmployeeModel>) employeeRepository.findAll();
	}

	//Method for finding all employeeModels
	@Override
	public List<EmployeeModel> getAllEmployees() {
		return employeeRepository.findAll();
	}

	//When passed an EmployeeModel object, save it to the repository. Add.
	@Override
	public void saveEmployeeHire(EmployeeModel employeeModel) {
		this.employeeRepository.save(employeeModel);

	}

	//When passed a username string, search the EmployeeModel list for it. If it is present, return it.
	@Override
	public EmployeeModel getEmployeeByUsername(String username) {
		Optional<EmployeeModel> optional = employeeRepository.findById(username);
		EmployeeModel employee = null;

		if (optional.isPresent()) {
			employee = optional.get();
		} else {
			throw new RuntimeException("Employee not found for username :: " + username);

		}

		return employee;
	}

	//Delete the passed employee by username.
	@Override
	public void deleteEmployeeByUsername(String username) {
			this.employeeRepository.deleteById(username);
	}

}

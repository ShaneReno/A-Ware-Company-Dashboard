package io.shane.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.shane.awareproject.EmployeeRepository;
import io.shane.models.EmployeeModel;
import io.shane.models.RosterModel;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public List<EmployeeModel> getAll() {
		return (List<EmployeeModel>) employeeRepository.findAll();
	}

	@Override
	public List<EmployeeModel> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public void saveEmployeeHire(EmployeeModel employeeModel) {
		this.employeeRepository.save(employeeModel);

	}

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

	@Override
	public void deleteEmployeeByUsername(String username) {
			this.employeeRepository.deleteById(username);
	}

}

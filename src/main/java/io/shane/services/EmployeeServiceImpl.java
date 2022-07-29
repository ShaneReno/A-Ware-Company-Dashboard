package io.shane.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.shane.awareproject.EmployeeRepository;
import io.shane.models.EmployeeModel;

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

}

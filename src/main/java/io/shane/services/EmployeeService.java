package io.shane.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.shane.awareproject.EmployeeRepository;
import io.shane.models.EmployeeModel;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	public List<EmployeeModel> getAll() {
		return (List<EmployeeModel>) employeeRepository.findAll();
	}

}

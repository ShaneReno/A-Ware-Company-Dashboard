package io.shane.services;

import java.util.List;

import io.shane.models.EmployeeModel;
import io.shane.models.RosterModel;


public interface EmployeeService {
	//CRUD ops
	List<EmployeeModel> getAllEmployees();
	void saveEmployeeHire(EmployeeModel employeeModel);
	EmployeeModel getEmployeeByUsername(String username);
	void deleteEmployeeByUsername(String username);
}

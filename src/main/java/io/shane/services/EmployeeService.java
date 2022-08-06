package io.shane.services;

import java.util.List;

import io.shane.models.EmployeeModel;

//An interface for the EmployeeService. This will determine the behaviours of the employeeModel without specifying
public interface EmployeeService {
	//CRUD ops
	List<EmployeeModel> getAllEmployees();
	void saveEmployeeHire(EmployeeModel employeeModel);
	EmployeeModel getEmployeeByUsername(String username);
	void deleteEmployeeByUsername(String username);
}

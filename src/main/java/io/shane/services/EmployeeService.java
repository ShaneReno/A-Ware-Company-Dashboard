package io.shane.services;

import java.util.List;

import io.shane.models.EmployeeModel;


public interface EmployeeService {
	List<EmployeeModel> getAllEmployees();
	void saveEmployeeHire(EmployeeModel employeeModel);
}

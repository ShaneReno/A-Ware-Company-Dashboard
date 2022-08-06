package io.shane.services;

import java.util.List;

import io.shane.models.RosterModel;

//An interface for the RosterService. This will determine the behaviours of the employeeModel without specifying
public interface RosterService {
	List<RosterModel> getAllRosteredEmployees();
	void saveRosteredEmployee(RosterModel rosterModel);
	RosterModel getEmployeeByemployeeId(int id);
	RosterModel getEmployeeByemployeeEmail(String email);
	void deleteRosteredEmployeeById(int id);
}

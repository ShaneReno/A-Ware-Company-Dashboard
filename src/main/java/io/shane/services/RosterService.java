package io.shane.services;

import java.util.List;

import io.shane.models.RosterModel;

public interface RosterService {
	List<RosterModel> getAllRosteredEmployees();
	void saveRosteredEmployee(RosterModel rosterModel);
	RosterModel getEmployeeByemployeeId(int id);
}

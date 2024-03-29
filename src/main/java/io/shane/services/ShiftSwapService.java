package io.shane.services;

import java.util.List;

import io.shane.models.ShiftSwapModel;


//An interface for the ShiftSwapService. This will determine the behaviours of the employeeModel without specifying
public interface ShiftSwapService {
	List<ShiftSwapModel> getAllShiftSwapRequests();
	void saveShiftSwapRequest(ShiftSwapModel shiftSwapModel);
	ShiftSwapModel getShiftSwapRequestByEmail(String email);
	ShiftSwapModel getShiftSwapRequestById(int Id);
	void deleteShiftSwapRequestById(int id);
}

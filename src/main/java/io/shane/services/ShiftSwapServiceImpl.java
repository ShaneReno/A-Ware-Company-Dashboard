package io.shane.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.shane.awareproject.ShiftSwapRepository;
import io.shane.models.ShiftSwapModel;

//Service implementation for the ShiftSwapService class. This specifies the actual behaviours and various methods
//Besides from just getters, setters, constructors and toString methods.
@Service
public class ShiftSwapServiceImpl implements ShiftSwapService {
	
	//Methods used in MainController class:
	
	//Method for returning all the shiftSwapModels in a list
	@Autowired
	private ShiftSwapRepository shiftSwapRepository;

	//Find all
	@Override
	public List<ShiftSwapModel> getAllShiftSwapRequests() {
		return shiftSwapRepository.findAll();
	}

	//Pass a ShiftSwapModel object in and save it to the repository.
	@Override
	public void saveShiftSwapRequest(ShiftSwapModel shiftSwapModel) {
		this.shiftSwapRepository.save(shiftSwapModel);	
	}

	//Pass an email through and search the shiftSwapRepository for an object that matches this email.
	@Override
	public ShiftSwapModel getShiftSwapRequestByEmail(String email) {
		Optional<ShiftSwapModel> optional = shiftSwapRepository.findByemployeeEmail(email);
		ShiftSwapModel shiftSwap = null;
		
		//If its present, return this object.
		if(optional.isPresent()) {
			shiftSwap = optional.get();
		}
		else {
			throw new RuntimeException("Shift swap request not found for Email :: " + email);  //Error handling
		}
		return shiftSwap;
	}

	//Same as previous method except search by ID, not email.
	@Override
	public ShiftSwapModel getShiftSwapRequestById(int Id) {
		Optional<ShiftSwapModel> optional = shiftSwapRepository.findByrequestId(Id);
		ShiftSwapModel shiftSwap = null;
		
		if(optional.isPresent()) {
			shiftSwap = optional.get();
		}
		else {
			throw new RuntimeException("Shift swap request not found for ID :: " + Id); //Error handling
		}
		return shiftSwap;
	}

	//Delete the shiftSwapModel from the repository if it matches the passed ID. It will match it because
	//The user does not manually enter the ID.
	@Override
	public void deleteShiftSwapRequestById(int id) {
		this.shiftSwapRepository.deleteById(id);
	}
	
	

}

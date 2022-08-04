package io.shane.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.shane.awareproject.ShiftSwapRepository;
import io.shane.models.ShiftSwapModel;

@Service
public class ShiftSwapServiceImpl implements ShiftSwapService {
	
	@Autowired
	private ShiftSwapRepository shiftSwapRepository;

	@Override
	public List<ShiftSwapModel> getAllShiftSwapRequests() {
		return shiftSwapRepository.findAll();
	}

	@Override
	public void saveShiftSwapRequest(ShiftSwapModel shiftSwapModel) {
		this.shiftSwapRepository.save(shiftSwapModel);	
	}

	@Override
	public ShiftSwapModel getShiftSwapRequestByEmail(String email) {
		Optional<ShiftSwapModel> optional = shiftSwapRepository.findByemployeeEmail(email);
		ShiftSwapModel shiftSwap = null;
		
		if(optional.isPresent()) {
			shiftSwap = optional.get();
		}
		else {
			throw new RuntimeException("Shift swap request not found for Email :: " + email);
		}
		return shiftSwap;
	}

	@Override
	public ShiftSwapModel getShiftSwapRequestById(int Id) {
		Optional<ShiftSwapModel> optional = shiftSwapRepository.findByrequestId(Id);
		ShiftSwapModel shiftSwap = null;
		
		if(optional.isPresent()) {
			shiftSwap = optional.get();
		}
		else {
			throw new RuntimeException("Shift swap request not found for ID :: " + Id);
		}
		return shiftSwap;
	}

	@Override
	public void deleteShiftSwapRequestById(int id) {
		this.shiftSwapRepository.deleteById(id);
	}
	
	

}

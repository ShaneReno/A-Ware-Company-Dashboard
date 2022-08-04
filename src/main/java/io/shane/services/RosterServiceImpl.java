package io.shane.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.shane.awareproject.RosterRepository;
import io.shane.awareproject.RosterRepository2;
import io.shane.models.RosterModel;

@Service
public class RosterServiceImpl implements RosterService {
	
	@Autowired
	private RosterRepository rosterRepository;
	
	@Autowired
	private RosterRepository2 rosterRepository2;
	
	//Displaying roster
	@Override
	public List<RosterModel> getAllRosteredEmployees() {
		return rosterRepository.findAll();
	}
	
	//Adding employee to roster
	@Override
	public void saveRosteredEmployee(RosterModel rosterModel) {
		this.rosterRepository.save(rosterModel);
	}

	//update method
	@Override
	public RosterModel getEmployeeByemployeeId(int id) {
		Optional<RosterModel> optional = rosterRepository.findByemployeeId(id);
		RosterModel roster = null;
		
		if(optional.isPresent()) {
			roster = optional.get();
		}
		else {
			throw new RuntimeException("Employee not found for ID :: " + id);
		}
		return roster;
	}

	//Delete method
	@Override
	public void deleteRosteredEmployeeById(int id) {
		this.rosterRepository.deleteById(id);
	}

	@Override
	public RosterModel getEmployeeByemployeeEmail(String email) {
		Optional<RosterModel> optional = rosterRepository2.findByemployeeEmail(email);
		RosterModel roster = null;
		
		if(optional.isPresent()) {
			roster = optional.get();
		}
		else {
			throw new RuntimeException("Employee not found for email :: " + email);
		}
		return roster;
	
	}
	
	/*
	public RosterModel get(String employeeEmail) {
		return rosterRepository.findByemployeeEmail(employeeEmail);
	}
	 */
	
	
	

}

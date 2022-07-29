package io.shane.services;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import io.shane.awareproject.RosterRepository;
import io.shane.models.RosterModel;

@Service
public class RosterServiceImpl implements RosterService {
	
	@Autowired
	private RosterRepository rosterRepository;
	
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
	
	/*
	public RosterModel get(String employeeEmail) {
		return rosterRepository.findByemployeeEmail(employeeEmail);
	}
	 */
	
	
	

}

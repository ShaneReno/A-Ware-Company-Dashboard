package io.shane.awareproject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RosterService {
	
	@Autowired
	private RosterRepository rosterRepository;
	
	public List<RosterModel> getAll() {
		return (List<RosterModel>) rosterRepository.findAll();
	}

}

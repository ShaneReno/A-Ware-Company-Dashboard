package io.shane.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.shane.awareproject.RosterRepository;
import io.shane.models.RosterModel;

@Service
public class RosterService {
	
	@Autowired
	private RosterRepository rosterRepository;
	
	public List<RosterModel> getAll() {
		return (List<RosterModel>) rosterRepository.findAll();
	}

}

package io.shane.awareproject;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.shane.models.RosterModel;
 
@Repository
public interface RosterRepository2 extends JpaRepository<RosterModel, String> {
	
	Optional<RosterModel> findByemployeeEmail(String employeeEmail);

}

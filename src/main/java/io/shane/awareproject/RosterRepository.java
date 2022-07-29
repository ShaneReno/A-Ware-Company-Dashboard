
package io.shane.awareproject;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.shane.models.RosterModel;

@Repository
public interface RosterRepository extends JpaRepository<RosterModel, Integer>{
	
	@Override
	List<RosterModel> findAll();
	
	Optional<RosterModel> findByemployeeId(int id);
	RosterModel findByemployeeEmail(String employeeEmail);

}


package io.shane.awareproject;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.shane.models.ShiftSwapModel;

@Repository
public interface ShiftSwapRepository extends JpaRepository<ShiftSwapModel, Integer>{
	
	@Override
	List<ShiftSwapModel> findAll();
	
	Optional<ShiftSwapModel> findByrequestId(int id);
	
	Optional<ShiftSwapModel> findByemployeeEmail(String employeeEmail);

}

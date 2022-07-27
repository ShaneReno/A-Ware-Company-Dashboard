
package io.shane.awareproject;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<EmployeeModel, Integer>{
	
	@Override
	List<EmployeeModel> findAll();


}

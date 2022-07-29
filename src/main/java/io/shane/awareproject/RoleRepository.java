
package io.shane.awareproject;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.shane.models.RoleModel;


@Repository
public interface RoleRepository extends JpaRepository<RoleModel, String>{
	
	@Override
	List<RoleModel> findAll();
	
	RoleModel findByUsername(String username);


}


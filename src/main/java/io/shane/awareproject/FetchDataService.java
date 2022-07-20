
package io.shane.awareproject;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FetchDataService extends JpaRepository<UserModel, Integer>{
	
	@Override
	List<UserModel> findAll();


}


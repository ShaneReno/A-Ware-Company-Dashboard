package io.shane.awarewebapplication;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String user_name) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUserName(user_name);
		
		user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + user_name));
		return user.map(MyUserDetails::new).get();
	}

}


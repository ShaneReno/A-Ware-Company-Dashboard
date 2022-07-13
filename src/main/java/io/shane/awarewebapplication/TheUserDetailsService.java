package io.shane.awarewebapplication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TheUserDetailsService implements UserDetailsService{
	
	@Override
	public UserDetails loadUserByUsername(String str) throws UsernameNotFoundException {
		return new MyUserDetails(str);
	}

}


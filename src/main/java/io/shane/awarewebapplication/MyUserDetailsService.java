package io.shane.awarewebapplication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import models.MyUserDetails;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		return new MyUserDetails(s);
	}

}


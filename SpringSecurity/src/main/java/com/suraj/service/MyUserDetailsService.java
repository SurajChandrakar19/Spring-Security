package com.suraj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.suraj.entity.UserPrincipal;
import com.suraj.entity.Users;
import com.suraj.userRepo.UserRepository;

//UserDetailsService is a interface its need to repository layer
@Service
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = repo.findByUsername(username);
		
		if(user == null) {
			System.out.println("User Not Fount");
			throw new UsernameNotFoundException("User not found"); 
		}
		
		
		return new UserPrincipal(user);
	}

}

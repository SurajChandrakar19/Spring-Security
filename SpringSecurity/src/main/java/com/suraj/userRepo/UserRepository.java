package com.suraj.userRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suraj.entity.Users;

//this repository need two dependency's Spring JPA and Mysql 
@Repository
public interface UserRepository extends JpaRepository<Users, Integer>{

	Users findByUsername(String username); 
	
	
}

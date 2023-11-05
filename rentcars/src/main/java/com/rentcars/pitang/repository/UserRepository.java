package com.rentcars.pitang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.rentcars.pitang.model.user.User;

public interface UserRepository extends JpaRepository<User, Long>{

	UserDetails findByLogin(String login);
	User findByEmail(String email);
}

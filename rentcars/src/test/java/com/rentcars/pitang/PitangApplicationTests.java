package com.rentcars.pitang;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class PitangApplicationTests {

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Test
	void contextLoads() {

	}

}

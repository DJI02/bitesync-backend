// package com.bitesync.web;

// import com.bitesync.web.security.JwtUtil;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;

// import java.util.ArrayList;

// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// @SpringBootTest
// class WebApplicationTests {

// 	// @Test
// 	// void contextLoads() {
// 	// 	// This test verifies that the application context loads successfully
// 	// }
	
// 	@Autowired
// 	private JwtUtil jwtUtil;
	
// 	@Test
// 	void testJwtTokenGeneration() {
// 		// Create a test user
// 		UserDetails userDetails = new User("test@example.com", "password", new ArrayList<>());
		
// 		// Generate a token
// 		String token = jwtUtil.generateToken(userDetails);
		
// 		// Verify token is not null
// 		assertNotNull(token);
		
// 		// Verify token is valid for the user
// 		assertTrue(jwtUtil.validateToken(token, userDetails));
// 	}
// }
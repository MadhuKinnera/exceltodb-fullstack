package com.madhu.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madhu.dto.LoginRequest;
import com.madhu.dto.ResponseMessage;
import com.madhu.entity.User;
import com.madhu.repository.UserRepository;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		Optional<User> userOptional = userRepository.findByEmailAndPassword(loginRequest.getEmail(),
				loginRequest.getPassword());

		if (userOptional.isPresent()) {
			return ResponseEntity.ok(new ResponseMessage("Login successful!"));
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
		}
	}

	@PostMapping("/createUser")
	public User createUser(@RequestBody LoginRequest request) throws Exception {

		System.out.println("the request is " + request);

		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new Exception("Email is already in use");
		}

		User user = new User();
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());

		return userRepository.save(user);
	}
}

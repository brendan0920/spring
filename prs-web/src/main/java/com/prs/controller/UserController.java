package com.prs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prs.db.UserRepo;
import com.prs.model.User;
import com.prs.model.UserLogin;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserRepo userRepo;
	
	@GetMapping("/")
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}
	
	
	// getById 	- "/api/users/{id}"
	//			- return : User
	@GetMapping("/{id}")
	public Optional<User> getUserById(@PathVariable int id) {
		// check if user exists for id
		// if yes, return user
		// if no, return not found
		Optional<User> u = userRepo.findById(id);
		if (u.isPresent()) {
			return u;
		} else {
			// return NotFound();
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "User not found for id: " + id);			
		}
	}
	
	
	// post 	- "/api/users/" (user will be in the RequestBody)
	//			- return : User
	@PostMapping("/")
	public User addUser (@RequestBody User user) {
		return userRepo.save(user);		
	}
	
	
	// put 		- "/api/users/{id}" (user passed in RequestBody)
	//			- return : NoContent()
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void putUser (@PathVariable int id, @RequestBody User user) {
		//check if passed vs id in instance
		// - BadRequest if not exist,
		if (id != user.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id mismatch vs URL.");
		// if user exists, update, else not found	
		} else if (userRepo.existsById(user.getId())){
			userRepo.save(user);
		} else {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "User not found for id: " + id);	
		}		
	}
	
	// delete 	- "/api/user/{id}"
	//			- return : NoContent()
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delUser (@PathVariable int id) {
		// check if user exists for id
		// if yes, delete user
		// if no, return not found
		if (userRepo.existsById(id)) {
			userRepo.deleteById(id);
		} else {
			// return NotFound();
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "User not found for id: " + id);
		}
	}
	
	@PostMapping("/login")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public User login (@RequestBody UserLogin userLogin) {
		User user = userRepo.findByUsername(userLogin.getUsername())
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User ID not found"));
						
		if (!userLogin.getUsername().equals(user.getUsername()) || !userLogin.getPassword().equals(user.getPassword())) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "User id and password not found");	
		} 
		
		return user;
	}
	
}

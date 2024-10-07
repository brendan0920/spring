package com.hobby.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hobbies")
public class HobbyController {
	/*
	 *  This is the first controller in Java
	 *  for the bootcamp. We'll create a list of
	 *  hobbies then perform the various CRUD
	 *  features on the list using CRUD Http Actions.
	 */
	
	private static List<String> hobbies = new ArrayList<>();
	
	@GetMapping("/")
	public List<String> getAll() {
		return hobbies;
	}
	
	@GetMapping("/{idx}")
	public String get(@PathVariable int idx) {
		return hobbies.get(idx);
	}
	
	@PostMapping("")
	public String add(String hobby) {
		if (hobbies.indexOf(hobby) >= 0) {
			return "Hobby already exists!";
		} else {
			hobbies.add(hobby);
			return "Hobby added!";
		}
	}
	
	@PutMapping("/{idx}/{hobby}")
	public String update(@PathVariable int idx, @PathVariable String hobby) {
		hobbies.set(idx, hobby);
		return "Hobby updated!";
	}
	
	@DeleteMapping("/{idx}")
	public String delete(@PathVariable int idx) {
		hobbies.remove(idx);
		return "Hobby removed!";
	}


}

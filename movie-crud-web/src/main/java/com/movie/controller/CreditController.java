package com.movie.controller;

import java.util.ArrayList;
import java.util.List;

import com.movie.model.Credit;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/credits")
public class CreditController {

	
private static List<Credit> credits = new ArrayList<>();
	
	@GetMapping("/")
	public List<Credit> getAll() {
		return credits;
	}
	
	@GetMapping("/{id}")
	public Credit get(@PathVariable int id) {
		Credit credit = null;
		for (Credit c: credits) {
			if (c.getId() == id) {
				credit = c;
			}
		}
		return credit;
	}
	
	@PostMapping("")
	public Credit add(@RequestBody Credit credit) {
		
		int maxId = 0;
		for (Credit ac: credits) {
			maxId = Math.max(maxId, c.getId());
		} 
		maxId += 1;
		credit.setId(maxId);
		credits.add(credit);
		return credit;		
	}
	
	@PutMapping("")
	public Credit update(@RequestBody Credit credit) {
		int idx = -1;
		for (int i = 0; i <credits.size(); i++) {
			if (credits.get(i).getId() == credit.getId()) {
				idx = i;
				credits.set(i, credit);
				break;
			}
		}
		if (idx < 0) {
			System.err.println("Error updating credit: id was not found.");
		}
		return credit;
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable int id) {
		for (Credit c: credits) {
			if (c.getId() == id) {
				credits.remove(c);
				break;
			}
		}
		return "Credit removed!";
	}
}

package com.prs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prs.db.LineItemRepo;
import com.prs.model.LineItem;

public class LineItemController {
	@Autowired
	private LineItemRepo lineItemRepo;
	
	@GetMapping("/")
	public List<LineItem> getAllLineItems() {
		return lineItemRepo.findAll();
	}
	
	
	// getById 	- "/api/lineItems/{id}"
	//			- return : LineItem
	@GetMapping("/{id}")
	public Optional<LineItem> getLineItemById(@PathVariable int id) {
		// check if lineItem exists for id
		// if yes, return lineItem
		// if no, return not found
		Optional<LineItem> li = lineItemRepo.findById(id);
		if (li.isPresent()) {
			return li;
		} else {
			// return NotFound();
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "LineItem not found for id: " + id);			
		}
	}
	
	
	// post 	- "/api/lineItems/" (lineItem will be in the RequestBody)
	//			- return : LineItem
	@PostMapping("/")
	public LineItem addLineItem (@RequestBody LineItem lineItem) {
		return lineItemRepo.save(lineItem);		
	}
	
	
	// put 		- "/api/lineItems/{id}" (lineItem passed in RequestBody)
	//			- return : NoContent()
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void putLineItem (@PathVariable int id, @RequestBody LineItem lineItem) {
		//check if passed vs id in instance
		// - BadLineItem if not exist,
		if (id != lineItem.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "LineItem id mismatch vs URL.");
		// if lineItem exists, update, else not found	
		} else if (lineItemRepo.existsById(lineItem.getId())){
			lineItemRepo.save(lineItem);
		} else {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "LineItem not found for id: " + id);	
		}		
	}
	
	// delete 	- "/api/lineItem/{id}"
	//			- return : NoContent()
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delLineItem (@PathVariable int id) {
		// check if lineItem exists for id
		// if yes, delete lineItem
		// if no, return not found
		if (lineItemRepo.existsById(id)) {
			lineItemRepo.deleteById(id);
		} else {
			// return NotFound();
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "LineItem not found for id: " + id);
		}
	}
}

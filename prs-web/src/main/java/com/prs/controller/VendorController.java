package com.prs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prs.db.VendorRepo;
import com.prs.model.Vendor;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {
	@Autowired
	private VendorRepo vendorRepo;
	
	@GetMapping("/")
	public List<Vendor> getAllVendors() {
		return vendorRepo.findAll();
	}
	
	
	// getById 	- "/api/vendors/{id}"
	//			- return : Vendor
	@GetMapping("/{id}")
	public Optional<Vendor> getVendorById(@PathVariable int id) {
		// check if vendor exists for id
		// if yes, return vendor
		// if no, return not found
		Optional<Vendor> v = vendorRepo.findById(id);
		if (v.isPresent()) {
			return v;
		} else {
			// return NotFound();
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Vendor not found for id: " + id);			
		}
	}
	
	
	// post 	- "/api/vendors/" (vendor will be in the RequestBody)
	//			- return : Vendor
	@PostMapping("/")
	public Vendor addVendor (@RequestBody Vendor vendor) {
		return vendorRepo.save(vendor);		
	}
	
	
	// put 		- "/api/vendors/{id}" (vendor passed in RequestBody)
	//			- return : NoContent()
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void putVendor (@PathVariable int id, @RequestBody Vendor vendor) {
		//check if passed vs id in instance
		// - BadRequest if not exist,
		if (id != vendor.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vendor id mismatch vs URL.");
		// if vendor exists, update, else not found	
		} else if (vendorRepo.existsById(vendor.getId())){
			vendorRepo.save(vendor);
		} else {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Vendor not found for id: " + id);	
		}		
	}
	
	// delete 	- "/api/vendor/{id}"
	//			- return : NoContent()
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delVendor (@PathVariable int id) {
		// check if vendor exists for id
		// if yes, delete vendor
		// if no, return not found
		if (vendorRepo.existsById(id)) {
			vendorRepo.deleteById(id);
		} else {
			// return NotFound();
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Vendor not found for id: " + id);
		}
	}
}

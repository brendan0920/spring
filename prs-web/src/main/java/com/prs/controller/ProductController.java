package com.prs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prs.db.ProductRepo;
import com.prs.model.Product;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	@Autowired
	private ProductRepo productRepo;
	
	@GetMapping("/")
	public List<Product> getAllProducts() {
		return productRepo.findAll();
	}
	
	
	// getById 	- "/api/products/{id}"
	//			- return : Product
	@GetMapping("/{id}")
	public Optional<Product> getProductById(@PathVariable int id) {
		// check if product exists for id
		// if yes, return product
		// if no, return not found
		Optional<Product> p = productRepo.findById(id);
		if (p.isPresent()) {
			return p;
		} else {
			// return NotFound();
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Product not found for id: " + id);			
		}
	}
	
	
	// post 	- "/api/products/" (product will be in the RequestBody)
	//			- return : Product
	@PostMapping("/")
	public Product addProduct (@RequestBody Product product) {
		return productRepo.save(product);		
	}
	
	
	// put 		- "/api/products/{id}" (product passed in RequestBody)
	//			- return : NoContent()
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void putProduct (@PathVariable int id, @RequestBody Product product) {
		//check if passed vs id in instance
		// - BadRequest if not exist,
		if (id != product.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product id mismatch vs URL.");
		// if product exists, update, else not found	
		} else if (productRepo.existsById(product.getId())){
			productRepo.save(product);
		} else {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Product not found for id: " + id);	
		}		
	}
	
	// delete 	- "/api/product/{id}"
	//			- return : NoContent()
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delProduct (@PathVariable int id) {
		// check if product exists for id
		// if yes, delete product
		// if no, return not found
		if (productRepo.existsById(id)) {
			productRepo.deleteById(id);
		} else {
			// return NotFound();
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Product not found for id: " + id);
		}
	}
}

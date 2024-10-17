package com.bmdb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.bmdb.model.Credit;
import com.bmdb.db.CreditRepo;

@RestController
@RequestMapping("/api/credits")
public class CreditController {
	
	@Autowired
	private CreditRepo creditRepo;
	
	@GetMapping("/")
	public List<Credit> getAllCredits() {
		return creditRepo.findAll();
	}
	
	
	// getById 	- "/api/credits/{id}"
	//			- return : Credit
	@GetMapping("/{id}")
	public Optional<Credit> getCreditById(@PathVariable int id) {
		// check if credit exists for id
		// if yes, return credit
		// if no, return not found
		Optional<Credit> c = creditRepo.findById(id);
		if (c.isPresent()) {
			return c;
		} else {
			// return NotFound();
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Movie not found for id: " + id);			
		}
	}
	
	
	// post 	- "/api/credits/" (credit will be in the RequestBody)
	//			- return : Credit
	@PostMapping("/")
	public Credit addCredit (@RequestBody Credit credit) {
		return creditRepo.save(credit);		
	}
	
	
	// put 		- "/api/credits/{id}" (credit passed in RequestBody)
	//			- return : NoContent()
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void putCredit (@PathVariable int id, @RequestBody Credit credit) {
		//check if passed vs id in instance
		// - BadRequest if not exist,
		if (id != credit.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credit id mismatch vs URL.");
		// if credit exists, update, else not found	
		} else if (creditRepo.existsById(credit.getId())){
			creditRepo.save(credit);
		} else {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Credit not found for id: " + id);	
		}		
	}
	
	// delete 	- "/api/credit/{id}"
	//			- return : NoContent()
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delCredit (@PathVariable int id) {
		// check if credit exists for id
		// if yes, delete credit
		// if no, return not found
		if (creditRepo.existsById(id)) {
			creditRepo.deleteById(id);
		} else {
			// return NotFound();
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Credit not found for id: " + id);
		}
	}
	
	// new requirement: Movie-Credits: return all credits for a movie
	// SELECT * FROM Credit WHERE MovieID = 1;
	@GetMapping("/movie-credits/{movieId}")
	public List<Credit> getCreditsForMovie(@PathVariable int movieId) {
						return creditRepo.findByMovieId(movieId);
	}
		
	// new requirement: Actor-Filmography: return all credits for an actor
	// SELECT * FROM Credit WHERE ActorID = 1;
	@GetMapping("/movie-actors/{actorId}")
	public List<Credit> getCreditsForActor(@PathVariable int actorId) {
		return creditRepo.findByActorId(actorId);
	}
	
	

}

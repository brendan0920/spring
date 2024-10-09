package com.prs.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prs.db.RequestRepo;
import com.prs.model.Request;

public class RequestController {
	@Autowired
	private RequestRepo requestRepo;
	
	@GetMapping("/")
	public List<Request> getAllRequests() {
		return requestRepo.findAll();
	}
	
	
	// getById 	- "/api/requests/{id}"
	//			- return : Request
	@GetMapping("/{id}")
	public Optional<Request> getRequestById(@PathVariable int id) {
		// check if request exists for id
		// if yes, return request
		// if no, return not found
		Optional<Request> r = requestRepo.findById(id);
		if (r.isPresent()) {
			return r;
		} else {
			// return NotFound();
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Request not found for id: " + id);			
		}
	}
	
	
	// post 	- "/api/requests/" (request will be in the RequestBody)
	//			- return : Request
	@PostMapping("/")
	public Request addRequest (@RequestBody Request request) {
		request.setStatus("NEW");
		request.setTotal(0.0);		
		
		String maxReqNbr = request.getMaxReqNbr();
		request.setRequestNumber(incrementRequestNumber(maxReqNbr));
		
		LocalDateTime now = LocalDateTime.now();
		Date submittedDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
	    request.setSubmittedDate(new java.sql.Date(submittedDate.getTime()));        
		
		return requestRepo.save(request);		
	}
	
	private String incrementRequestNumber(String maxReqNbr)
	{
	    StringBuilder nextReqNbr = new StringBuilder("");
	    int nbr = Integer.parseInt(maxReqNbr.substring(7));
	    nbr++;
	    nextReqNbr.append(maxReqNbr.charAt(0));
	    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyMMdd");
	    String dateStr = LocalDateTime.now().format(dateFormatter);
	    String nbrStr = String.format("%04d", nbr);
	    nextReqNbr.append(dateStr).append(nbrStr);
	    return nextReqNbr.toString();
	}
	
	
	// put 		- "/api/requests/{id}" (request passed in RequestBody)
	//			- return : NoContent()
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void putRequest (@PathVariable int id, @RequestBody Request request) {
		//check if passed vs id in instance
		// - BadRequest if not exist,
		if (id != request.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request id mismatch vs URL.");
		// if request exists, update, else not found	
		} else if (requestRepo.existsById(request.getId())){
			requestRepo.save(request);
		} else {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Request not found for id: " + id);	
		}		
	}
	
	// delete 	- "/api/request/{id}"
	//			- return : NoContent()
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delRequest (@PathVariable int id) {
		// check if request exists for id
		// if yes, delete request
		// if no, return not found
		if (requestRepo.existsById(id)) {
			requestRepo.deleteById(id);
		} else {
			// return NotFound();
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Request not found for id: " + id);
		}
	}
}

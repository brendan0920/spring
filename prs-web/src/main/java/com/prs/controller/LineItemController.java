package com.prs.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prs.db.LineItemRepo;
import com.prs.db.RequestRepo;
import com.prs.model.LineItem;
import com.prs.model.Request;

@RestController
@RequestMapping("/api/lineItems")
public class LineItemController {
	@Autowired
	private LineItemRepo lineItemRepo;
	@Autowired
	private RequestRepo requestRepo;

	@GetMapping("/")
	public List<LineItem> getAllLineItems() {
		return lineItemRepo.findAll();
	}

	// getById - "/api/lineItems/{id}"
	// - return : LineItem
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
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found for id: " + id);
		}
	}

	// post - "/api/lineItems/" (lineItem will be in the RequestBody)
	// - return : LineItem
	@PostMapping("/")
	public LineItem addLineItem(@RequestBody LineItem lineItem) {
		lineItemRepo.save(lineItem);

		Request request = requestRepo.findById(lineItem.getRequest().getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found."));
		List<LineItem> lineItems = lineItemRepo.findAllByRequestId(lineItem.getRequest().getId());
		double newRequestTotal = 0.0;
		for (var item : lineItems) {
			double lineTotal = (item.getProduct().getPrice()) * (item.getQuantity());
			newRequestTotal += lineTotal;
		}
		request.setTotal(newRequestTotal);
		requestRepo.save(request);

		return lineItem;
	}

	// put - "/api/lineItems/{id}" (lineItem passed in RequestBody)
	// - return : NoContent()
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void putLineItem(@PathVariable int id, @RequestBody LineItem lineItem) {
		// check if passed vs id in instance
		// - BadLineItem if not exist,
		if (id != lineItem.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "LineItem id mismatch vs URL.");
			// if lineItem exists, update, else not found
		} else if (lineItemRepo.existsById(lineItem.getId())) {
			lineItemRepo.save(lineItem);

			Request request = requestRepo.findById(lineItem.getRequest().getId())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found."));
			List<LineItem> lineItems = lineItemRepo.findAllByRequestId(id);
			double newRequestTotal = 0.0;
			for (var item : lineItems) {
				double lineTotal = (item.getProduct().getPrice()) * (item.getQuantity());
				newRequestTotal += lineTotal;
			}
			request.setTotal(newRequestTotal);
			requestRepo.save(request);

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found for id: " + id);
		}
	}

//	private void recalcucTotal(int id, LineItem lineItem) {
//		Request request = requestRepo.findById(lineItem.getRequest().getId())
//			    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found."));
//		List<LineItem> lineItems = lineItemRepo.findAllByRequestId(id);	
//		double newRequestTotal = 0.0;
//		for (var item: lineItems) {
//			double lineTotal = (item.getProduct().getPrice()) * (item.getQuantity());
//			newRequestTotal += lineTotal;
//		}						
//		request.setTotal(newRequestTotal);			
//		requestRepo.save(request);
//	}

	// delete - "/api/lineItem/{id}"
	// - return : NoContent()
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delLineItem(@PathVariable int id) {
		// check if lineItem exists for id
		// if yes, delete lineItem
		// if no, return not found
		LineItem lineItem = lineItemRepo.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found for id: " + id));
		lineItemRepo.deleteById(id);

		Request request = requestRepo.findById(lineItem.getRequest().getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found."));
		List<LineItem> lineItems = lineItemRepo.findAllByRequestId(id);
		double newRequestTotal = 0.0;
		for (var item : lineItems) {
			double lineTotal = (item.getProduct().getPrice()) * (item.getQuantity());
			newRequestTotal += lineTotal;
		}
		request.setTotal(newRequestTotal);
		requestRepo.save(request);
	}

	@GetMapping("/lines-for-req/{id}")
	public List<LineItem> getLineForRequestId(@PathVariable int id) {
		// check if lineItem exists for id
		// if yes, return lineItem
		// if no, return not found
//		Request request = requestRepo.findById(requestId)
		List<LineItem> lineItems = lineItemRepo.findAllByRequestId(id);
//	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request id not found" ));
		for (var li : lineItems) {
			if (li.getRequest().getId() == id) {
				return lineItems;
			} else {
				// // return NotFound();
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request id not found");
			}
		}
		return lineItems;
	}
}

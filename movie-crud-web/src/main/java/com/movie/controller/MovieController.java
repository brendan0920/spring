package com.movie.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

private static List<String> movies = new ArrayList<>();
	
	@GetMapping("/")
	public List<String> getAll() {
		return movies;
	}
	
	@GetMapping("/{idx}")
	public String get(@PathVariable int idx) {
		return movies.get(idx);
	}
	
	@PostMapping("")
	public String add(String movie) {
		if (movies.indexOf(movie) >= 0) {
			return "Movie already exists!";
		} else {
			movies.add(movie);
			return "Movie added!";
		}
	}
	
	@PutMapping("/{idx}/{movie}")
	public String update(@PathVariable int idx, @PathVariable String movie) {
		movies.set(idx, movie);
		return "Movie updated!";
	}
	
	@DeleteMapping("/{idx}")
	public String delete(@PathVariable int idx) {
		movies.remove(idx);
		return "Movie removed!";
	}

}

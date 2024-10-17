package com.bmdb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.bmdb.model.Credit;
import com.bmdb.model.Movie;
import com.bmdb.db.MovieRepo;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
	
	@Autowired
	private MovieRepo movieRepo;
	
	@GetMapping("/")
	public List<Movie> getAllMovies() {
		return movieRepo.findAll();
	}
	
	
	// getById 	- "/api/movies/{id}"
	//			- return : Movie
	@GetMapping("/{id}")
	public Optional<Movie> getMovieById(@PathVariable int id) {
		// check if movie exists for id
		// if yes, return movie
		// if no, return not found
		Optional<Movie> m = movieRepo.findById(id);
		if (m.isPresent()) {
			return m;
		} else {
			// return NotFound();
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Movie not found for id: " + id);			
		}
	}
	
	
	// post 	- "/api/movies/" (movie will be in the RequestBody)
	//			- return : Movie
	@PostMapping("/")
	public Movie addMovie (@RequestBody Movie movie) {
		return movieRepo.save(movie);		
	}
	
	
	// put 		- "/api/movies/{id}" (movie passed in RequestBody)
	//			- return : NoContent()
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void putMovie (@PathVariable int id, @RequestBody Movie movie) {
		//check if passed vs id in instance
		// - BadRequest if not exist,
		if (id != movie.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Movie id mismatch vs URL.");
		// if movie exists, update, else not found	
		} else if (movieRepo.existsById(movie.getId())){
			movieRepo.save(movie);
		} else {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Movie not found for id: " + id);	
		}		
	}
	
	// delete 	- "/api/movie/{id}"
	//			- return : NoContent()
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delMovie (@PathVariable int id) {
		// check if movie exists for id
		// if yes, delete movie
		// if no, return not found
		if (movieRepo.existsById(id)) {
			movieRepo.deleteById(id);
		} else {
			// return NotFound();
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Movie not found for id: " + id);
		}
	}
	
	// new requirement: : return movies by rating = R, and year = 2024
	// SELECT *	FROM Movie , WHERE Rating = 'R', AND Year = 2024;
	@GetMapping("/movie-rating-year/{rating}/{year}")
	public List<Movie> getMoviesForRatingYear(@PathVariable String rating, @PathVariable int year) {
		return movieRepo.findByRatingAndYear(rating, year);
		
	}
	
	// new requirement: : return movies by rating = PG, and year >= 1999
	// SELECT *	FROM Movie , WHERE Rating = 'PG', AND Year >= 1999;
	@GetMapping("/movie-rating-year-greater/{rating}/{year}")
	public List<Movie> getMoviesForRatingYearGreater(@PathVariable String rating, @PathVariable int year) {
		
		return movieRepo.findByRatingAndYearGreaterThanEqual(rating, year);
		
	}

	
	// new requirement: : return movies by rating != R
	// SELECT *	FROM Movie , WHERE Rating != 'R'
	@GetMapping("/movie-rating-notlike/{rating}")
	public List<Movie> getMoviesForRatingNotLike(@PathVariable String rating) {
		
		return movieRepo.findByRatingNotLike(rating);
		
	}
	
}

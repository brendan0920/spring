package com.movie.controller;

import java.util.ArrayList;
import java.util.List;
import com.movie.model.Actor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

private static List<Actor> actors = new ArrayList<>();
	
	@GetMapping("/")
	public List<Actor> getAll() {
		return actors;
	}
	
	@GetMapping("/{id}")
	public Actor get(@PathVariable int id) {
		Actor actor = null;
		for (Actor a: actors) {
			if (a.getId() == id) {
				actor = a;
			}
		}
		return actor;
	}
	
	@PostMapping("")
	public Actor add(@RequestBody Actor actor) {
		
		int maxId = 0;
		for (Actor a: actors) {
			maxId = Math.max(maxId, a.getId());
		} 
		maxId += 1;
		actor.setId(maxId);
		actors.add(actor);
		return actor;		
	}
	
	@PutMapping("")
	public Actor update(@RequestBody Actor actor) {
		int idx = -1;
		for (int i = 0; i <actors.size(); i++) {
			if (actors.get(i).getId() == actor.getId()) {
				idx = i;
				actors.set(i, actor);
				break;
			}
		}
		if (idx < 0) {
			System.err.println("Error updating actor: id was not found.");
		}
		return actor;
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable int id) {
		for (Actor a: actors) {
			if (a.getId() == id) {
				actors.remove(a);
				break;
			}
		}
		return "Actor removed!";
	}
	
	
}

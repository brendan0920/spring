package com.movie.model;

public class Credit {
	
	private int id;
	private int ActorId;	
	private int MovieId;
	private String role;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getActorId() {
		return ActorId;
	}
	public void setActorId(int actorId) {
		ActorId = actorId;
	}
	public int getMovieId() {
		return MovieId;
	}
	public void setMovieId(int movieId) {
		MovieId = movieId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "Credit [id=" + id + ", ActorId=" + ActorId + ", MovieId=" + MovieId + ", role=" + role + "]";
	}
	
	
	

}

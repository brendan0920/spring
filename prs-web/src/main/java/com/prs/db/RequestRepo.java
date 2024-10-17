package com.prs.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.prs.model.Request;

public interface RequestRepo extends JpaRepository<Request, Integer> {

	@Query("SELECT MAX(r.requestNumber) FROM Request r")
    String findMaxRequestNumber();
	
	
}

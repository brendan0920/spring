package com.prs.db;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);
}

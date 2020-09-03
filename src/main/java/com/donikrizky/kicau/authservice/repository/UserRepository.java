package com.donikrizky.kicau.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.donikrizky.kicau.authservice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	public Optional<User> findByUsername(String username);

	public Boolean existsByUsername(String username);

	@Query("SELECT u.username FROM User u WHERE u.userId = :userId")
	public String findUsernameByUserId(Integer userId);

}

package com.project.quizo.Repository;

import java.util.Optional;

import com.project.quizo.domain.userManagement.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long>  {
	
	Optional<User> findByUsernameIgnoreCase(String username);
	
	Optional<User> findByEmailIgnoreCase(String email);
	
	@Query("select u from #{#entityName} u where u.username = :username or u.email = :email")
	Optional<User> findByUsernameOrEmail(@Param("username") String username, @Param("email") String email);

}
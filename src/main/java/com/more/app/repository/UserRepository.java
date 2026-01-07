package com.more.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.more.app.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String username);
	@Query("select count(p) from PermissionEntity where p.resource = :resource AND ((p.action = :action AND p.type = ALLOW) OR (p.type = ALLOW_ALL))")
	String findByUsernameString(@Param("username") String username);
}



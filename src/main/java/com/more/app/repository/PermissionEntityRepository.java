package com.more.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.more.app.authorization.PermissionEntity;

public interface PermissionEntityRepository extends JpaRepository<PermissionEntity, Long> {
	


	List<PermissionEntity> findByRole(String role);
	
}



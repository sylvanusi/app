package com.more.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.more.app.authorization.PermissionEntity;

public interface PermissionEntityRepository extends JpaRepository<PermissionEntity, Long> {
	
	@Query("select count(p) from PermissionEntity where p.resource = :resource AND ((p.action = :action AND p.type = ALLOW) OR (p.type = ALLOW_ALL))")
	Long countForAuthorization(@Param("resource") String resource, @Param("action") String action);

}



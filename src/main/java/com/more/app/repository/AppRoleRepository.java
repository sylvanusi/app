package com.more.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.entity.AppRole;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
	List<AppRole> findByNameStartsWithAndStatusStartsWith(String name, String status);


}

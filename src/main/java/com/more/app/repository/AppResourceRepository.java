package com.more.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.entity.AppResource;

public interface AppResourceRepository extends JpaRepository<AppResource, Long> {

}

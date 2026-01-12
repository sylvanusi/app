package com.more.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {

	//code, branchNo, name, city
	List<Branch> findByCodeStartsWith(String code);
	List<Branch> findByCodeStartsWithAndNameStartsWithAndBranchNoStartsWithAndCityStartsWith(String code, String name, String branchNo, String city);
}

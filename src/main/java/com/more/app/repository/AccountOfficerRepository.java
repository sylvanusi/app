package com.more.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.entity.AccountOfficer;

public interface AccountOfficerRepository extends JpaRepository<AccountOfficer, Long> {

	//accountRole, code, description
	List<AccountOfficer> findByFullNameStartsWith(String paramCode);
	List<AccountOfficer> findByFullNameStartsWithAndDepartmentStartsWith(String fullName, String description);
}

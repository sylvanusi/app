package com.more.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.entity.AccountType;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {

	//accountRole, code, description
	List<AccountType> findByCodeStartsWith(String paramCode);
	List<AccountType> findByCodeStartsWithAndDescriptionStartsWithAndAccountRoleStartsWith(String code, String description , String accountRole);
}

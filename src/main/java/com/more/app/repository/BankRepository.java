package com.more.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.more.app.entity.Bank;
import com.more.app.entity.Country;

public interface BankRepository extends JpaRepository<Bank, Long> {

	List<Bank> findByCodeStartsWith(String code);
	List<Bank> findByCodeStartsWithAndNameStartsWith(String code, String name);
	//@Query("SELECT con FROM Bank con WHERE con.id = :id")
	//Bank findByCountryId(@Param("id") Long id);
}

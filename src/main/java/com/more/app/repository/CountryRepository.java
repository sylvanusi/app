package com.more.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.more.app.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {

	List<Country> findByCodeStartsWith(String code);
	List<Country> findByCodeStartsWithAndCountryNameStartsWith(String code, String countryName);

}

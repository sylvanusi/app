package com.more.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.entity.Parameters;

public interface ParametersRepository extends JpaRepository<Parameters, Long> {

	List<Parameters> findByParamCodeStartsWith(String paramCode);
	List<Parameters> findByParamCodeStartsWithAndParamValueStartsWith(String paramCode, String paramValue);
}

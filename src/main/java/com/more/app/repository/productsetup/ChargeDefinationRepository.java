package com.more.app.repository.productsetup;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.entity.ChargeDefination;

public interface ChargeDefinationRepository extends JpaRepository<ChargeDefination, Long>{
	List<ChargeDefination> findByChargeCodeStartsWithAndChargeNameStartsWith(String chargeCode, String chargeName);

}

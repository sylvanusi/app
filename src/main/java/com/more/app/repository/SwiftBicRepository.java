package com.more.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.entity.SwiftBic;

public interface SwiftBicRepository extends JpaRepository<SwiftBic, Long> {
	List<SwiftBic> findBySwiftBicStartsWith(String swiftBic);
	List<SwiftBic> findBySwiftBicStartsWithAndBankNameStartsWithAndBankCodeStartsWithAndCountryNameStartsWith(String swiftBic, String bankName, String bankCode, String countryName);
}

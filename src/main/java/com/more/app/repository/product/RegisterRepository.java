package com.more.app.repository.product;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.entity.product.Register;

public interface RegisterRepository extends JpaRepository<Register, Long> {
	List<Register> findByRegistrationDateBetweenAndApplicantNameStartsWith(LocalDate startDate, LocalDate endDate, String applicantName);
	
	
}

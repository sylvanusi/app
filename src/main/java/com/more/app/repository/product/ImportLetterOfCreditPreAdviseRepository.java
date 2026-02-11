package com.more.app.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.entity.product.ImportLetterOfCreditPreAdvise;

public interface ImportLetterOfCreditPreAdviseRepository extends JpaRepository<ImportLetterOfCreditPreAdvise, Long> {
	ImportLetterOfCreditPreAdvise findByLcMasterId(Long lcMasterId);

}

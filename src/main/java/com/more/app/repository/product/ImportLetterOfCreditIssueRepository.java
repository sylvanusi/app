package com.more.app.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.entity.product.ImportLetterOfCreditIssue;

public interface ImportLetterOfCreditIssueRepository extends JpaRepository<ImportLetterOfCreditIssue, Long> {
	ImportLetterOfCreditIssue findByLcMasterId(Long lcMasterId);

}

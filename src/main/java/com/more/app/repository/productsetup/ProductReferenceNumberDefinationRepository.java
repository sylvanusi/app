package com.more.app.repository.productsetup;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.entity.ProductReferenceNumberDefination;

public interface ProductReferenceNumberDefinationRepository extends JpaRepository<ProductReferenceNumberDefination, Long>{
	List<ProductReferenceNumberDefination> findByProductId(Long productId);
}

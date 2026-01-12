package com.more.app.repository.productsetup;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.entity.ProductType;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long>{
	
	List<ProductType> findByModuleIdAndCodeStartsWithAndProductTypeStartsWith(Long moduleId,String code, String productType);
}



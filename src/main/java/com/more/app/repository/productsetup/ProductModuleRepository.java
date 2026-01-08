package com.more.app.repository.productsetup;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.entity.ProductModule;

public interface ProductModuleRepository extends JpaRepository<ProductModule, Long>{
	List<ProductModule> findByCodeStartsWith(String code);
	List<ProductModule> findByCodeStartsWithAndNameStartsWith(String code, String name);
}

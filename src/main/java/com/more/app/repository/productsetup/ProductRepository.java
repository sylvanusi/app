package com.more.app.repository.productsetup;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.entity.Product;

public interface ProductRepository  extends JpaRepository<Product, Long>{

	List<Product> findByProductCodeStartsWith(String productCode);
}

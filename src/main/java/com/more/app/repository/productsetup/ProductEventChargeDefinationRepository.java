package com.more.app.repository.productsetup;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.entity.Product;
import com.more.app.entity.ProductEventChargeDefination;
import com.more.app.entity.ProductTypeEvent;

public interface ProductEventChargeDefinationRepository extends JpaRepository<ProductEventChargeDefination, Long>{
	List<ProductEventChargeDefination> findByProductAndEvent(Product product,ProductTypeEvent event);
}

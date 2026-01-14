package com.more.app.repository.productsetup;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.entity.Product;
import com.more.app.entity.ProductEventSwift;
import com.more.app.entity.ProductTypeEvent;

public interface ProductEventSwiftRepository extends JpaRepository<ProductEventSwift, Long>{
	List<ProductEventSwift> findByProductIdAndEventId(Long productId,Long eventIId);
}

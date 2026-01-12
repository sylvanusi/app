package com.more.app.repository.productsetup;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.entity.ProductTypeEvent;

public interface ProductTypeEventRepository  extends JpaRepository<ProductTypeEvent, Long>{
	List<ProductTypeEvent> findByProductTypeIdAndEventCodeStartsWith(Long productTypeId,String eventCode);
	List<ProductTypeEvent> findByProductTypeId(Long productTypeId);
}

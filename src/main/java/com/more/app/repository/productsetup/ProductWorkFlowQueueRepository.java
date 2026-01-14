package com.more.app.repository.productsetup;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.entity.Product;
import com.more.app.entity.ProductTypeEvent;
import com.more.app.entity.product.ProductWorkFlowQueue;

public interface ProductWorkFlowQueueRepository  extends JpaRepository<ProductWorkFlowQueue, Long>{
	List<ProductWorkFlowQueue> findByProductAndEvent(Product product, ProductTypeEvent event);
}

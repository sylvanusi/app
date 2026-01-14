package com.more.app.repository.productsetup;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.entity.ProductTypeEventPolicy;

public interface ProductTypeEventPolicyRepository  extends JpaRepository<ProductTypeEventPolicy, Long>{
	List<ProductTypeEventPolicy> findByEventIdAndInputQueueType(Long eventId, Boolean inputQueueType);
}

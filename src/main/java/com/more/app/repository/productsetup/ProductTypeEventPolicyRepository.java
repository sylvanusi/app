package com.more.app.repository.productsetup;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.more.app.entity.ProductTypeEventPolicy;

public interface ProductTypeEventPolicyRepository  extends JpaRepository<ProductTypeEventPolicy, Long>{
	List<ProductTypeEventPolicy> findByEventIdAndInputQueueType(Long eventId, Boolean inputQueueType);
	@Query("select a.policyClass from ProductTypeEventPolicy a, ProductWorkFlowQueue b where a.id = b.policyId and b.id =:currentQueueId")
	String getPolicyClass(@Param("currentQueueId") Long currentQueueId);
	
	@Query("select a.policyName from ProductTypeEventPolicy a, ProductWorkFlowQueue b where a.id = b.policyId and b.id =:currentQueueId")
	String getPolicyName(@Param("currentQueueId") Long currentQueueId);
}

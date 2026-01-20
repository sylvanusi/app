package com.more.app.repository.productsetup;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.more.app.entity.enums.QueueType;
import com.more.app.entity.product.ProductWorkFlowQueue;

public interface ProductWorkFlowQueueRepository  extends JpaRepository<ProductWorkFlowQueue, Long>{
	List<ProductWorkFlowQueue> findByProductIdAndEventId(Long productId, Long eventId);
	@Query("SELECT q FROM ProductWorkFlowQueue q, ProductTypeEvent e, Product p where q.productId = p.Id and p.typeId = e.productTypeId and e.id=q.eventId and e.order = 1 and q.flowSequence = 1 and q.queueType =:queueType and p.id =:productId")
	ProductWorkFlowQueue findRegisterInputterProductWorkFlowQueue(@Param("productId") Long productId, @Param("queueType") QueueType queueType);
	
	@Query("SELECT u FROM ProductWorkFlowQueue u where u.productId =:productId and u.eventId =:eventId and u.flowSequence =:flowSequence")
	ProductWorkFlowQueue findNextProductWorkFlowQueue(@Param("productId") Long productId, @Param("eventId") Long eventId, @Param("flowSequence") int flowSequence);
	
	@Query("select max(u.flowSequence) from ProductWorkFlowQueue u where u.productId =:productId and u.eventId =:eventId ")
	Integer findMaxFlowSeqByProductIdandEventId(@Param("productId") Long productId, @Param("eventId") Long eventId);
}

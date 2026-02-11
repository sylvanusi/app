package com.more.app.util;

import com.more.app.entity.Product;
import com.more.app.entity.enums.QueueType;
import com.more.app.entity.product.ProductWorkFlowQueue;
import com.more.app.entity.product.WorkFlowProductItem;
import com.more.app.repository.productsetup.ProductWorkFlowQueueRepository;

public class WorkFlowProductItemUtil
{

	private ProductWorkFlowQueueRepository repository;
	
	public <T extends WorkFlowProductItem> T generateInputterWorkFlowProductItemDetails(T entity,Product product)
	{
		ProductWorkFlowQueue queue = null, nextQueue = null;
	
		queue = repository.findRegisterInputterProductWorkFlowQueue(product.getId(), QueueType.INPUT);
	
		
		int nextSeq = queue.getFlowSequence() + 1;	
		Long eventId = queue.getEventId();
					
		nextQueue = repository.findNextProductWorkFlowQueue(product.getId(), eventId,nextSeq);	
		entity.setCurrentQueue(queue);
		entity.setCurrentQueueId(queue.getId());
		entity.setNextQueue(nextQueue);
		entity.setNextQueueId(nextQueue.getId());
		entity.setWorkflowStatus(queue.getQueueName() + " Processing");
		entity.setWorkflowReference(queue.getEventCode() + "001");
		entity.setWorkflowReferenceNo(1);
		return entity;
	}

	public ProductWorkFlowQueueRepository getRepository() {
		return repository;
	}

	public void setRepository(ProductWorkFlowQueueRepository repository) {
		this.repository = repository;
	}
}

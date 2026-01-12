package com.more.app.util;

import com.more.app.entity.Product;
import com.more.app.entity.product.ProductWorkFlowQueue;
import com.more.app.entity.product.WorkFlowProductItem;

public class WorkFlowProductItemUtil
{
	public <T extends WorkFlowProductItem> T generateInputterWorkFlowProductItemDetails(T entity,Product product)
	{
		ProductWorkFlowQueue queue = null, nextQueue = null;
		if(entity.getId() == null)
			queue = null;//new ProductDAO().findRegisterInputterProductWorkFlowQueue(product);
		
		nextQueue = null;//new ProductDAO().findNextProductWorkFlowQueue(queue);	
		entity.setCurrentQueue(queue);
		entity.setNextQueue(nextQueue);
		entity.setWorkflowStatus(queue.getQueueName() + " Processing");
		entity.setWorkflowReference(queue.getEvent().getEventCode() + "001");
		entity.setWorkflowReferenceNo(1);
		
		return entity;
	}
	
	
	
}

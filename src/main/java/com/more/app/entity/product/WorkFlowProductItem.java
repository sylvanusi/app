package com.more.app.entity.product;

public interface WorkFlowProductItem
{
	public void setCurrentQueue(ProductWorkFlowQueue currentQueue);
	public void setCurrentQueueId(Long currentQueueID);

	public void setNextQueue(ProductWorkFlowQueue nextQueue);
	public void setNextQueueId(Long nextQueueId);

	public void setWorkflowStatus(String workflowStatus);

	public void setWorkflowReference(String workflowReference);

	public void setWorkflowReferenceNo(int workflowReferenceNo);

	public ProductWorkFlowQueue getNextQueue();

	public Long getNextQueueId();

	public ProductWorkFlowQueue getCurrentQueue();
	
	public Long getCurrentQueueId();

	public String getWorkflowStatus();

	public String getWorkflowReference();

	public int getWorkflowReferenceNo();

	public Long getId();
}

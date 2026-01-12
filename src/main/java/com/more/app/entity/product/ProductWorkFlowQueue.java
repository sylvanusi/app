package com.more.app.entity.product;

import com.more.app.entity.AbstractPojo;
import com.more.app.entity.Product;
import com.more.app.entity.ProductTypeEvent;
import com.more.app.entity.ProductTypeEventPolicy;
import com.more.app.entity.enums.QueueType;

import jakarta.persistence.Entity;

@Entity
public class ProductWorkFlowQueue extends AbstractPojo
{
	private static final long serialVersionUID = 4165442068296496760L;
	private Product product;
	private ProductTypeEvent event;
	private String queueName;
	private QueueType queueType;
	private ProductTypeEventPolicy policy;
	private int flowSequence;

	public Product getProduct()
	{
		return product;
	}

	public ProductTypeEvent getEvent()
	{
		return event;
	}

	public String getQueueName()
	{
		return queueName;
	}

	public QueueType getQueueType()
	{
		return queueType;
	}

	public ProductTypeEventPolicy getPolicy()
	{
		return policy;
	}

	public int getFlowSequence()
	{
		return flowSequence;
	}

	public void setProduct(Product product)
	{
		this.product = product;
	}

	public void setEvent(ProductTypeEvent event)
	{
		this.event = event;
	}

	public void setQueueName(String queueName)
	{
		this.queueName = queueName;
	}

	public void setQueueType(QueueType queueType)
	{
		this.queueType = queueType;
	}

	public void setPolicy(ProductTypeEventPolicy policy)
	{
		this.policy = policy;
	}

	public void setFlowSequence(int flowSequence)
	{
		this.flowSequence = flowSequence;
	}	
}

package com.more.app.entity.product;

import org.hibernate.annotations.Formula;

import com.more.app.entity.AbstractPojo;
import com.more.app.entity.Product;
import com.more.app.entity.ProductTypeEvent;
import com.more.app.entity.ProductTypeEventPolicy;
import com.more.app.entity.enums.QueueType;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

@Entity
public class ProductWorkFlowQueue extends AbstractPojo
{
	private static final long serialVersionUID = 4165442068296496760L;
	
	@Transient
	private Product product;
	
	private Long productId;
	
	@Transient
	private ProductTypeEvent event;
	
	private Long eventId;
	@Formula("(select a.event_Code from Product_Type_Event a where a.id = event_Id)")
	private String eventCode;
	
	
	private String queueName;
	private QueueType queueType;
	
	@Transient
	private ProductTypeEventPolicy policy;
	private Long policyId;
	
	@Formula("(select a.policy_Name from Product_Type_Event_Policy a where a.id = policy_Id)")
	private String policyName;
	private int flowSequence;
	
	private boolean finalQueue = false;
	private boolean firstQueue = false;

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

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Long getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public boolean isFinalQueue() {
		return finalQueue;
	}

	public void setFinalQueue(boolean finalQueue) {
		this.finalQueue = finalQueue;
	}

	public boolean isFirstQueue() {
		return firstQueue;
	}

	public void setFirstQueue(boolean firstQueue) {
		this.firstQueue = firstQueue;
	}	
}

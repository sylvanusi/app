package com.more.app.entity;

import org.hibernate.annotations.Formula;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

@Entity
public class ProductEventCheckList extends AbstractPojo
{	
	private static final long serialVersionUID = 1L;
	@Transient
	private Product product;
	private Long productId;
	
	@Transient
	private ProductTypeEvent event;
	private Long eventId;
	private boolean defineCharges;
	private boolean definePosting;
	private boolean configureSwift;
	private boolean defineDocument;
	private boolean configureWorklow;
	
	@Formula("(select a.event_Code from Product_Type_Event a where a.id = event_Id)")
	private String eventName;
	
	/**
	 * @return the event
	 */
	public ProductTypeEvent getEvent()
	{
		return event;
	}
	/**
	 * @return the defineCharges
	 */
	public boolean isDefineCharges()
	{
		return defineCharges;
	}
	/**
	 * @return the definePosting
	 */
	public boolean isDefinePosting()
	{
		return definePosting;
	}
	/**
	 * @return the configureSwift
	 */
	public boolean isConfigureSwift()
	{
		return configureSwift;
	}
	/**
	 * @return the defineDocument
	 */
	public boolean isDefineDocument()
	{
		return defineDocument;
	}
	/**
	 * @return the configureWorklow
	 */
	public boolean isConfigureWorklow()
	{
		return configureWorklow;
	}
	/**
	 * @param event the event to set
	 */
	public void setEvent(ProductTypeEvent event)
	{
		this.event = event;
	}
	/**
	 * @param defineCharges the defineCharges to set
	 */
	public void setDefineCharges(boolean defineCharges)
	{
		this.defineCharges = defineCharges;
	}
	/**
	 * @param definePosting the definePosting to set
	 */
	public void setDefinePosting(boolean definePosting)
	{
		this.definePosting = definePosting;
	}
	/**
	 * @param configureSwift the configureSwift to set
	 */
	public void setConfigureSwift(boolean configureSwift)
	{
		this.configureSwift = configureSwift;
	}
	/**
	 * @param defineDocument the defineDocument to set
	 */
	public void setDefineDocument(boolean defineDocument)
	{
		this.defineDocument = defineDocument;
	}
	/**
	 * @param configureWorklow the configureWorklow to set
	 */
	public void setConfigureWorklow(boolean configureWorklow)
	{
		this.configureWorklow = configureWorklow;
	}
	/**
	 * @return the eventName
	 */
	public String getEventName()
	{
		if(null != event)
			return event.getEventCode();
		return eventName;
	}
	/**
	 * @param eventName the eventName to set
	 */
	public void setEventName(String eventName)
	{
		this.eventName = eventName;
	}
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
}

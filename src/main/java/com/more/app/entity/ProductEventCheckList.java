package com.more.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Transient;

@Entity
public class ProductEventCheckList extends AbstractPojo
{	
	@JoinColumn(referencedColumnName = "id", nullable = false)
	private ProductTypeEvent event;
	private boolean defineCharges;
	private boolean definePosting;
	private boolean configureSwift;
	private boolean defineDocument;
	private boolean configureWorklow;
	
	@Transient
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
}

package com.more.app.entity;


import com.more.app.annotations.Auditable;
import com.more.app.annotations.UIAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Transient;

@Entity
public class ProductTypeEvent extends AbstractPojo
{
	private static final long serialVersionUID = 1233419975551454813L;

	@UIAction(label = "Event Code",errorlabel="Event Code is mandatory")
	@Column(nullable = false, length =10)
	@Auditable(enableAudit=true, fieldNo=4)
	private String eventCode;
	
	@UIAction(label = "Event Description",errorlabel="Event Description is mandatory")
	@Column(nullable = false, length =35)
	@Auditable(enableAudit=true, fieldNo=5)
	private String eventDescription;
	
	@Auditable(enableAudit=true, fieldNo=6)
	@UIAction(label = "Product Type",errorlabel="Product Type is required")
	@JoinColumn(referencedColumnName = "id", nullable = false)
	private ProductType productType;
	
	@UIAction(label = "Product Type",errorlabel="Module is required")
	@Transient 
	private String productTypeDisplay;
	
	@UIAction(label = "Event Order",errorlabel="Event Order is mandatory")
	@Column(nullable = false, name = "EVENTORDER")
	@Auditable(enableAudit=true, fieldNo=7)
	private Integer order;
	
	@UIAction(label = "Entity Class",errorlabel="Policy Class is mandatory")
	@Column(nullable = true, name = "ENTITYCLASS")
	@Auditable(enableAudit=true, fieldNo=8)
	private String entityClass;

	/**
	 * @return the eventCode
	 */
	public String getEventCode()
	{
		return eventCode;
	}

	/**
	 * @return the eventDescription
	 */
	public String getEventDescription()
	{
		return eventDescription;
	}

	/**
	 * @return the productType
	 */
	public ProductType getProductType()
	{
		return productType;
	}

	/**
	 * @return the productTypeDisplay
	 */
	public String getProductTypeDisplay()
	{
		if(null != getProductType())
			productTypeDisplay=getProductType().getProductType();
		return productTypeDisplay;
	}

	/**
	 * @param eventCode the eventCode to set
	 */
	public void setEventCode(String eventCode)
	{
		this.eventCode = eventCode;
	}

	/**
	 * @param eventDescription the eventDescription to set
	 */
	public void setEventDescription(String eventDescription)
	{
		this.eventDescription = eventDescription;
	}

	/**
	 * @param productType the productType to set
	 */
	public void setProductType(ProductType productType)
	{
		this.productType = productType;
	}

	/**
	 * @param productTypeDisplay the productTypeDisplay to set
	 */
	public void setProductTypeDisplay(String productTypeDisplay)
	{
		this.productTypeDisplay = productTypeDisplay;
	}

	/**
	 * @return the order
	 */
	public Integer getOrder()
	{
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(Integer order)
	{
		this.order = order;
	}

	/**
	 * @return the entityClass
	 */
	public String getEntityClass()
	{
		return entityClass;
	}

	/**
	 * @param entityClass the entityClass to set
	 */
	public void setEntityClass(String entityClass)
	{
		this.entityClass = entityClass;
	}
}

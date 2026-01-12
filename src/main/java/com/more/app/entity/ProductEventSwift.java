package com.more.app.entity;

import com.more.app.entity.enums.SwiftMessageType;
import com.more.app.entity.enums.converters.SwiftMessageTypeConverter;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;

@Entity
public class ProductEventSwift extends AbstractPojo
{
	private static final long serialVersionUID = 1L;

	@JoinColumn(nullable = false, referencedColumnName = "id")
	private Product product;

	@JoinColumn(nullable = false, referencedColumnName = "id")
	private ProductTypeEvent event;

	@Convert(converter = SwiftMessageTypeConverter.class)
	private SwiftMessageType swiftMessageType;
	
	private boolean incoming;
	private boolean outgoing;
	/**
	 * @return the product
	 */
	public Product getProduct()
	{
		return product;
	}
	/**
	 * @return the event
	 */
	public ProductTypeEvent getEvent()
	{
		return event;
	}
	/**
	 * @return the swiftMessage
	 */
	public SwiftMessageType getSwiftMessageType()
	{
		return swiftMessageType;
	}
	/**
	 * @return the incoming
	 */
	public boolean isIncoming()
	{
		return incoming;
	}
	/**
	 * @return the outgoing
	 */
	public boolean isOutgoing()
	{
		return outgoing;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product)
	{
		this.product = product;
	}
	/**
	 * @param event the event to set
	 */
	public void setEvent(ProductTypeEvent event)
	{
		this.event = event;
	}
	/**
	 * @param swiftMessage the swiftMessage to set
	 */
	public void setSwiftMessageType(SwiftMessageType swiftMessageType)
	{
		this.swiftMessageType = swiftMessageType;
	}
	/**
	 * @param incoming the incoming to set
	 */
	public void setIncoming(boolean incoming)
	{
		this.incoming = incoming;
	}
	/**
	 * @param outgoing the outgoing to set
	 */
	public void setOutgoing(boolean outgoing)
	{
		this.outgoing = outgoing;
	}

	
}

package com.more.app.entity;

import com.more.app.entity.enums.ReferenceNumberItem;
import com.more.app.entity.enums.converters.ReferenceNumberItemsConverter;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Transient;

@Entity
public class ProductReferenceNumberDefination extends AbstractPojo
{
	private static final long serialVersionUID = 1L;

	@Transient
	private Product product;
	
	@JoinColumn(nullable = false, referencedColumnName = "id")
	private Long productId;
	
	@Convert(converter = ReferenceNumberItemsConverter.class)
	private ReferenceNumberItem item;
	
	private int position;
	
	private int refNoStartIndex;
	
	private int refNoStopIndex;
	
	@Transient
	private String refItemValue;
	
	@Transient
	private int itemLength;

	public Product getProduct()
	{
		return product;
	}

	public ReferenceNumberItem getItem()
	{
		return item;
	}

	public int getPosition()
	{
		return position;
	}

	public int getRefNoStartIndex()
	{
		return refNoStartIndex;
	}

	public int getRefNoStopIndex()
	{
		return refNoStopIndex;
	}

	public void setProduct(Product product)
	{
		this.product = product;
	}

	public void setItem(ReferenceNumberItem item)
	{
		this.item = item;
	}

	public void setPosition(int position)
	{
		this.position = position;
	}

	public void setRefNoStartIndex(int refNoStartIndex)
	{
		this.refNoStartIndex = refNoStartIndex;
	}

	public void setRefNoStopIndex(int refNoStopIndex)
	{
		this.refNoStopIndex = refNoStopIndex;
	}

	public String getRefItemValue()
	{
		return refItemValue;
	}

	public void setRefItemValue(String refItemValue)
	{
		this.refItemValue = refItemValue;
	}

	public int getItemLength()
	{
		return itemLength;
	}

	public void setItemLength(int itemLength)
	{
		this.itemLength = itemLength;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	
}

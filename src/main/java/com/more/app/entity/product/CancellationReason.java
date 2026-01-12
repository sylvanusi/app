package com.more.app.entity.product;

import com.more.app.entity.AbstractPojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class CancellationReason extends AbstractPojo
{
	@Column(length =140)
	private String reason;

	public String getReason()
	{
		return reason;
	}

	public void setReason(String reason)
	{
		this.reason = reason;
	}	
}

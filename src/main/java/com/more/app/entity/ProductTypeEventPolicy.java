package com.more.app.entity;

import com.more.app.annotations.Auditable;
import com.more.app.annotations.UIAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;

@Entity
public class ProductTypeEventPolicy extends AbstractPojo
{
	private static final long serialVersionUID = 2864096572258498667L;

	@UIAction(label = "Event",errorlabel="Event is mandatory")
	@JoinColumn(referencedColumnName = "id", nullable = false)
	@Auditable(enableAudit=true, fieldNo=4)
	private ProductTypeEvent event;
	
	@UIAction(label = "Policy Class",errorlabel="Policy Class is mandatory")
	@Column(nullable = false)
	@Auditable(enableAudit=true, fieldNo=5)
	private String policyClass;
	
	@UIAction(label = "Policy Name",errorlabel="Policy Name is mandatory")
	@Column(nullable = false)
	@Auditable(enableAudit=true, fieldNo=6)
	private String policyName;
	
	private Boolean inputQueueType = false;
	private Boolean updateQueueType = false;
	private Boolean approvalQueueType = false;
	private Boolean cancelQueueType = false;

	public ProductTypeEvent getEvent()
	{
		return event;
	}

	public String getPolicyClass()
	{
		return policyClass;
	}

	public String getPolicyName()
	{
		return policyName;
	}

	public Boolean getInputQueueType()
	{
		return inputQueueType;
	}

	public Boolean getUpdateQueueType()
	{
		return updateQueueType;
	}

	public Boolean getApprovalQueueType()
	{
		return approvalQueueType;
	}

	public Boolean getCancelQueueType()
	{
		return cancelQueueType;
	}

	public void setEvent(ProductTypeEvent event)
	{
		this.event = event;
	}

	public void setPolicyClass(String policyClass)
	{
		this.policyClass = policyClass;
	}

	public void setPolicyName(String policyName)
	{
		this.policyName = policyName;
	}

	public void setInputQueueType(Boolean inputQueueType)
	{
		this.inputQueueType = inputQueueType;
	}

	public void setUpdateQueueType(Boolean updateQueueType)
	{
		this.updateQueueType = updateQueueType;
	}
	
	public void setApprovalQueueType(Boolean approvalQueueType)
	{
		this.approvalQueueType = approvalQueueType;
	}
	public void setCancelQueueType(Boolean cancelQueueType)
	{
		this.cancelQueueType = cancelQueueType;
	}	
}

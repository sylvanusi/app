package com.more.app.entity.product;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.more.app.entity.AbstractPojo;
import com.more.app.entity.Branch;
import com.more.app.entity.Customer;
import com.more.app.entity.Product;
import com.more.app.entity.enums.Status;
import com.more.app.util.annotations.Auditable;
import com.more.app.util.annotations.UIAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Transient;

@Entity
public class Register extends AbstractPojo implements WorkFlowProductItem
{
	@Transient
	@UIAction(label = "Module",errorlabel="Module is required")
	private String module;
	
	@UIAction(label = "Product Type",errorlabel="Product Type is required")
	@Transient
	private String productType;
	
	@Auditable(enableAudit=true, fieldNo=7)
	@UIAction(label = "Product",errorlabel="Product is required")
	@JoinColumn(referencedColumnName = "id")
	private Product product;
	
	@UIAction(label = "Transaction Reference",errorlabel="Transaction Reference is mandatory")
	@Column(length =16)
	@Auditable(enableAudit=true, fieldNo=8)
	private String transactionReference;
	
	@UIAction(label = "Internal Reference",errorlabel="Internal Reference is mandatory")
	@Column(length =16)
	@Auditable(enableAudit=true, fieldNo=21)
	private String internalReference;
	
	@UIAction(label = "Ccy",errorlabel="Transaction Currency is mandatory")
	@Column(length =3)
	@Auditable(enableAudit=true, fieldNo=9)
	private String transactionCcy;
	
	@UIAction(label = "Transaction Amount",errorlabel="Transaction Amount is mandatory")
	@Column(length =15)
	@Auditable(enableAudit=true, fieldNo=10)
	private BigDecimal transactionAmount;
	
	@Auditable(enableAudit=true, fieldNo=11)
	@UIAction(label = "Applicant",errorlabel="Applicant is required")
	@JoinColumn(referencedColumnName = "id")
	private Customer applicant;
	
	@Auditable(enableAudit=true, fieldNo=12)
	@UIAction(label = "Beneficiary",errorlabel="Beneficiary is required")
	@JoinColumn(referencedColumnName = "id")
	private Customer beneficiaryCustomer;
	
	@Auditable(enableAudit=true, fieldNo=22)
	@UIAction(label = "Beneficiary",errorlabel="Beneficiary is required")
	@Column(length = 35)
	private String beneficiaryCustomerName;
	
	@Auditable(enableAudit=true, fieldNo=13)
	@UIAction(label = "Transacting Branch",errorlabel="Transacting Branch is required")
	@JoinColumn(referencedColumnName = "id")
	private Branch transactionBranch;
	
	@Auditable(enableAudit=true, fieldNo=14)
	@UIAction(label = "Work Flow Ref",errorlabel="Work Flow Ref is required")
	@Column(length =15)
	private String workflowReference;
	
	@Auditable(enableAudit=true, fieldNo=15)
	@UIAction(label = "Work Flow Ref No",errorlabel="Work Flow Ref No is required")
	@Column(length =3)
	private int workflowReferenceNo;
	
	@Auditable(enableAudit=true, fieldNo=16)
	@UIAction(label = "Work Flow Status",errorlabel="Work Flow Status")
	@Column(length =35)
	private String workflowStatus;
	
	@Auditable(enableAudit=true, fieldNo=17)
	@UIAction(label = "Current Queue",errorlabel="Current Queue is required")
	@JoinColumn(referencedColumnName = "id")
	private ProductWorkFlowQueue currentQueue;
	
	@Auditable(enableAudit=true, fieldNo=18)
	@UIAction(label = "Next Queue",errorlabel="Next Queue is required")
	@JoinColumn(referencedColumnName = "id")
	private ProductWorkFlowQueue nextQueue;
	
	@Auditable(enableAudit=true, fieldNo=19)
	@UIAction(label = "Transaction Status",errorlabel="Transaction Status is required")
	@Column(length =1)
	private Status transactionStatus;
	
	@Transient
	//@Auditable(enableAudit=true, fieldNo=20)
	//@UIAction(label = "Reason for cancelling",errorlabel="Reason for cancelling is required")
	//@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	//@JoinColumn(referencedColumnName = "id")
	private Set<CancellationReason> cancelReasons = new HashSet<>();
	
	@Transient
	//@Auditable(enableAudit=true, fieldNo=21)
	//@UIAction(label = "Reason for declining",errorlabel="Reason for declining is required")
	//@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	//@JoinColumn(referencedColumnName = "id")
	private Set<CancellationReason> declineComments = new HashSet<>();

	public String getModule()
	{
		if(null != getProduct())
			module = getProduct().getModule().getName();
		return module;
	}

	public String getProductType()
	{
		if(null != getProduct())
			productType = getProduct().getType().getProductType();
		return productType;
	}

	public Product getProduct()
	{
		return product;
	}

	public String getTransactionReference()
	{
		return transactionReference;
	}

	public String getTransactionCcy()
	{
		return transactionCcy;
	}

	public Customer getApplicant()
	{
		return applicant;
	}

	public Customer getBeneficiaryCustomer()
	{
		return beneficiaryCustomer;
	}

	public Branch getTransactionBranch()
	{
		return transactionBranch;
	}

	public void setModule(String module)
	{
		this.module = module;
	}

	public void setProductType(String productType)
	{
		this.productType = productType;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product)
	{
		this.product = product;
	}

	public void setTransactionReference(String transactionReference)
	{
		this.transactionReference = transactionReference;
	}

	public void setTransactionCcy(String transactionCcy)
	{
		this.transactionCcy = transactionCcy;
	}

	public void setApplicant(Customer applicant)
	{
		this.applicant = applicant;
	}

	public void setBeneficiaryCustomer(Customer beneficiaryCustomer)
	{
		this.beneficiaryCustomer = beneficiaryCustomer;
	}

	public void setTransactionBranch(Branch transactionBranch)
	{
		this.transactionBranch = transactionBranch;
	}

	public BigDecimal getTransactionAmount()
	{
		return transactionAmount;
	}

	public String getWorkflowReference()
	{
		return workflowReference;
	}

	public String getWorkflowStatus()
	{
		return workflowStatus;
	}

	public ProductWorkFlowQueue getCurrentQueue()
	{
		return currentQueue;
	}

	public ProductWorkFlowQueue getNextQueue()
	{
		return nextQueue;
	}

	public void setTransactionAmount(BigDecimal transactionAmount)
	{
		this.transactionAmount = transactionAmount;
	}

	public void setWorkflowReference(String workflowReference)
	{
		this.workflowReference = workflowReference;
	}

	public void setWorkflowStatus(String workflowStatus)
	{
		this.workflowStatus = workflowStatus;
	}

	public void setCurrentQueue(ProductWorkFlowQueue currentQueue)
	{
		this.currentQueue = currentQueue;
	}

	public void setNextQueue(ProductWorkFlowQueue nextQueue)
	{
		this.nextQueue = nextQueue;
	}

	public int getWorkflowReferenceNo()
	{
		return workflowReferenceNo;
	}

	public void setWorkflowReferenceNo(int workflowReferenceNo)
	{
		this.workflowReferenceNo = workflowReferenceNo;
	}

	public Status getTransactionStatus()
	{
		return transactionStatus;
	}
	
	public void setTransactionStatus(Status transactionStatus)
	{
		this.transactionStatus = transactionStatus;
	}

	public Set<CancellationReason> getCancelReasons()
	{
		return cancelReasons;
	}

	public void setCancelReasons(Set<CancellationReason> cancelReasons)
	{
		this.cancelReasons = cancelReasons;
	}

	public String getBeneficiaryCustomerName()
	{
		return beneficiaryCustomerName;
	}

	public void setBeneficiaryCustomerName(String beneficiaryCustomerName)
	{
		this.beneficiaryCustomerName = beneficiaryCustomerName;
	}

	public String getInternalReference()
	{
		return internalReference;
	}

	public void setInternalReference(String internalReference)
	{
		this.internalReference = internalReference;
	}		
}

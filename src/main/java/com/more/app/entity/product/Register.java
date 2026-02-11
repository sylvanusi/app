package com.more.app.entity.product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Formula;

import com.more.app.entity.AbstractPojo;
import com.more.app.entity.Branch;
import com.more.app.entity.Customer;
import com.more.app.entity.Product;
import com.more.app.entity.enums.Status;
import com.more.app.util.annotations.Auditable;
import com.more.app.util.annotations.UIAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

@Entity
public class Register extends AbstractPojo implements WorkFlowProductItem {
	private static final long serialVersionUID = -3559711307824157622L;

	@Formula("(select a.code from Product_Module a, Product_Type b, product c where a.id = b.module_Id and b.id = c.type_Id and c.id = product_Id)")
	@UIAction(label = "Module", errorlabel = "Module is required")
	private String module;

	@UIAction(label = "Product Type", errorlabel = "Product Type is required")
	@Formula("(select b.product_Type from product a , Product_Type b where a.type_id = b.id and a.id = product_Id)")
	private String productType;
	
	@UIAction(label = "Product Type", errorlabel = "Product Type is required")
	@Formula("(select b.code from product a , Product_Type b where a.type_id = b.id and a.id = product_Id)")
	private String productTypeCode;

	@UIAction(label = "Product Name", errorlabel = "Product Name is required")
	@Formula("(select a.product_Name from product a where a.id = product_Id)")
	private String productName;

	@Auditable(enableAudit = true, fieldNo = 7)
	@UIAction(label = "Product", errorlabel = "Product is required")
	@Transient
	private Product product;

	@Auditable(enableAudit = true, fieldNo = 7)
	@UIAction(label = "Product", errorlabel = "Product is required")
	@JoinColumn(referencedColumnName = "id")
	private Long productId;

	@UIAction(label = "Transaction Reference", errorlabel = "Transaction Reference is mandatory")
	@Column(length = 16)
	@Auditable(enableAudit = true, fieldNo = 8)
	private String transactionReference;

	@UIAction(label = "Internal Reference", errorlabel = "Internal Reference is mandatory")
	@Column(length = 16)
	@Auditable(enableAudit = true, fieldNo = 21)
	private String internalReference;

	@UIAction(label = "Ccy", errorlabel = "Transaction Currency is mandatory")
	@Column(length = 3)
	@Auditable(enableAudit = true, fieldNo = 9)
	private String transactionCcy;

	@UIAction(label = "Transaction Amount", errorlabel = "Transaction Amount is mandatory")
	@Column(length = 15)
	@Auditable(enableAudit = true, fieldNo = 10)
	private BigDecimal transactionAmount;

	@Auditable(enableAudit = true, fieldNo = 11)
	@UIAction(label = "Applicant", errorlabel = "Applicant is required")
	@Transient
	private Customer applicant;

	@Auditable(enableAudit = true, fieldNo = 11)
	@UIAction(label = "Applicant", errorlabel = "Applicant is required")
	@JoinColumn(referencedColumnName = "id")
	private Long applicantId;

	@Auditable(enableAudit = true, fieldNo = 11)
	@UIAction(label = "Applicant Name", errorlabel = "Applicant is required")
	@Column(length = 35)
	private String applicantName;

	@Auditable(enableAudit = true, fieldNo = 12)
	@UIAction(label = "Beneficiary", errorlabel = "Beneficiary is required")
	@Transient
	private Customer beneficiaryCustomer;

	@Auditable(enableAudit = true, fieldNo = 12)
	@UIAction(label = "Beneficiary", errorlabel = "Beneficiary is required")
	@JoinColumn(referencedColumnName = "id")
	private Long beneficiaryCustomerId;

	@Auditable(enableAudit = true, fieldNo = 22)
	@UIAction(label = "Beneficiary", errorlabel = "Beneficiary is required")
	@Column(length = 35)
	private String beneficiaryCustomerName;

	@Auditable(enableAudit = true, fieldNo = 13)
	@UIAction(label = "Transacting Branch", errorlabel = "Transacting Branch is required")
	@Transient
	private Branch transactionBranch;

	@Auditable(enableAudit = true, fieldNo = 13)
	@UIAction(label = "Transacting Branch", errorlabel = "Transacting Branch is required")
	@Formula("(select a.name from Branch a where a.id = transaction_Branch_Id)")
	private String transactionBranchName;

	@Auditable(enableAudit = true, fieldNo = 13)
	@UIAction(label = "Transacting Branch", errorlabel = "Transacting Branch is required")
	@JoinColumn(referencedColumnName = "id")
	private Long transactionBranchId;
	
	@Auditable(enableAudit = true, fieldNo = 13)
	@UIAction(label = "Transacting Branch", errorlabel = "Transacting Branch is required")
	@Formula("(select a.code from Branch a where a.id = transaction_Branch_Id)")
	private String transactionBranchCode;
	
	@Auditable(enableAudit = true, fieldNo = 13)
	@UIAction(label = "Transacting Branch", errorlabel = "Transacting Branch is required")
	@Formula("(select a.branch_No from Branch a where a.id = transaction_Branch_Id)")
	private String transactionBranchNo;
	

	@Auditable(enableAudit = true, fieldNo = 14)
	@UIAction(label = "Work Flow Ref", errorlabel = "Work Flow Ref is required")
	@Column(length = 15)
	private String workflowReference;

	@Auditable(enableAudit = true, fieldNo = 15)
	@UIAction(label = "Work Flow Ref No", errorlabel = "Work Flow Ref No is required")
	@Column(length = 3)
	private int workflowReferenceNo;

	@Auditable(enableAudit = true, fieldNo = 16)
	@UIAction(label = "Work Flow Status", errorlabel = "Work Flow Status")
	@Column(length = 35)
	private String workflowStatus;

	@Auditable(enableAudit = true, fieldNo = 17)
	@UIAction(label = "Current Queue", errorlabel = "Current Queue is required")
	@Transient
	private ProductWorkFlowQueue currentQueue;

	@Auditable(enableAudit = true, fieldNo = 17)
	@UIAction(label = "Current Queue", errorlabel = "Current Queue is required")
	@JoinColumn(referencedColumnName = "id")
	private Long currentQueueId;

	@Auditable(enableAudit = true, fieldNo = 17)
	@UIAction(label = "Current Queue", errorlabel = "Current Queue is required")
	@Formula("(select a.queue_Name from Product_Work_Flow_Queue a where a.id = current_Queue_Id)")
	private String currentQueueName;

	@Formula("(select a.event_description from Product_type_event a, Product_Work_Flow_Queue b where b.event_id = a.Id and b.id = current_Queue_Id)")
	private String eventDescription;

	@Auditable(enableAudit = true, fieldNo = 18)
	@UIAction(label = "Next Queue", errorlabel = "Next Queue is required")
	@Transient
	private ProductWorkFlowQueue nextQueue;

	@Auditable(enableAudit = true, fieldNo = 18)
	@UIAction(label = "Next Queue", errorlabel = "Next Queue is required")
	@JoinColumn(referencedColumnName = "id")
	private Long nextQueueId;

	@Auditable(enableAudit = true, fieldNo = 18)
	@UIAction(label = "Next Queue", errorlabel = "Next Queue is required")
	@Formula("(select a.queue_Name from Product_Work_Flow_Queue a where a.id = next_Queue_Id)")
	private String nextQueueName;

	@Auditable(enableAudit = true, fieldNo = 18)
	@UIAction(label = "Event", errorlabel = "Event is required")
	@Formula("(select b.event_description from Product_Work_Flow_Queue a, product_type_event b where b.id = a.event_Id and a.id = current_Queue_Id)")
	private String eventName;

	@Auditable(enableAudit = true, fieldNo = 18)
	@UIAction(label = "Event", errorlabel = "Event is required")
	@Formula("(select b.id from Product_Work_Flow_Queue a, product_type_event b where b.id = a.event_Id and a.id = current_Queue_Id)")
	private Long currentQueueEventId;

	@Auditable(enableAudit = true, fieldNo = 18)
	@UIAction(label = "Event", errorlabel = "Event is required")
	@Formula("(select a.flow_Sequence from Product_Work_Flow_Queue a where a.id = current_Queue_Id)")
	private Integer currentQueueFlowSequence;

	@Auditable(enableAudit = true, fieldNo = 18)
	@UIAction(label = "Event", errorlabel = "Event is required")
	@Formula("(select a.final_Queue from Product_Work_Flow_Queue a where a.id = current_Queue_Id)")
	private boolean finalQueue;

	@Auditable(enableAudit = true, fieldNo = 18)
	@UIAction(label = "Event", errorlabel = "Event is required")
	@Formula("(select b.id from Product_Work_Flow_Queue a, product_type_event b where b.id = a.event_Id and a.id = next_Queue_Id)")
	private Long nextQueueEventId;

	@Auditable(enableAudit = true, fieldNo = 18)
	@UIAction(label = "Event", errorlabel = "Event is required")
	@Formula("(select a.flow_Sequence from Product_Work_Flow_Queue a where a.id = next_Queue_Id)")
	private Integer nextQueueFlowSequence;

	@Auditable(enableAudit = true, fieldNo = 19)
	@UIAction(label = "Transaction Status", errorlabel = "Transaction Status is required")
	@Column(length = 1)
	private Status transactionStatus;
	
	@Transient
	private String transactionStatus_String;

	@Auditable(enableAudit = true, fieldNo = 19)
	@UIAction(label = "Event Status", errorlabel = "Event Status is required")
	@Column(length = 1)
	private Status eventStatus;

	@Transient
	@Auditable(enableAudit=true, fieldNo=20)
	@UIAction(label = "Reason for cancelling",errorlabel="Reason for cancelling is required")
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	 @JoinColumn(referencedColumnName = "id")
	private Set<CancellationReason> cancelReasons = new HashSet<>();

	@Transient
	// @Auditable(enableAudit=true, fieldNo=21)
	// @UIAction(label = "Reason for declining",errorlabel="Reason for declining is
	// required")
	// @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	// @JoinColumn(referencedColumnName = "id")
	private Set<CancellationReason> declineComments = new HashSet<>();
	
	@UIAction(label = "Registration Date", errorlabel = "Registration Date is mandatory")
	@Auditable(enableAudit = true, fieldNo = 22)
	private LocalDate registrationDate;

	public String getModule() {
		return module;
	}

	public String getProductType() {

		return productType;
	}

	public Product getProduct() {
		return product;
	}

	public String getTransactionReference() {
		return transactionReference;
	}

	public String getTransactionCcy() {
		return transactionCcy;
	}

	public Customer getApplicant() {
		return applicant;
	}

	public Customer getBeneficiaryCustomer() {
		return beneficiaryCustomer;
	}

	public Branch getTransactionBranch() {
		return transactionBranch;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	public void setTransactionReference(String transactionReference) {
		this.transactionReference = transactionReference;
	}

	public void setTransactionCcy(String transactionCcy) {
		this.transactionCcy = transactionCcy;
	}

	public void setApplicant(Customer applicant) {
		this.applicant = applicant;
	}

	public void setBeneficiaryCustomer(Customer beneficiaryCustomer) {
		this.beneficiaryCustomer = beneficiaryCustomer;
	}

	public void setTransactionBranch(Branch transactionBranch) {
		this.transactionBranch = transactionBranch;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public String getWorkflowReference() {
		return workflowReference;
	}

	public String getWorkflowStatus() {
		return workflowStatus;
	}

	public ProductWorkFlowQueue getCurrentQueue() {
		return currentQueue;
	}

	public ProductWorkFlowQueue getNextQueue() {
		return nextQueue;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public void setWorkflowReference(String workflowReference) {
		this.workflowReference = workflowReference;
	}

	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}

	public void setCurrentQueue(ProductWorkFlowQueue currentQueue) {
		this.currentQueue = currentQueue;
	}

	public void setNextQueue(ProductWorkFlowQueue nextQueue) {
		this.nextQueue = nextQueue;
	}

	public int getWorkflowReferenceNo() {
		return workflowReferenceNo;
	}

	public void setWorkflowReferenceNo(int workflowReferenceNo) {
		this.workflowReferenceNo = workflowReferenceNo;
	}

	public Status getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(Status transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	
	public String getTransactionStatus_String() {
		return transactionStatus.getStatus();
	}

	public void setTransactionStatus_String(String transactionStatus_String) {
		this.transactionStatus_String =  transactionStatus.getStatus();
	}

	public Set<CancellationReason> getCancelReasons() {
		return cancelReasons;
	}

	public void setCancelReasons(Set<CancellationReason> cancelReasons) {
		this.cancelReasons = cancelReasons;
	}

	public String getBeneficiaryCustomerName() {
		return beneficiaryCustomerName;
	}

	public void setBeneficiaryCustomerName(String beneficiaryCustomerName) {
		this.beneficiaryCustomerName = beneficiaryCustomerName;
	}

	public String getInternalReference() {
		return internalReference;
	}

	public void setInternalReference(String internalReference) {
		this.internalReference = internalReference;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(Long applicantId) {
		this.applicantId = applicantId;
	}

	public Long getBeneficiaryCustomerId() {
		return beneficiaryCustomerId;
	}

	public void setBeneficiaryCustomerId(Long beneficiaryCustomerId) {
		this.beneficiaryCustomerId = beneficiaryCustomerId;
	}

	public Long getTransactionBranchId() {
		return transactionBranchId;
	}

	public void setTransactionBranchId(Long transactionBranchId) {
		this.transactionBranchId = transactionBranchId;
	}

	public Long getCurrentQueueId() {
		return currentQueueId;
	}

	public void setCurrentQueueId(Long currentQueueId) {
		this.currentQueueId = currentQueueId;
	}

	public Long getNextQueueId() {
		return nextQueueId;
	}

	public void setNextQueueId(Long nextQueueId) {
		this.nextQueueId = nextQueueId;
	}

	public Set<CancellationReason> getDeclineComments() {
		return declineComments;
	}

	public void setDeclineComments(Set<CancellationReason> declineComments) {
		this.declineComments = declineComments;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getTransactionBranchName() {
		return transactionBranchName;
	}

	public void setTransactionBranchName(String transactionBranchName) {
		this.transactionBranchName = transactionBranchName;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCurrentQueueName() {
		return currentQueueName;
	}

	public void setCurrentQueueName(String currentQueueName) {
		this.currentQueueName = currentQueueName;
	}

	public String getNextQueueName() {
		return nextQueueName;
	}

	public void setNextQueueName(String nextQueueName) {
		this.nextQueueName = nextQueueName;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Long getCurrentQueueEventId() {
		return currentQueueEventId;
	}

	public void setCurrentQueueEventId(Long currentQueueEventId) {
		this.currentQueueEventId = currentQueueEventId;
	}

	public Long getNextQueueEventId() {
		return nextQueueEventId;
	}

	public void setNextQueueEventId(Long nextQueueEventId) {
		this.nextQueueEventId = nextQueueEventId;
	}

	public Integer getNextQueueFlowSequence() {
		return nextQueueFlowSequence;
	}

	public void setNextQueueFlowSequence(Integer nextQueueFlowSequence) {
		this.nextQueueFlowSequence = nextQueueFlowSequence;
	}

	public Status getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(Status eventStatus) {
		this.eventStatus = eventStatus;
	}

	public Integer getCurrentQueueFlowSequence() {
		return currentQueueFlowSequence;
	}

	public void setCurrentQueueFlowSequence(Integer currentQueueFlowSequence) {
		this.currentQueueFlowSequence = currentQueueFlowSequence;
	}

	public boolean isFinalQueue() {
		return finalQueue;
	}

	public void setFinalQueue(boolean finalQueue) {
		this.finalQueue = finalQueue;
	}

	
	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getTransactionBranchCode() {
		return transactionBranchCode;
	}

	public void setTransactionBranchCode(String transactionBranchCode) {
		this.transactionBranchCode = transactionBranchCode;
	}

	public String getTransactionBranchNo() {
		return transactionBranchNo;
	}

	public void setTransactionBranchNo(String transactionBranchNo) {
		this.transactionBranchNo = transactionBranchNo;
	}

	public String getProductTypeCode() {
		return productTypeCode;
	}

	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}

	@Override
	public String toString() {
		return "Register [module=" + module + ", productType=" + productType + ", productName=" + productName
				+ ", product=" + product + ", productId=" + productId + ", transactionReference=" + transactionReference
				+ ", internalReference=" + internalReference + ", transactionCcy=" + transactionCcy
				+ ", transactionAmount=" + transactionAmount + ", applicant=" + applicant + ", applicantId="
				+ applicantId + ", applicantName=" + applicantName + ", beneficiaryCustomer=" + beneficiaryCustomer
				+ ", beneficiaryCustomerId=" + beneficiaryCustomerId + ", beneficiaryCustomerName="
				+ beneficiaryCustomerName + ", transactionBranch=" + transactionBranch + ", transactionBranchName="
				+ transactionBranchName + ", transactionBranchId=" + transactionBranchId + ", transactionBranchCode="
				+ transactionBranchCode + ", transactionBranchNo=" + transactionBranchNo + ", workflowReference="
				+ workflowReference + ", workflowReferenceNo=" + workflowReferenceNo + ", workflowStatus="
				+ workflowStatus + ", transactionStatus=" + transactionStatus
				+ ", transactionStatus_String=" + transactionStatus_String + ", eventStatus=" + eventStatus
				+ ", cancelReasons=" + cancelReasons + ", declineComments=" + declineComments + ", registrationDate="
				+ registrationDate + "]";
	}
	
	

}

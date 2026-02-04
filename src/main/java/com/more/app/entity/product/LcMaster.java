package com.more.app.entity.product;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.Formula;

import com.more.app.entity.AbstractPojo;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

@Entity
public class LcMaster extends AbstractPojo {
	private static final long serialVersionUID = -3559711307824157622L;

	// registerId, applicantPartyId, beneficiaryPartyId, lcCcy, lcAmount, lcEvent - Registered, lcStatus - Registered, eventStatus - idle
	
	private Long registerId;
	
	@Transient
	private Register register;
	
	@Formula("(select a.transactionReference from register a where a.id = register_Id)")
	private String lcReferenceNo;

	@Formula("(select a.internalReference from register a where a.id = register_Id)")
	private String internalReferenceNo;

	private Long applicantPartyId;
	private Long beneficiaryPartyId;

	private String lcCcy;
	private BigDecimal lcAmount;
	
	private BigDecimal utilisedAmount;
	private String utilisedCcy;
	
	private BigDecimal outstandingAmount;
	private String oustandingCcy;
	
	private BigDecimal liabilityAmount;
	private String liabilityCcy;
	
	private LocalDate bookOffDate;
	private LocalDate deactivationDate;
	private LocalDate issueDate;
	private LocalDate expiryDate;
	private LocalDate archiveDate;
	
	// idle,inprogress,pending, completed.
	private String eventStatus;
	
	// Registered, PreAdvise, Issue, Amendment, Adjustment, Payment
	private String lcEvent;
	
	// Registered, Active, Inactive, archived, bookoff
	private String lcStatus;

	public Long getRegisterId() {
		return registerId;
	}

	public void setRegisterId(Long registerId) {
		this.registerId = registerId;
	}

	public Long getApplicantPartyId() {
		return applicantPartyId;
	}

	public void setApplicantPartyId(Long applicantPartyId) {
		this.applicantPartyId = applicantPartyId;
	}

	public Long getBeneficiaryPartyId() {
		return beneficiaryPartyId;
	}

	public void setBeneficiaryPartyId(Long beneficiaryPartyId) {
		this.beneficiaryPartyId = beneficiaryPartyId;
	}

	public String getLcCcy() {
		return lcCcy;
	}

	public void setLcCcy(String lcCcy) {
		this.lcCcy = lcCcy;
	}

	public BigDecimal getLcAmount() {
		return lcAmount;
	}

	public void setLcAmount(BigDecimal lcAmount) {
		this.lcAmount = lcAmount;
	}

	public BigDecimal getUtilisedAmount() {
		return utilisedAmount;
	}

	public void setUtilisedAmount(BigDecimal utilisedAmount) {
		this.utilisedAmount = utilisedAmount;
	}

	public String getUtilisedCcy() {
		return utilisedCcy;
	}

	public void setUtilisedCcy(String utilisedCcy) {
		this.utilisedCcy = utilisedCcy;
	}

	public BigDecimal getOutstandingAmount() {
		return outstandingAmount;
	}

	public void setOutstandingAmount(BigDecimal outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}

	public String getOustandingCcy() {
		return oustandingCcy;
	}

	public void setOustandingCcy(String oustandingCcy) {
		this.oustandingCcy = oustandingCcy;
	}

	public BigDecimal getLiabilityAmount() {
		return liabilityAmount;
	}

	public void setLiabilityAmount(BigDecimal liabilityAmount) {
		this.liabilityAmount = liabilityAmount;
	}

	public String getLiabilityCcy() {
		return liabilityCcy;
	}

	public void setLiabilityCcy(String liabilityCcy) {
		this.liabilityCcy = liabilityCcy;
	}

	public LocalDate getBookOffDate() {
		return bookOffDate;
	}

	public void setBookOffDate(LocalDate bookOffDate) {
		this.bookOffDate = bookOffDate;
	}

	public LocalDate getDeactivationDate() {
		return deactivationDate;
	}

	public void setDeactivationDate(LocalDate deactivationDate) {
		this.deactivationDate = deactivationDate;
	}

	public LocalDate getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public LocalDate getArchiveDate() {
		return archiveDate;
	}

	public void setArchiveDate(LocalDate archiveDate) {
		this.archiveDate = archiveDate;
	}

	public String getLcReferenceNo() {
		return lcReferenceNo;
	}

	public void setLcReferenceNo(String lcReferenceNo) {
		this.lcReferenceNo = lcReferenceNo;
	}

	public String getInternalReferenceNo() {
		return internalReferenceNo;
	}

	public void setInternalReferenceNo(String internalReferenceNo) {
		this.internalReferenceNo = internalReferenceNo;
	}

	public String getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}

	public String getLcEvent() {
		return lcEvent;
	}

	public void setLcEvent(String lcEvent) {
		this.lcEvent = lcEvent;
	}

	public String getLcStatus() {
		return lcStatus;
	}

	public void setLcStatus(String lcStatus) {
		this.lcStatus = lcStatus;
	}

	public Register getRegister() {
		return register;
	}

	public void setRegister(Register register) {
		this.register = register;
	}
	
}

package com.more.app.entity.product.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LcMasterSearchDTO {

    private Long registerId;
    private String lcReferenceNo;
    private String internalReferenceNo;

    private Long applicantPartyId;
    private String applicantName;

    private Long beneficiaryPartyId;
    private String beneficiaryName;

    private String lcCcy;

    private BigDecimal lcAmountFrom;
    private BigDecimal lcAmountTo;

    private LocalDate issueDateFrom;
    private LocalDate issueDateTo;
    
    private LocalDate fromCreateDate;
    private LocalDate toCreateDate;

    private LocalDate expiryDateFrom;
    private LocalDate expiryDateTo;

    private String eventStatus;
    private String lcEvent;
    private String lcStatus;
	public Long getRegisterId() {
		return registerId;
	}
	public void setRegisterId(Long registerId) {
		this.registerId = registerId;
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
	public Long getApplicantPartyId() {
		return applicantPartyId;
	}
	public void setApplicantPartyId(Long applicantPartyId) {
		this.applicantPartyId = applicantPartyId;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public Long getBeneficiaryPartyId() {
		return beneficiaryPartyId;
	}
	public void setBeneficiaryPartyId(Long beneficiaryPartyId) {
		this.beneficiaryPartyId = beneficiaryPartyId;
	}
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	public String getLcCcy() {
		return lcCcy;
	}
	public void setLcCcy(String lcCcy) {
		this.lcCcy = lcCcy;
	}
	public BigDecimal getLcAmountFrom() {
		return lcAmountFrom;
	}
	public void setLcAmountFrom(BigDecimal lcAmountFrom) {
		this.lcAmountFrom = lcAmountFrom;
	}
	public BigDecimal getLcAmountTo() {
		return lcAmountTo;
	}
	public void setLcAmountTo(BigDecimal lcAmountTo) {
		this.lcAmountTo = lcAmountTo;
	}
	public LocalDate getIssueDateFrom() {
		return issueDateFrom;
	}
	public void setIssueDateFrom(LocalDate issueDateFrom) {
		this.issueDateFrom = issueDateFrom;
	}
	public LocalDate getIssueDateTo() {
		return issueDateTo;
	}
	public void setIssueDateTo(LocalDate issueDateTo) {
		this.issueDateTo = issueDateTo;
	}
	public LocalDate getExpiryDateFrom() {
		return expiryDateFrom;
	}
	public void setExpiryDateFrom(LocalDate expiryDateFrom) {
		this.expiryDateFrom = expiryDateFrom;
	}
	public LocalDate getExpiryDateTo() {
		return expiryDateTo;
	}
	public void setExpiryDateTo(LocalDate expiryDateTo) {
		this.expiryDateTo = expiryDateTo;
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
	public LocalDate getFromCreateDate() {
		return fromCreateDate;
	}
	public void setFromCreateDate(LocalDate fromCreateDate) {
		this.fromCreateDate = fromCreateDate;
	}
	public LocalDate getToCreateDate() {
		return toCreateDate;
	}
	public void setToCreateDate(LocalDate toCreateDate) {
		this.toCreateDate = toCreateDate;
	}
	
	
}

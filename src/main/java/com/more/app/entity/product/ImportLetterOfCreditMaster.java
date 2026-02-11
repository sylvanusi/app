package com.more.app.entity.product;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.more.app.entity.AbstractPojo;
import com.more.app.util.annotations.UIAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;

@Entity
public class ImportLetterOfCreditMaster  extends AbstractPojo{

	private static final long serialVersionUID = 658359462247364756L;

	private Long masterId;
	
	@Column(length = 11)
	@UIAction(label = "Sender BIC")
	private String senderBic;

	@Column(length = 11)
	@UIAction(label = "Receiver BIC")
	private String receiverBic;

	@Column(length = 24)
	@UIAction(label = "Form of Documentary Credit")
	private String formOfDocumentaryCr;

	@Column(length = 16)
	@UIAction(label = "Documentary Credit No")
	private String documentaryCrNo;

	
	@Column(length = 16)
	@UIAction(label = "Pre-Advice Reference")
	private String referenceToPreAdvice;


	@UIAction(label = "Issue Date")
	private LocalDate dateOfIssue;

	@Column(length = 30)
	@UIAction(label = "Applicable Rules")
	private String applicableRules;
	
	@Column(length = 35)
	@UIAction(label = "Applicable Rules Narrative")
	private String applicableRulesNarrative;

	@UIAction(label = "Expiry Date")
	private LocalDate dateOfExpiry;
	
	@Column(length = 29)
	@UIAction(label = "Place of Expiry")
	private String placeOfExpiry;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName = "id")
	@UIAction(label = "Applicant Bank")	
	private Party applicantBank;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName = "id")
	@UIAction(label = "Applicant")
	private Party applicant;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName = "id")
	@UIAction(label = "Beneficiary")
	private Party beneficiary;

	@Column(length = 3)
	@UIAction(label = "Currency")
	private String currencyCode;
	
	@Column(precision = 15,scale=2)
	@UIAction(label = "LC Amount")
	private BigDecimal amountOfDocumentaryCredit;

	@Column(precision = 2,scale=0)
	@UIAction(label = "Tolerance (+)")
	private int tolerance1;
	
	@Column(precision = 2,scale=0)
	@UIAction(label = "Tolerance (-)")
	private int tolerance2;

	@Column(length = 140)
	@UIAction(label = "Additional Amounts Covered")
	private String additionalAmountsCovered;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName = "id")
	@UIAction(label = "Available With ... By ...")
	private Party availableWithBy;

	@Column(length = 140)
	@UIAction(label = "Drafts At")
	private String draftsAt;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName = "id")
	@UIAction(label = "Drawee")
	private Party drawee;

	@Column(length = 140)
	@UIAction(label = "Mixed Payment")
	private String mixedPaymentDetails;

	@Column(length = 140)
	@UIAction(label = "Negotiation / Deffered Payment")
	private String negotiationDeferredPaymentDetails;

	@Column(length = 11)
	@UIAction(label = "Partial Shipments")
	private String partialShipments;

	@Column(length = 11)
	@UIAction(label = "Transhipments")
	private String transhipments;

	@Column(length = 65)
	@UIAction(label = "Dispatch From")
	private String dispatchFrom;

	@Column(length = 65)
	@UIAction(label = "Port of Loading")
	private String portOfLoading;

	@Column(length = 65)
	@UIAction(label = "Port of Discharge")
	private String portOfDischarge;

	@Column(length = 65)
	@UIAction(label = "Final Destination")
	private String placeOfFinalDestination;


	@UIAction(label = "Latest Shipment Date")
	private LocalDate latestDateOfShipment;


	@Lob
	@Column(nullable = true)
	@UIAction(label = "Shipment Period")
	private String shipmentPeriod;


	@Lob
	@Column(nullable = true)
	@UIAction(label = "Goods Description")
	private String goodDesription;


	@Lob
	@Column(nullable = true)
	@UIAction(label = "Documents Required")
	private String documentsRequired;


	@Lob
	@Column(nullable = true)
	@UIAction(label = "Additional Conditions")
	private String additionalConditions;


	@Lob
	@Column(nullable = true)
	@UIAction(label = "Payment Conditions (Beneficiary)")
	private String specialPaymentConditionBeneficiary;


	@Lob
	@Column(nullable = true)
	@UIAction(label = "Payment Conditions (Receiving Bank)")
	private String specialPaymentConditionReceivingBank;


	@Lob
	@Column(nullable = true)
	@UIAction(label = "Charges")
	private String charges;
	
	@Column(length = 3)
	@UIAction(label = "Presentation Period")
	private int periodOfPresentationDays;
	
	@Column(length = 35)
	@UIAction(label = "Narrative")
	private String periodOfPresentationNarrative;

	@Column(length = 7)
	@UIAction(label = "Confirmation Instructions")
	private String confirmationInstructions;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName = "id")
	@UIAction(label = "Requested Confirmation Party")
	private Party requestedConfirmationParty;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName = "id")
	@UIAction(label = "Reimbursing Bank")
	private Party reimbursingBankParty;

	@Lob
	@Column(nullable = true)
	@UIAction(label = "Instructions To Paying Bank")
	private String instructionsToPayBank;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName = "id")
	@UIAction(label = "Advise Through Bank")
	private Party advThrBankParty;

	@Lob
	@Column(nullable = true)
	@UIAction(label = "Sender To Receiver Info")
	private String senderToReceiverInfo;

	public Long getMasterId() {
		return masterId;
	}

	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}

	public String getSenderBic() {
		return senderBic;
	}

	public void setSenderBic(String senderBic) {
		this.senderBic = senderBic;
	}

	public String getReceiverBic() {
		return receiverBic;
	}

	public void setReceiverBic(String receiverBic) {
		this.receiverBic = receiverBic;
	}

	public String getFormOfDocumentaryCr() {
		return formOfDocumentaryCr;
	}

	public void setFormOfDocumentaryCr(String formOfDocumentaryCr) {
		this.formOfDocumentaryCr = formOfDocumentaryCr;
	}

	public String getDocumentaryCrNo() {
		return documentaryCrNo;
	}

	public void setDocumentaryCrNo(String documentaryCrNo) {
		this.documentaryCrNo = documentaryCrNo;
	}

	public String getReferenceToPreAdvice() {
		return referenceToPreAdvice;
	}

	public void setReferenceToPreAdvice(String referenceToPreAdvice) {
		this.referenceToPreAdvice = referenceToPreAdvice;
	}

	public LocalDate getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(LocalDate dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public String getApplicableRules() {
		return applicableRules;
	}

	public void setApplicableRules(String applicableRules) {
		this.applicableRules = applicableRules;
	}

	public String getApplicableRulesNarrative() {
		return applicableRulesNarrative;
	}

	public void setApplicableRulesNarrative(String applicableRulesNarrative) {
		this.applicableRulesNarrative = applicableRulesNarrative;
	}

	public LocalDate getDateOfExpiry() {
		return dateOfExpiry;
	}

	public void setDateOfExpiry(LocalDate dateOfExpiry) {
		this.dateOfExpiry = dateOfExpiry;
	}

	public String getPlaceOfExpiry() {
		return placeOfExpiry;
	}

	public void setPlaceOfExpiry(String placeOfExpiry) {
		this.placeOfExpiry = placeOfExpiry;
	}

	public Party getApplicantBank() {
		return applicantBank;
	}

	public void setApplicantBank(Party applicantBank) {
		this.applicantBank = applicantBank;
	}

	public Party getApplicant() {
		return applicant;
	}

	public void setApplicant(Party applicant) {
		this.applicant = applicant;
	}

	public Party getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(Party beneficiary) {
		this.beneficiary = beneficiary;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getAmountOfDocumentaryCredit() {
		return amountOfDocumentaryCredit;
	}

	public void setAmountOfDocumentaryCredit(BigDecimal amountOfDocumentaryCredit) {
		this.amountOfDocumentaryCredit = amountOfDocumentaryCredit;
	}

	public int getTolerance1() {
		return tolerance1;
	}

	public void setTolerance1(int tolerance1) {
		this.tolerance1 = tolerance1;
	}

	public int getTolerance2() {
		return tolerance2;
	}

	public void setTolerance2(int tolerance2) {
		this.tolerance2 = tolerance2;
	}

	public String getAdditionalAmountsCovered() {
		return additionalAmountsCovered;
	}

	public void setAdditionalAmountsCovered(String additionalAmountsCovered) {
		this.additionalAmountsCovered = additionalAmountsCovered;
	}

	public Party getAvailableWithBy() {
		return availableWithBy;
	}

	public void setAvailableWithBy(Party availableWithBy) {
		this.availableWithBy = availableWithBy;
	}

	public String getDraftsAt() {
		return draftsAt;
	}

	public void setDraftsAt(String draftsAt) {
		this.draftsAt = draftsAt;
	}

	public Party getDrawee() {
		return drawee;
	}

	public void setDrawee(Party drawee) {
		this.drawee = drawee;
	}

	public String getMixedPaymentDetails() {
		return mixedPaymentDetails;
	}

	public void setMixedPaymentDetails(String mixedPaymentDetails) {
		this.mixedPaymentDetails = mixedPaymentDetails;
	}

	public String getNegotiationDeferredPaymentDetails() {
		return negotiationDeferredPaymentDetails;
	}

	public void setNegotiationDeferredPaymentDetails(String negotiationDeferredPaymentDetails) {
		this.negotiationDeferredPaymentDetails = negotiationDeferredPaymentDetails;
	}

	public String getPartialShipments() {
		return partialShipments;
	}

	public void setPartialShipments(String partialShipments) {
		this.partialShipments = partialShipments;
	}

	public String getTranshipments() {
		return transhipments;
	}

	public void setTranshipments(String transhipments) {
		this.transhipments = transhipments;
	}

	public String getDispatchFrom() {
		return dispatchFrom;
	}

	public void setDispatchFrom(String dispatchFrom) {
		this.dispatchFrom = dispatchFrom;
	}

	public String getPortOfLoading() {
		return portOfLoading;
	}

	public void setPortOfLoading(String portOfLoading) {
		this.portOfLoading = portOfLoading;
	}

	public String getPortOfDischarge() {
		return portOfDischarge;
	}

	public void setPortOfDischarge(String portOfDischarge) {
		this.portOfDischarge = portOfDischarge;
	}

	public String getPlaceOfFinalDestination() {
		return placeOfFinalDestination;
	}

	public void setPlaceOfFinalDestination(String placeOfFinalDestination) {
		this.placeOfFinalDestination = placeOfFinalDestination;
	}

	public LocalDate getLatestDateOfShipment() {
		return latestDateOfShipment;
	}

	public void setLatestDateOfShipment(LocalDate latestDateOfShipment) {
		this.latestDateOfShipment = latestDateOfShipment;
	}

	public String getShipmentPeriod() {
		return shipmentPeriod;
	}

	public void setShipmentPeriod(String shipmentPeriod) {
		this.shipmentPeriod = shipmentPeriod;
	}

	public String getGoodDesription() {
		return goodDesription;
	}

	public void setGoodDesription(String goodDesription) {
		this.goodDesription = goodDesription;
	}

	public String getDocumentsRequired() {
		return documentsRequired;
	}

	public void setDocumentsRequired(String documentsRequired) {
		this.documentsRequired = documentsRequired;
	}

	public String getAdditionalConditions() {
		return additionalConditions;
	}

	public void setAdditionalConditions(String additionalConditions) {
		this.additionalConditions = additionalConditions;
	}

	public String getSpecialPaymentConditionBeneficiary() {
		return specialPaymentConditionBeneficiary;
	}

	public void setSpecialPaymentConditionBeneficiary(String specialPaymentConditionBeneficiary) {
		this.specialPaymentConditionBeneficiary = specialPaymentConditionBeneficiary;
	}

	public String getSpecialPaymentConditionReceivingBank() {
		return specialPaymentConditionReceivingBank;
	}

	public void setSpecialPaymentConditionReceivingBank(String specialPaymentConditionReceivingBank) {
		this.specialPaymentConditionReceivingBank = specialPaymentConditionReceivingBank;
	}

	public String getCharges() {
		return charges;
	}

	public void setCharges(String charges) {
		this.charges = charges;
	}

	public int getPeriodOfPresentationDays() {
		return periodOfPresentationDays;
	}

	public void setPeriodOfPresentationDays(int periodOfPresentationDays) {
		this.periodOfPresentationDays = periodOfPresentationDays;
	}

	public String getPeriodOfPresentationNarrative() {
		return periodOfPresentationNarrative;
	}

	public void setPeriodOfPresentationNarrative(String periodOfPresentationNarrative) {
		this.periodOfPresentationNarrative = periodOfPresentationNarrative;
	}

	public String getConfirmationInstructions() {
		return confirmationInstructions;
	}

	public void setConfirmationInstructions(String confirmationInstructions) {
		this.confirmationInstructions = confirmationInstructions;
	}

	public Party getRequestedConfirmationParty() {
		return requestedConfirmationParty;
	}

	public void setRequestedConfirmationParty(Party requestedConfirmationParty) {
		this.requestedConfirmationParty = requestedConfirmationParty;
	}

	public Party getReimbursingBankParty() {
		return reimbursingBankParty;
	}

	public void setReimbursingBankParty(Party reimbursingBankParty) {
		this.reimbursingBankParty = reimbursingBankParty;
	}

	public String getInstructionsToPayBank() {
		return instructionsToPayBank;
	}

	public void setInstructionsToPayBank(String instructionsToPayBank) {
		this.instructionsToPayBank = instructionsToPayBank;
	}

	public Party getAdvThrBankParty() {
		return advThrBankParty;
	}

	public void setAdvThrBankParty(Party advThrBankParty) {
		this.advThrBankParty = advThrBankParty;
	}

	public String getSenderToReceiverInfo() {
		return senderToReceiverInfo;
	}

	public void setSenderToReceiverInfo(String senderToReceiverInfo) {
		this.senderToReceiverInfo = senderToReceiverInfo;
	}
	
	
	

}

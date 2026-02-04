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
public class ImportLetterOfCreditIssue  extends AbstractPojo
{
	private Long lcMasterId;
	
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


	/**
	 * @return the senderBic
	 */
	public String getSenderBic()
	{
		return senderBic;
	}

	/**
	 * @return the receiverBic
	 */
	public String getReceiverBic()
	{
		return receiverBic;
	}

	/**
	 * @return the formOfDocumentaryCr
	 */
	public String getFormOfDocumentaryCr()
	{
		return formOfDocumentaryCr;
	}

	/**
	 * @return the documentaryCrNo
	 */
	public String getDocumentaryCrNo()
	{
		return documentaryCrNo;
	}

	/**
	 * @return the referenceToPreAdvice
	 */
	public String getReferenceToPreAdvice()
	{
		return referenceToPreAdvice;
	}

	/**
	 * @return the dateOfIssue
	 */
	public LocalDate getDateOfIssue()
	{
		return dateOfIssue;
	}

	/**
	 * @return the applicableRules
	 */
	public String getApplicableRules()
	{
		return applicableRules;
	}

	/**
	 * @return the applicableRulesNarrative
	 */
	public String getApplicableRulesNarrative()
	{
		return applicableRulesNarrative;
	}

	/**
	 * @return the dateOfExpiry
	 */
	public LocalDate getDateOfExpiry()
	{
		return dateOfExpiry;
	}

	/**
	 * @return the placeOfExpiry
	 */
	public String getPlaceOfExpiry()
	{
		return placeOfExpiry;
	}

	/**
	 * @return the applicantBank
	 */
	public Party getApplicantBank()
	{
		return applicantBank;
	}

	/**
	 * @return the applicant
	 */
	public Party getApplicant()
	{
		return applicant;
	}

	/**
	 * @return the beneficiary
	 */
	public Party getBeneficiary()
	{
		return beneficiary;
	}

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode()
	{
		return currencyCode;
	}

	/**
	 * @return the amountOfDocumentaryCredit
	 */
	public BigDecimal getAmountOfDocumentaryCredit()
	{
		return amountOfDocumentaryCredit;
	}

	/**
	 * @return the tolerance1
	 */
	public int getTolerance1()
	{
		return tolerance1;
	}

	/**
	 * @return the tolerance2
	 */
	public int getTolerance2()
	{
		return tolerance2;
	}

	/**
	 * @return the additionalAmountsCovered
	 */
	public String getAdditionalAmountsCovered()
	{
		return additionalAmountsCovered;
	}

	/**
	 * @return the availableWithBy
	 */
	public Party getAvailableWithBy()
	{
		return availableWithBy;
	}

	/**
	 * @return the draftsAt
	 */
	public String getDraftsAt()
	{
		return draftsAt;
	}

	/**
	 * @return the drawee
	 */
	public Party getDrawee()
	{
		return drawee;
	}

	/**
	 * @return the mixedPaymentDetails
	 */
	public String getMixedPaymentDetails()
	{
		return mixedPaymentDetails;
	}

	/**
	 * @return the negotiationDeferredPaymentDetails
	 */
	public String getNegotiationDeferredPaymentDetails()
	{
		return negotiationDeferredPaymentDetails;
	}

	/**
	 * @return the partialShipments
	 */
	public String getPartialShipments()
	{
		return partialShipments;
	}

	/**
	 * @return the transhipments
	 */
	public String getTranshipments()
	{
		return transhipments;
	}

	/**
	 * @return the dispatchFrom
	 */
	public String getDispatchFrom()
	{
		return dispatchFrom;
	}

	/**
	 * @return the portOfLoading
	 */
	public String getPortOfLoading()
	{
		return portOfLoading;
	}

	/**
	 * @return the portOfDischarge
	 */
	public String getPortOfDischarge()
	{
		return portOfDischarge;
	}

	/**
	 * @return the placeOfFinalDestination
	 */
	public String getPlaceOfFinalDestination()
	{
		return placeOfFinalDestination;
	}

	/**
	 * @return the latestDateOfShipment
	 */
	public LocalDate getLatestDateOfShipment()
	{
		return latestDateOfShipment;
	}

	/**
	 * @return the shipmentPeriod
	 */
	public String getShipmentPeriod()
	{
		return shipmentPeriod;
	}

	/**
	 * @return the goodDesription
	 */
	public String getGoodDesription()
	{
		return goodDesription;
	}

	/**
	 * @return the documentsRequired
	 */
	public String getDocumentsRequired()
	{
		return documentsRequired;
	}

	/**
	 * @return the additionalConditions
	 */
	public String getAdditionalConditions()
	{
		return additionalConditions;
	}

	/**
	 * @return the specialPaymentConditionBeneficiary
	 */
	public String getSpecialPaymentConditionBeneficiary()
	{
		return specialPaymentConditionBeneficiary;
	}

	/**
	 * @return the specialPaymentConditionApplicant
	 */
	public String getSpecialPaymentConditionReceivingBank()
	{
		return specialPaymentConditionReceivingBank;
	}

	/**
	 * @return the charges
	 */
	public String getCharges()
	{
		return charges;
	}

	/**
	 * @return the periodOfPresentationDays
	 */
	public int getPeriodOfPresentationDays()
	{
		return periodOfPresentationDays;
	}

	/**
	 * @return the periodOfPresentationNarrative
	 */
	public String getPeriodOfPresentationNarrative()
	{
		return periodOfPresentationNarrative;
	}

	/**
	 * @return the confirmationInstructions
	 */
	public String getConfirmationInstructions()
	{
		return confirmationInstructions;
	}

	/**
	 * @return the requestedConfirmationParty
	 */
	public Party getRequestedConfirmationParty()
	{
		return requestedConfirmationParty;
	}

	/**
	 * @return the reimbursingBankParty
	 */
	public Party getReimbursingBankParty()
	{
		return reimbursingBankParty;
	}

	/**
	 * @return the instructionsToPayBank
	 */
	public String getInstructionsToPayBank()
	{
		return instructionsToPayBank;
	}

	/**
	 * @return the advThrBankParty
	 */
	public Party getAdvThrBankParty()
	{
		return advThrBankParty;
	}

	/**
	 * @return the senderToReceiverInfo
	 */
	public String getSenderToReceiverInfo()
	{
		return senderToReceiverInfo;
	}


	/**
	 * @param senderBic the senderBic to set
	 */
	public void setSenderBic(String senderBic)
	{
		this.senderBic = senderBic;
	}

	/**
	 * @param receiverBic the receiverBic to set
	 */
	public void setReceiverBic(String receiverBic)
	{
		this.receiverBic = receiverBic;
	}

	/**
	 * @param formOfDocumentaryCr the formOfDocumentaryCr to set
	 */
	public void setFormOfDocumentaryCr(String formOfDocumentaryCr)
	{
		this.formOfDocumentaryCr = formOfDocumentaryCr;
	}

	/**
	 * @param documentaryCrNo the documentaryCrNo to set
	 */
	public void setDocumentaryCrNo(String documentaryCrNo)
	{
		this.documentaryCrNo = documentaryCrNo;
	}

	/**
	 * @param referenceToPreAdvice the referenceToPreAdvice to set
	 */
	public void setReferenceToPreAdvice(String referenceToPreAdvice)
	{
		this.referenceToPreAdvice = referenceToPreAdvice;
	}

	/**
	 * @param dateOfIssue the dateOfIssue to set
	 */
	public void setDateOfIssue(LocalDate dateOfIssue)
	{
		this.dateOfIssue = dateOfIssue;
	}

	/**
	 * @param applicableRules the applicableRules to set
	 */
	public void setApplicableRules(String applicableRules)
	{
		this.applicableRules = applicableRules;
	}

	/**
	 * @param applicableRulesNarrative the applicableRulesNarrative to set
	 */
	public void setApplicableRulesNarrative(String applicableRulesNarrative)
	{
		this.applicableRulesNarrative = applicableRulesNarrative;
	}

	/**
	 * @param dateOfExpiry the dateOfExpiry to set
	 */
	public void setDateOfExpiry(LocalDate dateOfExpiry)
	{
		this.dateOfExpiry = dateOfExpiry;
	}

	/**
	 * @param placeOfExpiry the placeOfExpiry to set
	 */
	public void setPlaceOfExpiry(String placeOfExpiry)
	{
		this.placeOfExpiry = placeOfExpiry;
	}

	/**
	 * @param applicantBank the applicantBank to set
	 */
	public void setApplicantBank(Party applicantBank)
	{
		this.applicantBank = applicantBank;
	}

	/**
	 * @param applicant the applicant to set
	 */
	public void setApplicant(Party applicant)
	{
		this.applicant = applicant;
	}

	/**
	 * @param beneficiary the beneficiary to set
	 */
	public void setBeneficiary(Party beneficiary)
	{
		this.beneficiary = beneficiary;
	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode)
	{
		this.currencyCode = currencyCode;
	}

	/**
	 * @param amountOfDocumentaryCredit the amountOfDocumentaryCredit to set
	 */
	public void setAmountOfDocumentaryCredit(BigDecimal amountOfDocumentaryCredit)
	{
		this.amountOfDocumentaryCredit = amountOfDocumentaryCredit;
	}

	/**
	 * @param tolerance1 the tolerance1 to set
	 */
	public void setTolerance1(int tolerance1)
	{
		this.tolerance1 = tolerance1;
	}

	/**
	 * @param tolerance2 the tolerance2 to set
	 */
	public void setTolerance2(int tolerance2)
	{
		this.tolerance2 = tolerance2;
	}

	/**
	 * @param additionalAmountsCovered the additionalAmountsCovered to set
	 */
	public void setAdditionalAmountsCovered(String additionalAmountsCovered)
	{
		this.additionalAmountsCovered = additionalAmountsCovered;
	}

	/**
	 * @param availableWithBy the availableWithBy to set
	 */
	public void setAvailableWithBy(Party availableWithBy)
	{
		this.availableWithBy = availableWithBy;
	}

	/**
	 * @param draftsAt the draftsAt to set
	 */
	public void setDraftsAt(String draftsAt)
	{
		this.draftsAt = draftsAt;
	}

	/**
	 * @param drawee the drawee to set
	 */
	public void setDrawee(Party drawee)
	{
		this.drawee = drawee;
	}

	/**
	 * @param mixedPaymentDetails the mixedPaymentDetails to set
	 */
	public void setMixedPaymentDetails(String mixedPaymentDetails)
	{
		this.mixedPaymentDetails = mixedPaymentDetails;
	}

	/**
	 * @param negotiationDeferredPaymentDetails the negotiationDeferredPaymentDetails to set
	 */
	public void setNegotiationDeferredPaymentDetails(String negotiationDeferredPaymentDetails)
	{
		this.negotiationDeferredPaymentDetails = negotiationDeferredPaymentDetails;
	}

	/**
	 * @param partialShipments the partialShipments to set
	 */
	public void setPartialShipments(String partialShipments)
	{
		this.partialShipments = partialShipments;
	}

	/**
	 * @param transhipments the transhipments to set
	 */
	public void setTranshipments(String transhipments)
	{
		this.transhipments = transhipments;
	}

	/**
	 * @param dispatchFrom the dispatchFrom to set
	 */
	public void setDispatchFrom(String dispatchFrom)
	{
		this.dispatchFrom = dispatchFrom;
	}

	/**
	 * @param portOfLoading the portOfLoading to set
	 */
	public void setPortOfLoading(String portOfLoading)
	{
		this.portOfLoading = portOfLoading;
	}

	/**
	 * @param portOfDischarge the portOfDischarge to set
	 */
	public void setPortOfDischarge(String portOfDischarge)
	{
		this.portOfDischarge = portOfDischarge;
	}

	/**
	 * @param placeOfFinalDestination the placeOfFinalDestination to set
	 */
	public void setPlaceOfFinalDestination(String placeOfFinalDestination)
	{
		this.placeOfFinalDestination = placeOfFinalDestination;
	}

	/**
	 * @param latestDateOfShipment the latestDateOfShipment to set
	 */
	public void setLatestDateOfShipment(LocalDate latestDateOfShipment)
	{
		this.latestDateOfShipment = latestDateOfShipment;
	}

	/**
	 * @param shipmentPeriod the shipmentPeriod to set
	 */
	public void setShipmentPeriod(String shipmentPeriod)
	{
		this.shipmentPeriod = shipmentPeriod;
	}

	/**
	 * @param goodDesription the goodDesription to set
	 */
	public void setGoodDesription(String goodDesription)
	{
		this.goodDesription = goodDesription;
	}

	/**
	 * @param documentsRequired the documentsRequired to set
	 */
	public void setDocumentsRequired(String documentsRequired)
	{
		this.documentsRequired = documentsRequired;
	}

	/**
	 * @param additionalConditions the additionalConditions to set
	 */
	public void setAdditionalConditions(String additionalConditions)
	{
		this.additionalConditions = additionalConditions;
	}

	/**
	 * @param specialPaymentConditionBeneficiary the specialPaymentConditionBeneficiary to set
	 */
	public void setSpecialPaymentConditionBeneficiary(String specialPaymentConditionBeneficiary)
	{
		this.specialPaymentConditionBeneficiary = specialPaymentConditionBeneficiary;
	}

	/**
	 * @param specialPaymentConditionApplicant the specialPaymentConditionApplicant to set
	 */
	public void setSpecialPaymentConditionReceivingBank(String specialPaymentConditionReceivingBank)
	{
		this.specialPaymentConditionReceivingBank = specialPaymentConditionReceivingBank;
	}

	/**
	 * @param charges the charges to set
	 */
	public void setCharges(String charges)
	{
		this.charges = charges;
	}

	/**
	 * @param periodOfPresentationDays the periodOfPresentationDays to set
	 */
	public void setPeriodOfPresentationDays(int periodOfPresentationDays)
	{
		this.periodOfPresentationDays = periodOfPresentationDays;
	}

	/**
	 * @param periodOfPresentationNarrative the periodOfPresentationNarrative to set
	 */
	public void setPeriodOfPresentationNarrative(String periodOfPresentationNarrative)
	{
		this.periodOfPresentationNarrative = periodOfPresentationNarrative;
	}

	/**
	 * @param confirmationInstructions the confirmationInstructions to set
	 */
	public void setConfirmationInstructions(String confirmationInstructions)
	{
		this.confirmationInstructions = confirmationInstructions;
	}

	/**
	 * @param requestedConfirmationParty the requestedConfirmationParty to set
	 */
	public void setRequestedConfirmationParty(Party requestedConfirmationParty)
	{
		this.requestedConfirmationParty = requestedConfirmationParty;
	}

	/**
	 * @param reimbursingBankParty the reimbursingBankParty to set
	 */
	public void setReimbursingBankParty(Party reimbursingBankParty)
	{
		this.reimbursingBankParty = reimbursingBankParty;
	}

	/**
	 * @param instructionsToPayBank the instructionsToPayBank to set
	 */
	public void setInstructionsToPayBank(String instructionsToPayBank)
	{
		this.instructionsToPayBank = instructionsToPayBank;
	}

	/**
	 * @param advThrBankParty the advThrBankParty to set
	 */
	public void setAdvThrBankParty(Party advThrBankParty)
	{
		this.advThrBankParty = advThrBankParty;
	}

	/**
	 * @param senderToReceiverInfo the senderToReceiverInfo to set
	 */
	public void setSenderToReceiverInfo(String senderToReceiverInfo)
	{
		this.senderToReceiverInfo = senderToReceiverInfo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "ImportLetterOfCreditIssue [id=" + id + ", senderBic=" + senderBic + ", receiverBic=" + receiverBic
				+ ", formOfDocumentaryCr=" + formOfDocumentaryCr + ", documentaryCrNo=" + documentaryCrNo
				+ ", referenceToPreAdvice=" + referenceToPreAdvice + ", dateOfIssue=" + dateOfIssue
				+ ", applicableRules=" + applicableRules + ", applicableRulesNarrative=" + applicableRulesNarrative
				+ ", dateOfExpiry=" + dateOfExpiry + ", placeOfExpiry=" + placeOfExpiry + ", applicantBank="
				+ applicantBank + ", applicant=" + applicant + ", beneficiary=" + beneficiary + ", currencyCode="
				+ currencyCode + ", amountOfDocumentaryCredit=" + amountOfDocumentaryCredit + ", tolerance1="
				+ tolerance1 + ", tolerance2=" + tolerance2 + ", additionalAmountsCovered=" + additionalAmountsCovered
				+ ", availableWithBy=" + availableWithBy + ", draftsAt=" + draftsAt + ", drawee=" + drawee
				+ ", mixedPaymentDetails=" + mixedPaymentDetails + ", negotiationDeferredPaymentDetails="
				+ negotiationDeferredPaymentDetails + ", partialShipments=" + partialShipments + ", transhipments="
				+ transhipments + ", dispatchFrom=" + dispatchFrom + ", portOfLoading=" + portOfLoading
				+ ", portOfDischarge=" + portOfDischarge + ", placeOfFinalDestination=" + placeOfFinalDestination
				+ ", latestDateOfShipment=" + latestDateOfShipment + ", shipmentPeriod=" + shipmentPeriod
				+ ", goodDesription=" + goodDesription + ", documentsRequired=" + documentsRequired
				+ ", additionalConditions=" + additionalConditions + ", specialPaymentConditionBeneficiary="
				+ specialPaymentConditionBeneficiary + ", specialPaymentConditionReceivingBank="
				+ specialPaymentConditionReceivingBank + ", charges=" + charges + ", periodOfPresentationDays="
				+ periodOfPresentationDays + ", periodOfPresentationNarrative=" + periodOfPresentationNarrative
				+ ", confirmationInstructions=" + confirmationInstructions + ", requestedConfirmationParty="
				+ requestedConfirmationParty + ", reimbursingBankParty=" + reimbursingBankParty
				+ ", instructionsToPayBank=" + instructionsToPayBank + ", advThrBankParty=" + advThrBankParty
				+ ", senderToReceiverInfo=" + senderToReceiverInfo + "]";
	}

}

package com.more.app.entity.product;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.Formula;

import com.more.app.entity.AbstractPojo;
import com.more.app.util.annotations.UIAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.Transient;

@Entity
public class ImportLetterOfCreditPreAdvise extends AbstractPojo {

	private static final long serialVersionUID = 1L;

	private Long lcMasterId;	
	@Transient
	private Register register;
	@Transient
	private LcMaster lcMaster;

	@Column(length = 11)
	@UIAction(label = "Sender BIC")
	private String senderBic;

	@Column(length = 11)
	@UIAction(label = "Receiver BIC")
	private String receiverBic;

	// swift field 40A component
	@Column(length = 24)
	@UIAction(label = "Form of Documentary Credit")
	private String formOfDocumentaryCr;

	// swift field 20 component
	@Column(length = 16)
	@UIAction(label = "Documentary Credit No")
	private String documentaryCrNo;

	// swift field 31D component
	@UIAction(label = "Expiry Date")
	private LocalDate dateOfExpiry;

	@Column(length = 29)
	@UIAction(label = "Place of Expiry")
	private String placeOfExpiry;
	

	// swift field 50 component
	@JoinColumn(referencedColumnName = "id")
	@UIAction(label = "Applicant")
	private Long applicantPartyId;

	// swift field 50 component
	@UIAction(label = "Applicant")
	@Formula("(select a.name from Party a where a.id = applicant_Party_Id)")
	private String applicantName;

	@Transient
	private Party applicant;

	// swift field 59 components
	@JoinColumn(referencedColumnName = "id")
	@UIAction(label = "Beneficiary")
	private Long beneficiaryPartyId;

	@UIAction(label = "Beneficiary")
	@Formula("(select a.name from Party a where a.id = beneficiary_Party_Id)")
	private String beneficiaryName;

	@Transient
	private Party beneficiary;
	
	@Column(length = 11)
	private String advisingBankBicCode;
	
	@JoinColumn(referencedColumnName = "id")
	@UIAction(label = "Beneficiary")
	private Long advisingBankPartyId;
	
	@UIAction(label = "Beneficiary")
	@Formula("(select a.name from Party a where a.id = advising_Bank_Party_Id)")
	private String advisingBankName;
	
	@Transient
	private Party advisingBank;
	
	@Column(length = 2)
	private String beneficiaryCountry;

	// swift field 32B components
	@Column(length = 3)
	@UIAction(label = "Currency")
	private String currencyCode;

	@Column(precision = 15, scale = 2)
	@UIAction(label = "LC Amount")
	private BigDecimal amountOfDocumentaryCredit;

	// swift field 39A components
	@Column(precision = 2, scale = 0)
	@UIAction(label = "Tolerance (+)")
	private Double tolerancePlus;

	@Column(precision = 2, scale = 0)
	@UIAction(label = "Tolerance (-)")
	private Double toleranceMinus;

	// swift field 39C component
	@Column(length = 140)
	@UIAction(label = "Additional Amounts Covered")
	private String additionalAmountsCovered;

	@Column(length = 14)
	private String availableWithByCode;
	@Column(length = 11)
	private String availableWithByBic;
	@Column(length = 35)
	private String availableWithByName;
	@Column(length = 35)
	private String availableWithByAddress1;
	@Column(length = 35)
	private String availableWithByAddress2;
	@Column(length = 35)
	private String availableWithByAddress3;
	
	// availableWithByCode, availableWithByBic, availableWithByName, availableWithByAddress1, availableWithByAddress2, availableWithByAddress3

	// swift field 44A component
	@Column(length = 140)
	@UIAction(label = "Dispatch From")
	private String dispatchFrom;

	// swift field 44E component
	@Column(length = 140)
	@UIAction(label = "Port of Loading")
	private String portOfLoading;

	// swift field 44F component
	@Column(length = 140)
	@UIAction(label = "Port of Discharge")
	private String portOfDischarge;

	// swift field 44B component
	@Column(length = 140)
	@UIAction(label = "Final Destination")
	private String placeOfFinalDestination;
	
	// dispatchFrom, portOfLoading, portOfDischarge, placeOfFinalDestination

	// swift field 44C component
	@UIAction(label = "Latest Shipment Date")
	private LocalDate latestDateOfShipment;

	// swift field 44D component
	@Lob
	@Column(nullable = true)
	@UIAction(label = "Shipment Period")
	private String shipmentPeriod;

	// swift field 45A component
	@Lob
	@Column(nullable = true)
	@UIAction(label = "Goods Description")
	private String goodsDescription;
	
	//shipmentPeriod, goodDesription, narrative, senderToReceiverInfo

	// swift field 57a component
	@JoinColumn(referencedColumnName = "id")
	@UIAction(label = "Advise Through Bank")
	private Long advThrBankPartyId;
	
	@UIAction(label = "Beneficiary")
	@Formula("(select a.name from Party a where a.id = adv_Thr_Bank_Party_Id)")
	private String advThrBankName;
	
	@UIAction(label = "Beneficiary")
	@Formula("(select a.bic_Code from Party a where a.id = adv_Thr_Bank_Party_Id)")
	private String advThrBankBic;
	
	@UIAction(label = "Beneficiary")
	@Formula("(select a.account from Party a where a.id = adv_Thr_Bank_Party_Id)")
	private String advThrBankAccountNo;

	// swift field 79Z component
	@Lob
	@Column(nullable = true)
	@UIAction(label = "Narrative")
	private String narrative;

	// swift field 72Z component
	@Lob
	@Column(nullable = true)
	@UIAction(label = "Sender To Receiver Info")
	private String senderToReceiverInfo;

	/**
	 * @return the senderBic
	 */
	public String getSenderBic() {
		return senderBic;
	}

	/**
	 * @return the receiverBic
	 */
	public String getReceiverBic() {
		return receiverBic;
	}

	/**
	 * @return the formOfDocumentaryCr
	 */
	public String getFormOfDocumentaryCr() {
		return formOfDocumentaryCr;
	}

	/**
	 * @return the documentaryCrNo
	 */
	public String getDocumentaryCrNo() {
		return documentaryCrNo;
	}

	/**
	 * @return the dateOfExpiry
	 */
	public LocalDate getDateOfExpiry() {
		return dateOfExpiry;
	}

	/**
	 * @return the placeOfExpiry
	 */
	public String getPlaceOfExpiry() {
		return placeOfExpiry;
	}

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @return the amountOfDocumentaryCredit
	 */
	public BigDecimal getAmountOfDocumentaryCredit() {
		return amountOfDocumentaryCredit;
	}

	/**
	 * @return the tolerance1
	 */
	public Double getTolerancePlus() {
		return tolerancePlus;
	}

	/**
	 * @return the tolerance2
	 */
	public Double getToleranceMinus() {
		return toleranceMinus;
	}

	/**
	 * @return the additionalAmountsCovered
	 */
	public String getAdditionalAmountsCovered() {
		return additionalAmountsCovered;
	}

	

	/**
	 * @return the dispatchFrom
	 */
	public String getDispatchFrom() {
		return dispatchFrom;
	}

	/**
	 * @return the portOfLoading
	 */
	public String getPortOfLoading() {
		return portOfLoading;
	}

	/**
	 * @return the portOfDischarge
	 */
	public String getPortOfDischarge() {
		return portOfDischarge;
	}

	/**
	 * @return the placeOfFinalDestination
	 */
	public String getPlaceOfFinalDestination() {
		return placeOfFinalDestination;
	}

	/**
	 * @return the latestDateOfShipment
	 */
	public LocalDate getLatestDateOfShipment() {
		return latestDateOfShipment;
	}

	/**
	 * @return the shipmentPeriod
	 */
	public String getShipmentPeriod() {
		return shipmentPeriod;
	}

	/**
	 * @return the goodDesription
	 */
	public String getGoodsDescription() {
		return goodsDescription;
	}

	/**
	 * @return the advThrBankParty
	 */
	public Long getAdvThrBankPartyId() {
		return advThrBankPartyId;
	}

	/**
	 * @return the narrative
	 */
	public String getNarrative() {
		return narrative;
	}

	/**
	 * @return the senderToReceiverInfo
	 */
	public String getSenderToReceiverInfo() {
		return senderToReceiverInfo;
	}

	/**
	 * @param senderBic the senderBic to set
	 */
	public void setSenderBic(String senderBic) {
		this.senderBic = senderBic;
	}

	/**
	 * @param receiverBic the receiverBic to set
	 */
	public void setReceiverBic(String receiverBic) {
		this.receiverBic = receiverBic;
	}

	/**
	 * @param formOfDocumentaryCr the formOfDocumentaryCr to set
	 */
	public void setFormOfDocumentaryCr(String formOfDocumentaryCr) {
		this.formOfDocumentaryCr = formOfDocumentaryCr;
	}

	/**
	 * @param documentaryCrNo the documentaryCrNo to set
	 */
	public void setDocumentaryCrNo(String documentaryCrNo) {
		this.documentaryCrNo = documentaryCrNo;
	}

	/**
	 * @param dateOfExpiry the dateOfExpiry to set
	 */
	public void setDateOfExpiry(LocalDate dateOfExpiry) {
		this.dateOfExpiry = dateOfExpiry;
	}

	/**
	 * @param placeOfExpiry the placeOfExpiry to set
	 */
	public void setPlaceOfExpiry(String placeOfExpiry) {
		this.placeOfExpiry = placeOfExpiry;
	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @param amountOfDocumentaryCredit the amountOfDocumentaryCredit to set
	 */
	public void setAmountOfDocumentaryCredit(BigDecimal amountOfDocumentaryCredit) {
		this.amountOfDocumentaryCredit = amountOfDocumentaryCredit;
	}

	/**
	 * @param tolerance1 the tolerance1 to set
	 */
	public void setTolerancePlus(Double tolerancePlus) {
		this.tolerancePlus = tolerancePlus;
	}

	/**
	 * @param tolerance2 the tolerance2 to set
	 */
	public void setToleranceMinus(Double toleranceMinus) {
		this.toleranceMinus = toleranceMinus;
	}

	/**
	 * @param additionalAmountsCovered the additionalAmountsCovered to set
	 */
	public void setAdditionalAmountsCovered(String additionalAmountsCovered) {
		this.additionalAmountsCovered = additionalAmountsCovered;
	}

	

	/**
	 * @param dispatchFrom the dispatchFrom to set
	 */
	public void setDispatchFrom(String dispatchFrom) {
		this.dispatchFrom = dispatchFrom;
	}

	/**
	 * @param portOfLoading the portOfLoading to set
	 */
	public void setPortOfLoading(String portOfLoading) {
		this.portOfLoading = portOfLoading;
	}

	/**
	 * @param portOfDischarge the portOfDischarge to set
	 */
	public void setPortOfDischarge(String portOfDischarge) {
		this.portOfDischarge = portOfDischarge;
	}

	/**
	 * @param placeOfFinalDestination the placeOfFinalDestination to set
	 */
	public void setPlaceOfFinalDestination(String placeOfFinalDestination) {
		this.placeOfFinalDestination = placeOfFinalDestination;
	}

	/**
	 * @param latestDateOfShipment the latestDateOfShipment to set
	 */
	public void setLatestDateOfShipment(LocalDate latestDateOfShipment) {
		this.latestDateOfShipment = latestDateOfShipment;
	}

	/**
	 * @param shipmentPeriod the shipmentPeriod to set
	 */
	public void setShipmentPeriod(String shipmentPeriod) {
		this.shipmentPeriod = shipmentPeriod;
	}

	/**
	 * @param goodDesription the goodDesription to set
	 */
	public void setGoodsDescription(String goodsDescription) {
		this.goodsDescription = goodsDescription;
	}

	/**
	 * @param advThrBankParty the advThrBankParty to set
	 */
	public void setAdvThrBankParty(Long advThrBankPartyId) {
		this.advThrBankPartyId = advThrBankPartyId;
	}

	/**
	 * @param narrative the narrative to set
	 */
	public void setNarrative(String narrative) {
		this.narrative = narrative;
	}

	/**
	 * @param senderToReceiverInfo the senderToReceiverInfo to set
	 */
	public void setSenderToReceiverInfo(String senderToReceiverInfo) {

		this.senderToReceiverInfo = senderToReceiverInfo;
	}

	public Long getLcMasterId() {
		return lcMasterId;
	}

	public void setLcMasterId(Long lcMasterId) {
		this.lcMasterId = lcMasterId;
	}

	public Long getApplicantPartyId() {
		return applicantPartyId;
	}

	public void setApplicantPartyId(Long applicantPartyId) {
		this.applicantPartyId = applicantPartyId;
	}

	public Party getApplicant() {
		return applicant;
	}

	public void setApplicant(Party applicant) {
		this.applicant = applicant;
	}

	public Long getBeneficiaryPartyId() {
		return beneficiaryPartyId;
	}

	public void setBeneficiaryPartyId(Long beneficiaryPartyId) {
		this.beneficiaryPartyId = beneficiaryPartyId;
	}

	public Party getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(Party beneficiary) {
		this.beneficiary = beneficiary;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public String getAdvThrBankName() {
		return advThrBankName;
	}

	public void setAdvThrBankName(String advThrBankName) {
		this.advThrBankName = advThrBankName;
	}

	public String getAdvThrBankBic() {
		return advThrBankBic;
	}

	public void setAdvThrBankBic(String advThrBankBic) {
		this.advThrBankBic = advThrBankBic;
	}

	public String getAdvThrBankAccountNo() {
		return advThrBankAccountNo;
	}

	public void setAdvThrBankAccountNo(String advThrBankAccountNo) {
		this.advThrBankAccountNo = advThrBankAccountNo;
	}

	public void setAdvThrBankPartyId(Long advThrBankPartyId) {
		this.advThrBankPartyId = advThrBankPartyId;
	}

	public String getAvailableWithByCode() {
		return availableWithByCode;
	}

	public void setAvailableWithByCode(String availableWithByCode) {
		this.availableWithByCode = availableWithByCode;
	}

	public String getAvailableWithByBic() {
		return availableWithByBic;
	}

	public void setAvailableWithByBic(String availableWithByBic) {
		this.availableWithByBic = availableWithByBic;
	}

	public String getAvailableWithByName() {
		return availableWithByName;
	}

	public void setAvailableWithByName(String availableWithByName) {
		this.availableWithByName = availableWithByName;
	}

	public String getAvailableWithByAddress1() {
		return availableWithByAddress1;
	}

	public void setAvailableWithByAddress1(String availableWithByAddress1) {
		this.availableWithByAddress1 = availableWithByAddress1;
	}

	public String getAvailableWithByAddress2() {
		return availableWithByAddress2;
	}

	public void setAvailableWithByAddress2(String availableWithByAddress2) {
		this.availableWithByAddress2 = availableWithByAddress2;
	}

	public String getAvailableWithByAddress3() {
		return availableWithByAddress3;
	}

	public void setAvailableWithByAddress3(String availableWithByAddress3) {
		this.availableWithByAddress3 = availableWithByAddress3;
	}

	public Register getRegister() {
		return register;
	}

	public void setRegister(Register register) {
		this.register = register;
	}

	public LcMaster getLcMaster() {
		return lcMaster;
	}

	public void setLcMaster(LcMaster lcMaster) {
		this.lcMaster = lcMaster;
	}

	public Long getAdvisingBankPartyId() {
		return advisingBankPartyId;
	}

	public void setAdvisingBankPartyId(Long advisingBankPartyId) {
		this.advisingBankPartyId = advisingBankPartyId;
	}

	public String getAdvisingBankName() {
		return advisingBankName;
	}

	public void setAdvisingBankName(String advisingBankName) {
		this.advisingBankName = advisingBankName;
	}

	public Party getAdvisingBank() {
		return advisingBank;
	}

	public void setAdvisingBank(Party advisingBank) {
		this.advisingBank = advisingBank;
	}

	public String getBeneficiaryCountry() {
		return beneficiaryCountry;
	}

	public void setBeneficiaryCountry(String beneficiaryCountry) {
		this.beneficiaryCountry = beneficiaryCountry;
	}

	public String getAdvisingBankBicCode() {
		return advisingBankBicCode;
	}

	public void setAdvisingBankBicCode(String advisingBankBicCode) {
		this.advisingBankBicCode = advisingBankBicCode;
	}
	
	
	
}

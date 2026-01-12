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
public class ImportLetterOfCreditPreAdvise extends AbstractPojo
{
	
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
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName = "id")
	@UIAction(label = "Applicant")
	private Party applicant;

	// swift field 59 components
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName = "id")
	@UIAction(label = "Beneficiary")
	private Party beneficiary;

	// swift field 32B components
	@Column(length = 3)
	@UIAction(label = "Currency")
	private String currencyCode;
	
	@Column(precision = 15,scale=2)
	@UIAction(label = "LC Amount")
	private BigDecimal amountOfDocumentaryCredit;

	// swift field 39A components
	@Column(precision = 2,scale=0)
	@UIAction(label = "Tolerance (+)")
	private int tolerance1;
	
	@Column(precision = 2,scale=0)
	@UIAction(label = "Tolerance (-)")
	private int tolerance2;

	// swift field 39C component
	@Column(length = 140)
	@UIAction(label = "Additional Amounts Covered")
	private String additionalAmountsCovered;

	// swift field 41A component :BIC
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName = "id")
	@UIAction(label = "Available With ... By ...")
	private Party availableWithBy;


	// swift field 44A component
	@Column(length = 65)
	@UIAction(label = "Dispatch From")
	private String dispatchFrom;

	// swift field 44E component
	@Column(length = 65)
	@UIAction(label = "Port of Loading")
	private String portOfLoading;

	// swift field 44F component
	@Column(length = 65)
	@UIAction(label = "Port of Discharge")
	private String portOfDischarge;

	// swift field 44B component
	@Column(length = 65)
	@UIAction(label = "Final Destination")
	private String placeOfFinalDestination;

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
	private String goodDesription;

	// swift field 57a component
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName = "id")
	@UIAction(label = "Advise Through Bank")
	private Party advThrBankParty;

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
	 * @return the advThrBankParty
	 */
	public Party getAdvThrBankParty()
	{
		return advThrBankParty;
	}

	/**
	 * @return the narrative
	 */
	public String getNarrative()
	{
		return narrative;
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
	 * @param advThrBankParty the advThrBankParty to set
	 */
	public void setAdvThrBankParty(Party advThrBankParty)
	{
		this.advThrBankParty = advThrBankParty;
	}

	/**
	 * @param narrative the narrative to set
	 */
	public void setNarrative(String narrative)
	{
		this.narrative = narrative;
	}

	/**
	 * @param senderToReceiverInfo the senderToReceiverInfo to set
	 */
	public void setSenderToReceiverInfo(String senderToReceiverInfo)
	{
		this.senderToReceiverInfo = senderToReceiverInfo;
	}
}

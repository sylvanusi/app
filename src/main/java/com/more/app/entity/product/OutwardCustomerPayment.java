package com.more.app.entity.product;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.more.app.entity.AbstractPojo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;

@Entity
public class OutwardCustomerPayment extends AbstractPojo
{

	@Column(length = 11)
	private String senderBic;

	@Column(length = 11)
	private String receiverBic;

	@Column(length = 16)
	private String sendersReference;

	//@Temporal(TemporalType.DATE)
	private LocalDate interbankSettledValueDate;

	@Column(length = 3)
	private String interbankSettledCcy;

	@Column(length = 15, precision = 2)
	private BigDecimal interbankSettledAmount;

	@Column(length = 3)
	private String instructedCcy;

	@Column(length = 15, precision = 2)
	private BigDecimal instructedAmount;

	@Column(length = 12, precision = 12)
	private BigDecimal exchangeRate;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName = "id")
	private Party orderingCustomer;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName = "id")
	private Party sendingInstitution;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName = "id")
	private Party orderingInstitution;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName = "id")
	private Party senderCorrespondent;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName = "id")
	private Party receiverCorrespondent;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName = "id")
	private Party thirdReimbursementInstitution;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName = "id")
	private Party intermediaryInstitution;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName = "id")
	private Party accountWithInstitution;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName = "id")
	private Party beneficiaryCustomer;

	@Column(length = 140)
	private String remittanceInformation;

	@Column(length = 3)
	private String detailsOfCharges;

	@Column(length = 3)
	private String sendersChargeCcy;

	@Column(length = 15, precision = 2)
	private BigDecimal sendersChargeAmount;

	// swift field 71G component
	@Column(length = 3)
	private String receiversChargeCcy;
	@Column(length = 15, precision = 2)
	private BigDecimal receiversChargeAmount;
	// swift field 72 component
	@Lob
	@Column(nullable = true)
	private String senderToReceiverInformation;
	// swift field 77B component
	@Lob
	@Column(nullable = true)
	private String regulatoryReporting;
	
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
	public String getSendersReference()
	{
		return sendersReference;
	}
	public void setSendersReference(String sendersReference)
	{
		this.sendersReference = sendersReference;
	}
	public LocalDate getInterbankSettledValueDate()
	{
		return interbankSettledValueDate;
	}
	public void setInterbankSettledValueDate(LocalDate interbankSettledValueDate)
	{
		this.interbankSettledValueDate = interbankSettledValueDate;
	}
	public String getInterbankSettledCcy()
	{
		return interbankSettledCcy;
	}
	public void setInterbankSettledCcy(String interbankSettledCcy)
	{
		this.interbankSettledCcy = interbankSettledCcy;
	}
	public BigDecimal getInterbankSettledAmount()
	{
		return interbankSettledAmount;
	}
	public void setInterbankSettledAmount(BigDecimal interbankSettledAmount)
	{
		this.interbankSettledAmount = interbankSettledAmount;
	}
	public String getInstructedCcy()
	{
		return instructedCcy;
	}
	public void setInstructedCcy(String instructedCcy)
	{
		this.instructedCcy = instructedCcy;
	}
	public BigDecimal getInstructedAmount()
	{
		return instructedAmount;
	}
	public void setInstructedAmount(BigDecimal instructedAmount)
	{
		this.instructedAmount = instructedAmount;
	}
	public BigDecimal getExchangeRate()
	{
		return exchangeRate;
	}
	public void setExchangeRate(BigDecimal exchangeRate)
	{
		this.exchangeRate = exchangeRate;
	}
	public Party getOrderingCustomer()
	{
		return orderingCustomer;
	}
	public void setOrderingCustomer(Party orderingCustomer)
	{
		this.orderingCustomer = orderingCustomer;
	}
	public Party getSendingInstitution()
	{
		return sendingInstitution;
	}
	public void setSendingInstitution(Party sendingInstitution)
	{
		this.sendingInstitution = sendingInstitution;
	}
	public Party getOrderingInstitution()
	{
		return orderingInstitution;
	}
	public void setOrderingInstitution(Party orderingInstitution)
	{
		this.orderingInstitution = orderingInstitution;
	}
	public Party getSenderCorrespondent()
	{
		return senderCorrespondent;
	}
	public void setSenderCorrespondent(Party senderCorrespondent)
	{
		this.senderCorrespondent = senderCorrespondent;
	}
	public Party getReceiverCorrespondent()
	{
		return receiverCorrespondent;
	}
	public void setReceiverCorrespondent(Party receiverCorrespondent)
	{
		this.receiverCorrespondent = receiverCorrespondent;
	}
	public Party getThirdReimbursementInstitution()
	{
		return thirdReimbursementInstitution;
	}
	public void setThirdReimbursementInstitution(Party thirdReimbursementInstitution)
	{
		this.thirdReimbursementInstitution = thirdReimbursementInstitution;
	}
	public Party getIntermediaryInstitution()
	{
		return intermediaryInstitution;
	}
	public void setIntermediaryInstitution(Party intermediaryInstitution)
	{
		this.intermediaryInstitution = intermediaryInstitution;
	}
	public Party getAccountWithInstitution()
	{
		return accountWithInstitution;
	}
	public void setAccountWithInstitution(Party accountWithInstitution)
	{
		this.accountWithInstitution = accountWithInstitution;
	}
	public Party getBeneficiaryCustomer()
	{
		return beneficiaryCustomer;
	}
	public void setBeneficiaryCustomer(Party beneficiaryCustomer)
	{
		this.beneficiaryCustomer = beneficiaryCustomer;
	}
	public String getRemittanceInformation()
	{
		return remittanceInformation;
	}
	public void setRemittanceInformation(String remittanceInformation)
	{
		this.remittanceInformation = remittanceInformation;
	}
	public String getDetailsOfCharges()
	{
		return detailsOfCharges;
	}
	public void setDetailsOfCharges(String detailsOfCharges)
	{
		this.detailsOfCharges = detailsOfCharges;
	}
	public String getSendersChargeCcy()
	{
		return sendersChargeCcy;
	}
	public void setSendersChargeCcy(String sendersChargeCcy)
	{
		this.sendersChargeCcy = sendersChargeCcy;
	}
	public BigDecimal getSendersChargeAmount()
	{
		return sendersChargeAmount;
	}
	public void setSendersChargeAmount(BigDecimal sendersChargeAmount)
	{
		this.sendersChargeAmount = sendersChargeAmount;
	}
	public String getReceiversChargeCcy()
	{
		return receiversChargeCcy;
	}
	public void setReceiversChargeCcy(String receiversChargeCcy)
	{
		this.receiversChargeCcy = receiversChargeCcy;
	}
	public BigDecimal getReceiversChargeAmount()
	{
		return receiversChargeAmount;
	}
	public void setReceiversChargeAmount(BigDecimal receiversChargeAmount)
	{
		this.receiversChargeAmount = receiversChargeAmount;
	}
	public String getSenderToReceiverInformation()
	{
		return senderToReceiverInformation;
	}
	public void setSenderToReceiverInformation(String senderToReceiverInformation)
	{
		this.senderToReceiverInformation = senderToReceiverInformation;
	}
	public String getRegulatoryReporting()
	{
		return regulatoryReporting;
	}
	public void setRegulatoryReporting(String regulatoryReporting)
	{
		this.regulatoryReporting = regulatoryReporting;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "OutwardCustomerPayment [id=" + id + ", senderBic=" + senderBic + ", receiverBic=" + receiverBic
				+ ", sendersReference=" + sendersReference + ", interbankSettledValueDate=" + interbankSettledValueDate
				+ ", interbankSettledCcy=" + interbankSettledCcy + ", interbankSettledAmount=" + interbankSettledAmount
				+ ", instructedCcy=" + instructedCcy + ", instructedAmount=" + instructedAmount + ", exchangeRate="
				+ exchangeRate + ", orderingCustomer=" + orderingCustomer + ", sendingInstitution=" + sendingInstitution
				+ ", orderingInstitution=" + orderingInstitution + ", senderCorrespondent=" + senderCorrespondent
				+ ", receiverCorrespondent=" + receiverCorrespondent + ", thirdReimbursementInstitution="
				+ thirdReimbursementInstitution + ", intermediaryInstitution=" + intermediaryInstitution
				+ ", accountWithInstitution=" + accountWithInstitution + ", beneficiaryCustomer=" + beneficiaryCustomer
				+ ", remittanceInformation=" + remittanceInformation + ", detailsOfCharges=" + detailsOfCharges
				+ ", sendersChargeCcy=" + sendersChargeCcy + ", sendersChargeAmount=" + sendersChargeAmount
				+ ", receiversChargeCcy=" + receiversChargeCcy + ", receiversChargeAmount=" + receiversChargeAmount
				+ ", senderToReceiverInformation=" + senderToReceiverInformation + ", regulatoryReporting="
				+ regulatoryReporting + "]";
	}

}

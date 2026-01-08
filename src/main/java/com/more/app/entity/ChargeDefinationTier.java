package com.more.app.entity;

import com.more.app.util.annotations.Auditable;
import com.more.app.util.annotations.UIAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Transient;

@Entity
@NamedQuery(name = "ChargeDefinationTier.findAllOrderedDesc", query = "SELECT p FROM ChargeDefinationTier p order by p.id desc")
public class ChargeDefinationTier extends AbstractPojo
{	
	//@UIAction(label = "Charge Defination", errorlabel = "Charge Defination is mandatory")
	//@JoinColumn(nullable = false, referencedColumnName = "id")
	//@Auditable(enableAudit = true, fieldNo = 4)
	//private ChargeDefination chargeDefn;
	
	@UIAction(label = "Charge Name", errorlabel = "Charge Name is mandatory")
	@Transient
	private String chargeDefnName;
	
	@UIAction(label = "Charge Code", errorlabel = "Charge Code is mandatory")
	@Transient
	private String chargeDefnCode;
	
	@UIAction(label = "Tier No", errorlabel = "Tier No is mandatory")
	@Column(nullable = true)
	@Auditable(enableAudit = true, fieldNo = 5)
	private int tierNo;
	
	@UIAction(label = "Lower Limit", errorlabel = "Lower Limit is mandatory")
	@Column(nullable = true, precision = 15)
	@Auditable(enableAudit = true, fieldNo = 6)
	private Double lowerLimitAmount;
	
	@UIAction(label = "Upper Limit", errorlabel = "Upper Limit is mandatory")
	@Column(nullable = true, precision = 15)
	@Auditable(enableAudit = true, fieldNo = 7)
	private Double upperLimitAmount;
	
	@UIAction(label = "Charge Amount", errorlabel = "Charge Amount is mandatory")
	@Column(nullable = true, precision = 15)
	@Auditable(enableAudit = true, fieldNo = 8)
	private Double chargeAmount;
	
	@UIAction(label = "Min Charge Amount", errorlabel = "Min Charge Amount is mandatory")
	@Column(nullable = true, precision = 15)
	@Auditable(enableAudit = true, fieldNo = 9)
	private Double minTierChargeAmount;
	
	@UIAction(label = "Max Charge Amount", errorlabel = "Max Charge Amount is mandatory")
	@Column(nullable = true, precision = 15)
	@Auditable(enableAudit = true, fieldNo = 10)
	private Double maxTierChargeAmount;
	
	@UIAction(label = "Percentage", errorlabel = "Percentage is mandatory")
	@Column(nullable = true, precision = 2)
	@Auditable(enableAudit = true, fieldNo = 11)
	private double chargePercent;

	@UIAction(label = "Interest Rate", errorlabel = "Interest Rate is mandatory")
	@Column(nullable = true, precision = 2)
	@Auditable(enableAudit = true, fieldNo = 12)
	private double interestRate;


	/**
	 * @return the chargeDefnName
	 */
	public String getChargeDefnName()
	{
		return chargeDefnName;
	}

	/**
	 * @return the chargeDefnCode
	 */
	public String getChargeDefnCode()
	{
		return chargeDefnCode;
	}

	/**
	 * @return the tierNo
	 */
	public int getTierNo()
	{
		return tierNo;
	}

	/**
	 * @return the lowerLimitAmount
	 */
	public Double getLowerLimitAmount()
	{
		return lowerLimitAmount;
	}

	/**
	 * @return the upperLimitAmount
	 */
	public Double getUpperLimitAmount()
	{
		return upperLimitAmount;
	}

	/**
	 * @return the chargeAmount
	 */
	public Double getChargeAmount()
	{
		return chargeAmount;
	}

	/**
	 * @return the minTierChargeAmount
	 */
	public Double getMinTierChargeAmount()
	{
		return minTierChargeAmount;
	}

	/**
	 * @return the maxTierChargeAmount
	 */
	public Double getMaxTierChargeAmount()
	{
		return maxTierChargeAmount;
	}

	/**
	 * @return the chargePercent
	 */
	public double getChargePercent()
	{
		return chargePercent;
	}

	/**
	 * @return the interestRate
	 */
	public double getInterestRate()
	{
		return interestRate;
	}


	/**
	 * @param chargeDefnName the chargeDefnName to set
	 */
	public void setChargeDefnName(String chargeDefnName)
	{
		this.chargeDefnName = chargeDefnName;
	}

	/**
	 * @param chargeDefnCode the chargeDefnCode to set
	 */
	public void setChargeDefnCode(String chargeDefnCode)
	{
		this.chargeDefnCode = chargeDefnCode;
	}

	/**
	 * @param tierNo the tierNo to set
	 */
	public void setTierNo(int tierNo)
	{
		this.tierNo = tierNo;
	}

	/**
	 * @param lowerLimitAmount the lowerLimitAmount to set
	 */
	public void setLowerLimitAmount(Double lowerLimitAmount)
	{
		this.lowerLimitAmount = lowerLimitAmount;
	}

	/**
	 * @param upperLimitAmount the upperLimitAmount to set
	 */
	public void setUpperLimitAmount(Double upperLimitAmount)
	{
		this.upperLimitAmount = upperLimitAmount;
	}

	/**
	 * @param chargeAmount the chargeAmount to set
	 */
	public void setChargeAmount(Double chargeAmount)
	{
		this.chargeAmount = chargeAmount;
	}

	/**
	 * @param minTierChargeAmount the minTierChargeAmount to set
	 */
	public void setMinTierChargeAmount(Double minTierChargeAmount)
	{
		this.minTierChargeAmount = minTierChargeAmount;
	}

	/**
	 * @param maxTierChargeAmount the maxTierChargeAmount to set
	 */
	public void setMaxTierChargeAmount(Double maxTierChargeAmount)
	{
		this.maxTierChargeAmount = maxTierChargeAmount;
	}

	/**
	 * @param chargePercent the chargePercent to set
	 */
	public void setChargePercent(double chargePercent)
	{
		this.chargePercent = chargePercent;
	}

	/**
	 * @param interestRate the interestRate to set
	 */
	public void setInterestRate(double interestRate)
	{
		this.interestRate = interestRate;
	}

}

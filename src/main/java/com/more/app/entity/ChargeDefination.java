package com.more.app.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.more.app.util.annotations.Auditable;
import com.more.app.util.annotations.UIAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

@Entity
@NamedQuery(name = "ChargeDefination.findAllOrderedDesc", query = "SELECT p FROM ChargeDefination p order by p.id desc")

public class ChargeDefination extends AbstractPojo
{
	@UIAction(label = "Bank", errorlabel = "Bank is mandatory")
	@JoinColumn(nullable = false, referencedColumnName = "id")
	@Auditable(enableAudit = true, fieldNo = 4)
	private Bank bank;

	@UIAction(label = "Bank", errorlabel = "Bank is mandatory")
	@Transient
	private String bankName;

	@UIAction(label = "Account Type", errorlabel = "Account Type is mandatory")
	@JoinColumn(nullable = false, referencedColumnName = "id")
	@Auditable(enableAudit = true, fieldNo = 5)
	private AccountType accountType;

	@UIAction(label = "Account Type", errorlabel = "Account Type is mandatory")
	@Transient
	private String accountTypeCode;

	@UIAction(label = "Account Type", errorlabel = "Account Type is mandatory")
	@Transient
	private String accountTypeDesc;

	@UIAction(label = "Charge Name", errorlabel = "Charge Name is mandatory")
	@Column(nullable = false, length = 35)
	@Auditable(enableAudit = true, fieldNo = 6)
	private String chargeName;

	@UIAction(label = "Charge Code", errorlabel = "Charge Code is mandatory")
	@Column(nullable = false, length = 10)
	@Auditable(enableAudit = true, fieldNo = 7)
	private String chargeCode;

	@UIAction(label = "Charge Description", errorlabel = "Charge Description is mandatory")
	@Column(nullable = false, length = 70)
	@Auditable(enableAudit = true, fieldNo = 8)
	private String description;

	// otions are Standard Tax
	@UIAction(label = "Charge Type", errorlabel = "Charge Type is mandatory")
	@Column(nullable = false, length = 1)
	@Auditable(enableAudit = true, fieldNo = 9)
	private String chargeType;

	// OneOff, daily, weekly monthly biannual annual
	@UIAction(label = "Frequency", errorlabel = "Frequency is mandatory")
	@Column(nullable = true, length = 12)
	@Auditable(enableAudit = true, fieldNo = 10)
	//@Convert("chargeFrequency")
	private String chargeFrequency;
	
	@UIAction(label = "Charge In", errorlabel = "Charge In is mandatory")
	@Column(nullable = false, length = 1)
	@Auditable(enableAudit = true, fieldNo = 21)
	private String chargeIn;

	@UIAction(label = "Charge CCY", errorlabel = "Charge CCY is mandatory")
	@Column(nullable = true, length = 3)
	@Auditable(enableAudit = true, fieldNo = 12)
	private String chargeCcy;

	@UIAction(label = "Charge Amount", errorlabel = "Charge Amount is mandatory")
	@Column(nullable = true, precision = 15)
	@Auditable(enableAudit = true, fieldNo = 15)
	private BigDecimal amountToCharge;

	@UIAction(label = "Min. Charge", errorlabel = "Minimum Charge is mandatory")
	@Column(nullable = true, precision = 15)
	@Auditable(enableAudit = true, fieldNo = 13)
	private BigDecimal minAmount;

	@UIAction(label = "Max. Charge", errorlabel = "Maximum Charge is mandatory")
	@Column(nullable = true, precision = 15)
	@Auditable(enableAudit = true, fieldNo = 14)
	private BigDecimal maxAmount;

	@UIAction(label = "Has Tiers?", errorlabel = "Has Tiers is mandatory")
	@Auditable(enableAudit = true, fieldNo = 16)
	private boolean tiered;

	@UIAction(label = "Applies to product", errorlabel = "For Product is mandatory")
	@Auditable(enableAudit = true, fieldNo = 17)
	private boolean appliesToProduct;

	@UIAction(label = "Overall Min Amount", errorlabel = "Charge Amount is mandatory")
	@Column(nullable = true, precision = 15)
	@Auditable(enableAudit = true, fieldNo = 18)
	private BigDecimal overallMinAmount;
	
	@UIAction(label = "Overall Max Amount", errorlabel = "Charge Amount is mandatory")
	@Column(nullable = true, precision = 15)
	@Auditable(enableAudit = true, fieldNo = 19)
	private BigDecimal overallMaxAmount;

	@UIAction(label = "Percentage", errorlabel = "Percentage is mandatory")
	@Column(nullable = true, precision = 8)
	@Auditable(enableAudit = true, fieldNo = 11)
	private Double chargePercent;

	@UIAction(label = "Interest Rate", errorlabel = "Interest Rate is mandatory")
	@Column(nullable = true, precision = 8)
	@Auditable(enableAudit = true, fieldNo = 12)
	private Double interestRate;

	@UIAction(label = "Taxable", errorlabel = "Taxable is mandatory")
	@Auditable(enableAudit = true, fieldNo = 20)
	private boolean taxable;

	@UIAction(label = "Calculation Method", errorlabel = "Calculation Method is mandatory")
	@Column(nullable = false, length = 15)
	@Auditable(enableAudit = true, fieldNo = 55)
	private String calculationMethod;


	@UIAction(label = "Tax Charge", errorlabel = "Tax Charge is mandatory")
	//@Column(nullable = true)
	@Auditable(enableAudit = true, fieldNo = 56)
	private ChargeDefination taxChargeDef;
	
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
	@Auditable(enableAudit = false, fieldNo = 57)
	private Set<ChargeDefinationTier> chargeTiers = new HashSet<>();
	/**
	 * @return the calculationMethod
	 */
	public String getCalculationMethod()
	{
		return calculationMethod;
	}

	/**
	 * @param calculationMethod the calculationMethod to set
	 */
	public void setCalculationMethod(String calculationMethod)
	{
		this.calculationMethod = calculationMethod;
	}

	/**
	 * @return the bank
	 */
	public Bank getBank()
	{
		return bank;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName()
	{
		if (null != getBank())
			bankName = getBank().getName();
		return bankName;
	}

	/**
	 * @return the accountType
	 */
	public AccountType getAccountType()
	{
		return accountType;
	}

	/**
	 * @return the accountTypeCode
	 */
	public String getAccountTypeCode()
	{
		if (null != getAccountType())
			accountTypeCode = getAccountType().getCode();
		return accountTypeCode;
	}

	/**
	 * @return the accountTypeDesc
	 */
	public String getAccountTypeDesc()
	{
		if (null != getAccountType())
			accountTypeDesc = getAccountType().getDescription();
		return accountTypeDesc;
	}

	/**
	 * @return the chargeName
	 */
	public String getChargeName()
	{
		return chargeName;
	}

	/**
	 * @return the chargeCode
	 */
	public String getChargeCode()
	{
		return chargeCode;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @return the chargeType
	 */
	public String getChargeType()
	{
		return chargeType;
	}

	/**
	 * @return the chargeFrequency
	 */
	public String getChargeFrequency()
	{
		return chargeFrequency;
	}

	/**
	 * @return the chargeCcy
	 */
	public String getChargeCcy()
	{
		return chargeCcy;
	}

	/**
	 * @return the minAmount
	 */
	public BigDecimal getMinAmount()
	{
		return minAmount;
	}

	/**
	 * @return the maxAmount
	 */
	public BigDecimal getMaxAmount()
	{
		return maxAmount;
	}

	/**
	 * @return the tiered
	 */
	public boolean isTiered()
	{
		return tiered;
	}

	/**
	 * @return the appliesToProduct
	 */
	public boolean isAppliesToProduct()
	{
		return appliesToProduct;
	}

	/**
	 * @return the overallMinAmount
	 */
	public BigDecimal getOverallMinAmount()
	{
		return overallMinAmount;
	}

	/**
	 * @return the overallMaxAmount
	 */
	public BigDecimal getOverallMaxAmount()
	{
		return overallMaxAmount;
	}

	/**
	 * @param bank the bank to set
	 */
	public void setBank(Bank bank)
	{
		this.bank = bank;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}

	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(AccountType accountType)
	{
		this.accountType = accountType;
	}

	/**
	 * @param accountTypeCode the accountTypeCode to set
	 */
	public void setAccountTypeCode(String accountTypeCode)
	{
		this.accountTypeCode = accountTypeCode;
	}

	/**
	 * @param accountTypeDesc the accountTypeDesc to set
	 */
	public void setAccountTypeDesc(String accountTypeDesc)
	{
		this.accountTypeDesc = accountTypeDesc;
	}

	/**
	 * @param chargeName the chargeName to set
	 */
	public void setChargeName(String chargeName)
	{
		this.chargeName = chargeName;
	}

	/**
	 * @param chargeCode the chargeCode to set
	 */
	public void setChargeCode(String chargeCode)
	{
		this.chargeCode = chargeCode;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @param chargeType the chargeType to set
	 */
	public void setChargeType(String chargeType)
	{
		this.chargeType = chargeType;
	}

	/**
	 * @param chargeFrequency the chargeFrequency to set
	 */
	public void setChargeFrequency(String chargeFrequency)
	{
		this.chargeFrequency = chargeFrequency;
	}

	/**
	 * @param chargeCcy the chargeCcy to set
	 */
	public void setChargeCcy(String chargeCcy)
	{
		this.chargeCcy = chargeCcy;
	}

	/**
	 * @param minAmount the minAmount to set
	 */
	public void setMinAmount(BigDecimal minAmount)
	{
		this.minAmount = minAmount;
	}

	/**
	 * @param maxAmount the maxAmount to set
	 */
	public void setMaxAmount(BigDecimal maxAmount)
	{
		this.maxAmount = maxAmount;
	}

	/**
	 * @param tiered the tiered to set
	 */
	public void setTiered(boolean tiered)
	{
		this.tiered = tiered;
	}

	/**
	 * @param appliesToProduct the appliesToProduct to set
	 */
	public void setAppliesToProduct(boolean appliesToProduct)
	{
		this.appliesToProduct = appliesToProduct;
	}

	/**
	 * @param overallMinAmount the overallMinAmount to set
	 */
	public void setOverallMinAmount(BigDecimal overallMinAmount)
	{
		this.overallMinAmount = overallMinAmount;
	}

	/**
	 * @param overallMaxAmount the overallMaxAmount to set
	 */
	public void setOverallMaxAmount(BigDecimal overallMaxAmount)
	{
		this.overallMaxAmount = overallMaxAmount;
	}

	/**
	 * @return the amountToCharge
	 */
	public BigDecimal getAmountToCharge()
	{
		return amountToCharge;
	}

	/**
	 * @param amountToCharge the amountToCharge to set
	 */
	public void setAmountToCharge(BigDecimal amountToCharge)
	{
		this.amountToCharge = amountToCharge;
	}

	/**
	 * @return the chargePercent
	 */
	public Double getChargePercent()
	{
		return chargePercent;
	}

	/**
	 * @return the interestRate
	 */
	public Double getInterestRate()
	{
		return interestRate;
	}

	/**
	 * @param chargePercent the chargePercent to set
	 */
	public void setChargePercent(Double chargePercent)
	{
		this.chargePercent = chargePercent;
	}

	public void setInterestRate(Double interestRate)
	{
		this.interestRate = interestRate;
	}

	public boolean isTaxable()
	{
		return taxable;
	}

	public void setTaxable(boolean taxable)
	{
		this.taxable = taxable;
	}

	public ChargeDefination getTaxChargeDef()
	{
		return taxChargeDef;
	}

	public Set<ChargeDefinationTier> getChargeTiers()
	{
		return chargeTiers;
	}

	public void setTaxChargeDef(ChargeDefination taxChargeDef)
	{
		this.taxChargeDef = taxChargeDef;
	}

	public void setChargeTiers(Set<ChargeDefinationTier> chargeTiers)
	{
		this.chargeTiers = chargeTiers;
	}
	
	public String getChargeIn()
	{
		return chargeIn;
	}
	
	public void setChargeIn(String chargeIn)
	{
		this.chargeIn = chargeIn;
	}
}

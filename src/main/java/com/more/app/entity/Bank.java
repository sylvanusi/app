package com.more.app.entity;

import org.hibernate.annotations.Formula;

import com.more.app.util.annotations.Auditable;
import com.more.app.util.annotations.UIAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Transient;

@Entity
public class Bank extends AbstractPojo
{
	
	private static final long serialVersionUID = 5073107118631294740L;

	@Auditable(enableAudit=true, fieldNo=4)
	@UIAction(label = "Bank Code",errorlabel="Code is required")
	@Column(nullable = false, length = 10)
	private String code;

	@Auditable(enableAudit=true, fieldNo=5)
	@UIAction(label = "Bank Name",errorlabel="Bank Name is required")
	@Column(nullable = false, length = 35)
	private String name;

	@Auditable(enableAudit=true, fieldNo=6)
	@UIAction(label = "Country Object",errorlabel="Country Name is required")
	@JoinColumn(referencedColumnName = "id", nullable = false)
	private Long countryId;
	
	@UIAction(label = "Country Name")
	@Transient
	private Country country;

	@UIAction(label = "Country Name")
	@Formula("(select a.country_name from Country a where a.id = country_Id)")
	private String countryName;

	@Auditable(enableAudit=true, fieldNo=7)
	@UIAction(label = "Local Currency Object",errorlabel="Local Currency is required")
	@JoinColumn(referencedColumnName = "id",nullable = false)
	private Long localCurrencyId;
	
	@UIAction(label = "Local Currency Object",errorlabel="Local Currency is required")
	@Transient
	private Currency localCurrency;

	@UIAction(label = "Local Currency")
	@Formula("(select a.currency from Currency a where a.id = LOCAL_CURRENCY_ID)")
	private String localCurrencyName;

	@UIAction(label = "Head Office Object",errorlabel="Head Office Branch Name is required")
	@Transient
	private Branch hoBranch;
	
	@Auditable(enableAudit=true, fieldNo=8)
	@UIAction(label = "Head Office Object",errorlabel="Head Office Branch Name is required")
	@JoinColumn(referencedColumnName = "id")
	private Long hoBranchId;

	@UIAction(label = "Head Office Branch Name")
	//@Transient
	@Column(nullable = false, length = 55)
	private String hoBranchName;

	@Auditable(enableAudit=true, fieldNo=9)
	@UIAction(label = "Default Swift Address",errorlabel="Default Swift Address is required")
	@Column(nullable = false, length = 11)
	private String defaultSwiftAddress;

	@UIAction(label = "Bank Group Param Object",errorlabel="Bank Group is required")
	@Transient
	private Parameters bankGroupParam;
	
	@Auditable(enableAudit=true, fieldNo=3)
	@UIAction(label = "Bank Group Param Object",errorlabel="Bank Group is required")
	@JoinColumn(referencedColumnName = "id",nullable = false)
	private Long bankGroupParamId;

	@UIAction(label = "Bank Group")
	@Transient
	private String bankGroupParamValue;

// code name country countryName localCurrency localCurrencyName hOBranch hOBranchName defaultSwiftAddress bankGroupParam bankGroupParamValue
	/**
	 * @return the code
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return the country
	 */
	public Country getCountry()
	{
		return country;
	}

	/**
	 * @return the countryName
	 */
	public String getCountryName()
	{
		if (null != this.getCountry())
			return this.getCountry().getCountryName();
		else
			return countryName;
	}

	/**
	 * @return the localCurrency
	 */
	public Currency getLocalCurrency()
	{
		return localCurrency;
	}

	/**
	 * @return the localCurrencyName
	 */
	public String getLocalCurrencyName()
	{
		if (null != this.getLocalCurrency())
			return this.getLocalCurrency().getCurrency();
		else
			return localCurrencyName;
	}

	/**
	 * @return the hOBranch
	 */
	public Branch getHoBranch()
	{
		return hoBranch;
	}

	/**
	 * @return the hOBranchName
	 */
	public String getHoBranchName()
	{
		if (null != this.getHoBranch())
			return this.getHoBranch().getName();
		else
			return hoBranchName;
	}

	/**
	 * @return the defaultSwiftAddress
	 */
	public String getDefaultSwiftAddress()
	{
		return defaultSwiftAddress;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code)
	{
		this.code = code;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(Country country)
	{
		this.country = country;
	}

	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName)
	{
		this.countryName = countryName;
	}

	/**
	 * @param localCurrency the localCurrency to set
	 */
	public void setLocalCurrency(Currency localCurrency)
	{
		this.localCurrency = localCurrency;
	}

	/**
	 * @param localCurrencyName the localCurrencyName to set
	 */
	public void setLocalCurrencyName(String localCurrencyName)
	{
		this.localCurrencyName = localCurrencyName;
	}

	/**
	 * @param hOBranch the hOBranch to set
	 */
	public void setHoBranch(Branch hoBranch)
	{
		this.hoBranch = hoBranch;
	}

	/**
	 * @param hOBranchName the hOBranchName to set
	 */
	public void setHoBranchName(String hoBranchName)
	{
		this.hoBranchName = hoBranchName;
	}

	/**
	 * @param defaultSwiftAddress the defaultSwiftAddress to set
	 */
	public void setDefaultSwiftAddress(String defaultSwiftAddress)
	{
		this.defaultSwiftAddress = defaultSwiftAddress;
	}

	/**
	 * @return the bankGroupParam
	 */
	public Parameters getBankGroupParam()
	{
		return bankGroupParam;
	}

	/**
	 * @return the bankGroupParamValue
	 */
	public String getBankGroupParamValue()
	{

		if (null != this.getBankGroupParam())
			return this.getBankGroupParam().getParamValue();
		else
			return bankGroupParamValue;
	}

	/**
	 * @param bankGroupParam the bankGroupParam to set
	 */
	public void setBankGroupParam(Parameters bankGroupParam)
	{
		this.bankGroupParam = bankGroupParam;
	}

	/**
	 * @param bankGroupParamValue the bankGroupParamValue to set
	 */
	public void setBankGroupParamValue(String bankGroupParamValue)
	{
		this.bankGroupParamValue = bankGroupParamValue;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Long getLocalCurrencyId() {
		return localCurrencyId;
	}

	public void setLocalCurrencyId(Long localCurrencyId) {
		this.localCurrencyId = localCurrencyId;
	}

	public Long getHoBranchId() {
		return hoBranchId;
	}

	public void setHoBranchId(Long hoBranchId) {
		this.hoBranchId = hoBranchId;
	}

	public Long getBankGroupParamId() {
		return bankGroupParamId;
	}

	public void setBankGroupParamId(Long bankGroupParamId) {
		this.bankGroupParamId = bankGroupParamId;
	}

	
}

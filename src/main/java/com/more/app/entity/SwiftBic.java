package com.more.app.entity;

import com.more.app.annotations.UIAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class SwiftBic extends AbstractPojo
{
	private static final long serialVersionUID = -1888725261112743193L;

	@UIAction(label = "Swift BIC",errorlabel="Swift BIC is mandatory")
	@Column(nullable = false, length = 11)
	private String swiftBic;

	@UIAction(label = "Bank Name",errorlabel="Bank Name is mandatory")
	@Column(nullable = false, length = 50)
	private String bankName;
	
	@UIAction(label = "Country Name",errorlabel="Country Name is mandatory")
	@Column(nullable = false, length = 35)
	private String countryName;

	@UIAction(label = "Bank Code",errorlabel="Bank Code is mandatory")
	@Column(nullable = false, length = 4)
	private String bankCode;	
	
	@UIAction(label = "Country Code",errorlabel="Country Code is mandatory")
	@Column(nullable = false, length = 2)
	private String countryCode;	
	
	@UIAction(label = "Location Code",errorlabel="Location Code is mandatory")
	@Column(nullable = false, length = 2)
	private String locationCode;
	
	@UIAction(label = "Branch Code",errorlabel="Branch Code is mandatory")
	@Column(nullable = true, length = 3)
	private String branchCode = "XXX";
	

	/**
	 * @return the swiftBic
	 */
	public String getSwiftBic()
	{
		return swiftBic;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName()
	{
		return bankName;
	}

	/**
	 * @return the countryName
	 */
	public String getCountryName()
	{
		return countryName;
	}

	/**
	 * @return the bankCode
	 */
	public String getBankCode()
	{
		return bankCode;
	}

	/**
	 * @return the countryCode
	 */
	public String getCountryCode()
	{
		return countryCode;
	}

	/**
	 * @return the locationCode
	 */
	public String getLocationCode()
	{
		return locationCode;
	}

	/**
	 * @return the branchCode
	 */
	public String getBranchCode()
	{
		return branchCode;
	}

	/**
	 * @param swiftBic the swiftBic to set
	 */
	public void setSwiftBic(String swiftBic)
	{
		this.swiftBic = swiftBic;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}

	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName)
	{
		this.countryName = countryName;
	}

	/**
	 * @param bankCode the bankCode to set
	 */
	public void setBankCode(String bankCode)
	{
		this.bankCode = bankCode;
	}

	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode)
	{
		this.countryCode = countryCode;
	}

	/**
	 * @param locationCode the locationCode to set
	 */
	public void setLocationCode(String locationCode)
	{
		this.locationCode = locationCode;
	}

	/**
	 * @param branchCode the branchCode to set
	 */
	public void setBranchCode(String branchCode)
	{
		this.branchCode = branchCode;
	}
}

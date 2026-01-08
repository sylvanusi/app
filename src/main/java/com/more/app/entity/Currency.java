package com.more.app.entity;

import org.hibernate.annotations.Formula;

import com.more.app.util.annotations.Auditable;
import com.more.app.util.annotations.UIAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

@Entity
public class Currency extends AbstractPojo
{
	
	private static final long serialVersionUID = 607497965334953447L;


	@Auditable(enableAudit=true, fieldNo=4)
	@UIAction(label = "Currency",errorlabel="Currency cannot be Empty")
	@Column(nullable = false, length=50)
	private String currency;
	

	@Auditable(enableAudit=true, fieldNo=3)
	@UIAction(label = "Code",errorlabel="Code cannot be Empty")
	@Column(nullable = false, length=3)
	private String code;

	@Auditable(enableAudit=true, fieldNo=5)
	@UIAction(label = "Country",errorlabel="Country Name is required")
	@Column(nullable = false)
	private Long countryId;

	@Auditable(enableAudit=true, fieldNo=6)
	@UIAction(label = "Major Unit")
	@Column(nullable = true, length=3)
	private int numericCode;

	@Auditable(enableAudit=true, fieldNo=7)
	@UIAction(label = "Minor Unit")
	@Column(nullable = true, length=2)
	private int minorUnit;
	
	@UIAction(label = "Country")
	@Formula("(select a.country_Name from Country a where a.id = country_Id)")
	private String countryName;
	
	@Transient
	@UIAction(label = "Country")
	private Country country;
	
	public String getCurrency()
	{
		return currency;
	}
	public void setCurrency(String currency)
	{
		this.currency = currency;
	}
	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}
	public Long getCountryId()
	{
		return countryId;
	}
	public void setCountryId(Long countryId)
	{
		this.countryId = countryId;
	}
	public int getNumericCode()
	{
		return numericCode;
	}
	public void setNumericCode(int numericCode)
	{
		this.numericCode = numericCode;
	}
	public int getMinorUnit()
	{
		return minorUnit;
	}
	public void setMinorUnit(int minorUnit)
	{
		this.minorUnit = minorUnit;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	@Override
	public String toString() {
		return "Currency [currency=" + currency + ", code=" + code + ", countryId=" + countryId + ", numericCode="
				+ numericCode + ", minorUnit=" + minorUnit + ", countryName=" + countryName + ", country=" + country
				+ "]";
	}	
	
	
}

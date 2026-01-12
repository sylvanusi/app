package com.more.app.entity;

import com.more.app.util.annotations.Auditable;
import com.more.app.util.annotations.UIAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Country extends AbstractPojo
{
	private static final long serialVersionUID = 4900430673101737149L;


	public String getCountryName()
	{
		return countryName;
	}
	public void setCountryName(String countryName)
	{
		this.countryName = countryName;
	}

	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}
	
	@Override
	public void setAuditUser(String auditUser)
	{
		this.auditUser = auditUser;
	}
	
	@UIAction(label = "Country Name",errorlabel="Country Name is required")
	@Column(nullable = false, length=50)
	@Auditable(enableAudit=true, fieldNo=4)
	private String countryName;
	

	@UIAction(label = "Country Code",errorlabel="Country Code is required")
	@Column(nullable = false, length=2)
	@Auditable(enableAudit=true, fieldNo=3)
	private String code;
}

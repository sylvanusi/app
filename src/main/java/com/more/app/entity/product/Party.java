package com.more.app.entity.product;

import com.more.app.entity.AbstractPojo;
import com.more.app.entity.Account;
import com.more.app.entity.Country;
import com.more.app.entity.SwiftBic;
import com.more.app.util.annotations.UIAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

@Entity
public class Party extends AbstractPojo
{	
	// account name address1 address2 address3
	@Column(length=34)
	@UIAction(label="Account No")
	private String account;
	
	@Transient
	private Account accountEntity;
	
	@Column(length=34)
	@UIAction(label="BIC Code")
	private String bicCode;
	
	@Transient
	private SwiftBic bicEntity;
	
	@Column(length=34)
	@UIAction(label="Name")
	private String name;
	
	@Column(length=34)
	@UIAction(label="Address Line 1")
	private String address1;
	
	@Column(length=34)
	@UIAction(label="Address Line 2")
	private String address2;
	
	@Column(length=34)
	@UIAction(label="Address Line 3")
	private String address3;
	
	@Column(length=34)
	@UIAction(label="Location")
	private String location;
	
	@Column(length=4)
	@UIAction(label="Identifier Code")
	private String identifierCode;
	
	@Column(length=2)
	@UIAction(label="Country Code")
	private String identifierCountryCode;
	
	@Transient
	private Country countryEntity;
	
	@Column(length=28)
	@UIAction(label="Identifier")
	private String identifier;
	
	
	//A ,F,K
	@Column(length=1)
	@UIAction(label="Party Option")
	private String swiftPartyType;


	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}


	/**
	 * @return the account
	 */
	public String getAccount()
	{
		return account;
	}


	/**
	 * @return the bicCode
	 */
	public String getBicCode()
	{
		return bicCode;
	}


	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}


	/**
	 * @return the address1
	 */
	public String getAddress1()
	{
		return address1;
	}


	/**
	 * @return the address2
	 */
	public String getAddress2()
	{
		return address2;
	}


	/**
	 * @return the address3
	 */
	public String getAddress3()
	{
		return address3;
	}


	/**
	 * @return the location
	 */
	public String getLocation()
	{
		return location;
	}


	/**
	 * @return the identifierCode
	 */
	public String getIdentifierCode()
	{
		return identifierCode;
	}


	/**
	 * @return the identifierCountryCode
	 */
	public String getIdentifierCountryCode()
	{
		return identifierCountryCode;
	}


	/**
	 * @return the identifier
	 */
	public String getIdentifier()
	{
		return identifier;
	}


	/**
	 * @return the swiftPartyType
	 */
	public String getSwiftPartyType()
	{
		return swiftPartyType;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}


	/**
	 * @param account the account to set
	 */
	public void setAccount(String account)
	{
		this.account = account;
	}


	/**
	 * @param bicCode the bicCode to set
	 */
	public void setBicCode(String bicCode)
	{
		this.bicCode = bicCode;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}


	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1)
	{
		this.address1 = address1;
	}


	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2)
	{
		this.address2 = address2;
	}


	/**
	 * @param address3 the address3 to set
	 */
	public void setAddress3(String address3)
	{
		this.address3 = address3;
	}


	/**
	 * @param location the location to set
	 */
	public void setLocation(String location)
	{
		this.location = location;
	}


	/**
	 * @param identifierCode the identifierCode to set
	 */
	public void setIdentifierCode(String identifierCode)
	{
		this.identifierCode = identifierCode;
	}


	/**
	 * @param identifierCountryCode the identifierCountryCode to set
	 */
	public void setIdentifierCountryCode(String identifierCountryCode)
	{
		this.identifierCountryCode = identifierCountryCode;
	}


	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(String identifier)
	{
		this.identifier = identifier;
	}


	/**
	 * @param swiftPartyType the swiftPartyType to set
	 */
	public void setSwiftPartyType(String swiftPartyType)
	{
		this.swiftPartyType = swiftPartyType;
	}


	public Account getAccountEntity() {
		return accountEntity;
	}


	public void setAccountEntity(Account accountEntity) {
		this.accountEntity = accountEntity;
	}


	public SwiftBic getBicEntity() {
		return bicEntity;
	}


	public void setBicEntity(SwiftBic bicEntity) {
		this.bicEntity = bicEntity;
	}


	public Country getCountryEntity() {
		return countryEntity;
	}


	public void setCountryEntity(Country countryEntity) {
		this.countryEntity = countryEntity;
	}


	@Override
	public String toString() {
		return "Party [account=" + account + ", accountEntity=" + accountEntity + ", bicCode=" + bicCode
				+ ", bicEntity=" + bicEntity + ", name=" + name + ", address1=" + address1 + ", address2=" + address2
				+ ", address3=" + address3 + ", location=" + location + ", identifierCode=" + identifierCode
				+ ", identifierCountryCode=" + identifierCountryCode + ", countryEntity=" + countryEntity
				+ ", identifier=" + identifier + ", swiftPartyType=" + swiftPartyType + ", id=" + id + ", auditUser="
				+ auditUser + ", auditTimestamp=" + auditTimestamp + "]";
	}
	
	
}

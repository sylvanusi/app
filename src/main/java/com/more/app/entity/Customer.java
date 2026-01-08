package com.more.app.entity;

import org.hibernate.annotations.Formula;

import com.more.app.util.annotations.UIAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Transient;

@Entity
public class Customer extends AbstractPojo {

	@UIAction(label = "Full Name", errorlabel = "Full Name is mandatory")
	@Column(nullable = false, length = 35)
	private String fullName;

	@UIAction(label = "Customer Number", errorlabel = "Customer Number/Code is mandatory")
	@Column(nullable = false, length = 35, unique = true)
	private String customerNo;

	@UIAction(label = "Alias", errorlabel = "Alias is mandatory")
	@Column(length = 15)
	private String alias;

	@UIAction(label = "Address", errorlabel = "Address is mandatory")
	@Column(nullable = false, length = 35)
	private String address1;
	@Column(length = 35)
	private String address2;
	@Column(length = 35)
	private String address3;
	@Column(length = 35)
	private String address4;

	@UIAction(label = "City", errorlabel = "City is mandatory")
	@Column(length = 35)
	private String city;

	@UIAction(label = "Phone Number", errorlabel = "Phone Number is mandatory")
	@Column(nullable = false, length = 15)
	private String phoneNo;

	@UIAction(label = "Email", errorlabel = "Email is mandatory")
	@Column(nullable = false, length = 35)
	private String email;

	@UIAction(label = "Account Officer", errorlabel = "Account Officer is mandatory")
	@JoinColumn(nullable = true, referencedColumnName = "id")
	private Long accountOfficerId;

	@UIAction(label = "Account Officer", errorlabel = "Account Officer is mandatory")
	@Transient
	private AccountOfficer accountOfficer;

	@UIAction(label = "Account Officer", errorlabel = "Account Officer is mandatory")
	@Formula("(select a.full_Name from Account_Officer a where a.id = account_officer_Id)")
	private String accountOfficerName;

	@UIAction(label = "Customer Type", errorlabel = "Customer Type is mandatory")
	@Column(nullable = false, length = 10)
	private String customerType;

	@UIAction(label = "Correspondent Bank")
	private boolean correspondent;

	@UIAction(label = "Country", errorlabel = "Country is mandatory")
	@Transient
	private Country country;

	@UIAction(label = "Country", errorlabel = "Country is mandatory")
	@JoinColumn(nullable = false, referencedColumnName = "id")
	private Long countryId;

	@UIAction(label = "Country", errorlabel = "Country is mandatory")
	@Formula("(select a.country_name from Country a where a.id = country_Id)")
	private String countryName;

	@UIAction(label = "Swift Address")
	@Column(length = 11)
	private String swiftAddress;

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @return the customerNo
	 */
	public String getCustomerNo() {
		return customerNo;
	}

	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * @return the address3
	 */
	public String getAddress3() {
		return address3;
	}

	/**
	 * @return the address4
	 */
	public String getAddress4() {
		return address4;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the phoneNo
	 */
	public String getPhoneNo() {
		return phoneNo;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the accountOfficer
	 */
	public AccountOfficer getAccountOfficer() {
		return accountOfficer;
	}

	/**
	 * @return the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * @return the correspondent
	 */
	public boolean isCorrespondent() {
		return correspondent;
	}

	/**
	 * @return the country
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * @return the swiftAddress
	 */
	public String getSwiftAddress() {
		return swiftAddress;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @param customerNo the customerNo to set
	 */
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * @param address3 the address3 to set
	 */
	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	/**
	 * @param address4 the address4 to set
	 */
	public void setAddress4(String address4) {
		this.address4 = address4;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @param phoneNo the phoneNo to set
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	/**
	 * @return the accountOfficerName
	 */
	public String getAccountOfficerName() {
		if (null != getAccountOfficer())
			accountOfficerName = getAccountOfficer().getFullName();
		return accountOfficerName;
	}

	/**
	 * @param accountOfficerName the accountOfficerName to set
	 */
	public void setAccountOfficerName(String accountOfficerName) {
		this.accountOfficerName = accountOfficerName;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param accountOfficer the accountOfficer to set
	 */
	public void setAccountOfficer(AccountOfficer accountOfficer) {
		this.accountOfficer = accountOfficer;
	}

	/**
	 * @param customerType the customerType to set
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * @param correspondent the correspondent to set
	 */
	public void setCorrespondent(boolean correspondent) {
		this.correspondent = correspondent;
	}

	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		if (null != getCountry())
			countryName = getCountry().getCountryName();
		return countryName;
	}

	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * @param swiftAddress the swiftAddress to set
	 */
	public void setSwiftAddress(String swiftAddress) {
		this.swiftAddress = swiftAddress;
	}

	public Long getAccountOfficerId() {
		return accountOfficerId;
	}

	public void setAccountOfficerId(Long accountOfficerId) {
		this.accountOfficerId = accountOfficerId;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
}

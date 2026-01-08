package com.more.app.entity;


import org.hibernate.annotations.Formula;

import com.more.app.util.annotations.Auditable;
import com.more.app.util.annotations.UIAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Transient;

@Entity
public class Account extends AbstractPojo {
	
	private static final long serialVersionUID = 1L;

	@UIAction(label = "Customer", errorlabel = "Customer is mandatory")
	@Auditable(enableAudit = true, fieldNo = 4)
	@Transient
	private Customer customer;

	@UIAction(label = "Customer", errorlabel = "Customer is mandatory")
	@JoinColumn(nullable = false, referencedColumnName = "id")
	@Auditable(enableAudit = true, fieldNo = 4)
	private Long customerId;
	
	@UIAction(label = "Customer Name", errorlabel = "Customer Name is mandatory")	
	@Formula("(select a.full_Name from Customer a where a.id = customer_Id)")
	private String customerName;

	@UIAction(label = "Account Type", errorlabel = "Account Type is mandatory")
	@Auditable(enableAudit = true, fieldNo = 5)
	@Transient
	private AccountType accountType;

	@UIAction(label = "Account Type", errorlabel = "Account Type is mandatory")
	@JoinColumn(nullable = false, referencedColumnName = "id")
	@Auditable(enableAudit = true, fieldNo = 5)
	private Long accountTypeId;
	
	@UIAction(label = "Account Type", errorlabel = "Account Type is mandatory")
	@Formula("(select a.code from Account_Type a where a.id = account_Type_Id)")
	private String accountTypeCode;

	@UIAction(label = "Branch", errorlabel = "Branch is mandatory")
	@Auditable(enableAudit = true, fieldNo = 6)
	@Transient
	private Branch branch;
	
	@UIAction(label = "Branch", errorlabel = "Branch is mandatory")
	@JoinColumn(nullable = false, referencedColumnName = "id")
	@Auditable(enableAudit = true, fieldNo = 6)
	private Long branchId;

	@UIAction(label = "Branch Code", errorlabel = "Branch Code is mandatory")
	@Formula("(select a.code from Branch a where a.id = branch_Id)")
	private String branchCode;

	@UIAction(label = "Bank", errorlabel = "Bank Name is mandatory")
	private String bankName;

	@UIAction(label = "Curreny", errorlabel = "Currency is mandatory")
	@Column(nullable = true, length = 3)
	@Auditable(enableAudit = true, fieldNo = 7)
	private String currency;

	@UIAction(label = "Account Name", errorlabel = "Account Name is mandatory")
	@Column(nullable = false, length = 35)
	@Auditable(enableAudit = true, fieldNo = 8)
	private String accountName;

	@UIAction(label = "Account Number", errorlabel = "Account Number is mandatory")
	@Column(nullable = false, length = 34)
	@Auditable(enableAudit = true, fieldNo = 9)
	private String accountNo;

	@UIAction(label = "B/O AccountNo", errorlabel = "B/O Account Number is mandatory")
	@Column(nullable = true, length = 34)
	@Auditable(enableAudit = true, fieldNo = 10)
	private String boAccountNo;

	@UIAction(label = "IBAN", errorlabel = "IBAN Account Number is mandatory")
	@Column(nullable = true, length = 34)
	@Auditable(enableAudit = true, fieldNo = 11)
	private String iban;

	@UIAction(label = "Country", errorlabel = "Country")
	private String country;

	private boolean customerAccount;

	/**
	 * @return the branch
	 */
	public Branch getBranch() {
		return branch;
	}

	/**
	 * @return the branchCode
	 */
	@Transient
	public String getBranchCode() {
		return null == getBranch() ? "" : getBranch().getCode();
	}

	/**
	 * @return the bankName
	 */
	@Transient
	public String getBankName() {
		return null == getBranch() ? "" : getBranch().getBank().getBankGroupParamValue();
	}

	/**
	 * @return the accountType
	 */
	public AccountType getAccountType() {
		return accountType;
	}

	/**
	 * @return the accountTypeCode
	 */
	@Transient
	public String getAccountTypeCode() {
		return null == getAccountType() ? "" : getAccountType().getCode();
	}

	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @return the customerName
	 */
	@Transient
	public String getCustomerName() {

		return null == getCustomer() ? "" : getCustomer().getFullName();
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @return the accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * @return the boAccountNo
	 */
	public String getBoAccountNo() {
		return boAccountNo;
	}

	/**
	 * @return the iban
	 */
	public String getIban() {
		return iban;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param branch the branch to set
	 */
	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	/**
	 * @param branchCode the branchCode to set
	 */
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	/**
	 * @param accountTypeCode the accountTypeCode to set
	 */
	public void setAccountTypeCode(String accountTypeCode) {
		this.accountTypeCode = accountTypeCode;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * @param accountNo the accountNo to set
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * @param boAccountNo the boAccountNo to set
	 */
	public void setBoAccountNo(String boAccountNo) {
		this.boAccountNo = boAccountNo;
	}

	/**
	 * @param iban the iban to set
	 */
	public void setIban(String iban) {
		this.iban = iban;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the customerAccount
	 */
	public boolean isCustomerAccount() {
		return customerAccount;
	}

	/**
	 * @param customerAccount the customerAccount to set
	 */
	public void setCustomerAccount(boolean customerAccount) {
		this.customerAccount = customerAccount;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getAccountTypeId() {
		return accountTypeId;
	}

	public void setAccountTypeId(Long accountTypeId) {
		this.accountTypeId = accountTypeId;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	
	

}

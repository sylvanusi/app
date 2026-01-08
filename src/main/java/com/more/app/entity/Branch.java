package com.more.app.entity;

import org.hibernate.annotations.Formula;

import com.more.app.util.annotations.Auditable;
import com.more.app.util.annotations.UIAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Transient;

@Entity
@NamedQuery(name = "Branch.findAllOrderedDesc", query = "SELECT p FROM Branch p order by p.id desc")

public class Branch extends AbstractPojo
{
	@Auditable(enableAudit=true, fieldNo=5)
	@UIAction(label = "Branch Code",errorlabel="Branch Code is mandatory")
	@Column(nullable = false, length = 10)
	private String code;

	@Auditable(enableAudit=true, fieldNo=4)
	@UIAction(label = "Branch Number",errorlabel="Branch No is mandatory")
	@Column(nullable = false, length = 6)
	private int branchNo;

	@Auditable(enableAudit=true, fieldNo=6)
	@UIAction(label = "Branch Name",errorlabel="Branch Name is mandatory")
	@Column(nullable = false, length = 35)
	private String name;

	@Auditable(enableAudit=true, fieldNo=8)
	@UIAction(label = "Branch Address",errorlabel="Branch Address is mandatory")
	@Column(nullable = false, length = 140)
	private String address;

	// true Can Authorize trade and accounts can be posted into
	// false cannot autorize trade and accounts cannot be posted into
	@Auditable(enableAudit=true, fieldNo=4)
	@UIAction(label = "Trade Status")
	@Column(nullable = true, length = 1)
	private String tradeStatus;

	// Opened or Closed for transactions
	// Open 0, Closed C
	@Auditable(enableAudit=true, fieldNo=4)
	@UIAction(label = "Transaction Status")
	@Column(nullable = true, length = 1)
	private String txnStatus;

	@Auditable(enableAudit=true, fieldNo=12)
	@UIAction(label = "Use Default Swift")
	private boolean useDefaultSwift;

	@Auditable(enableAudit=true, fieldNo=13)
	@UIAction(label = "Swift Address")
	@Column(nullable = true, length = 11)
	private String swiftAddress;

	// code, branchNo, name, address, tradeStatus, txnStatus, useDefaultSwift,
	// swiftAddress

	@Auditable(enableAudit=true, fieldNo=4)
	@UIAction(label = "Bank Object",errorlabel="Bank Name is required")
	@Transient
	private Bank bank;
	
	@Auditable(enableAudit=true, fieldNo=4)
	@UIAction(label = "Bank Object",errorlabel="Bank Name is required")
	@JoinColumn(referencedColumnName = "id", nullable = false)
	private Long bankId;

	@UIAction(label = "Bank Name")
	@Formula("(select a.name from Bank a where a.id = bank_Id)")
	private String bankName;

	@UIAction(label = "Bank Code")
	@Transient
	private String bankCode;

	@Auditable(enableAudit=true, fieldNo=7)
	@UIAction(label = "Accounting Number")
	@Column(length = 4)
	private String accountingNo;

	@Auditable(enableAudit=true, fieldNo=9)
	@UIAction(label = "City")
	@Column(length = 35)
	private String city;

	@Auditable(enableAudit=true, fieldNo=10)
	@UIAction(label = "Email")
	@Column(length = 35)
	private String email;

	@Auditable(enableAudit=true, fieldNo=11)
	@UIAction(label = "Phone Number")
	@Column(length = 15)
	private String phoneNo;
	
	/**
	 * @return the accountingNo
	 */
	public String getAccountingNo()
	{
		return accountingNo;
	}

	/**
	 * @return the city
	 */
	public String getCity()
	{
		return city;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @return the phoneNo
	 */
	public String getPhoneNo()
	{
		return phoneNo;
	}

	/**
	 * @param accountingNo the accountingNo to set
	 */
	public void setAccountingNo(String accountingNo)
	{
		this.accountingNo = accountingNo;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city)
	{
		this.city = city;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * @param phoneNo the phoneNo to set
	 */
	public void setPhoneNo(String phoneNo)
	{
		this.phoneNo = phoneNo;
	}

	/**
	 * @return the code
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * @return the branchNo
	 */
	public int getBranchNo()
	{
		return branchNo;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return the address
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * @return the tradeStatus
	 */
	public String getTradeStatus()
	{
		return tradeStatus;
	}

	/**
	 * @return the txnStatus
	 */
	public String getTxnStatus()
	{
		return txnStatus;
	}

	/**
	 * @return the useDefaultSwift
	 */
	public boolean isUseDefaultSwift()
	{
		return useDefaultSwift;
	}

	/**
	 * @return the swiftAddress
	 */
	public String getSwiftAddress()
	{
		return swiftAddress;
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
			return this.getBank().getName();
		else
			return bankName;
	}

	/**
	 * @return the bankCode
	 */
	public String getBankCode()
	{
		return this.getBank().getCode();
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code)
	{
		this.code = code;
	}

	/**
	 * @param branchNo the branchNo to set
	 */
	public void setBranchNo(int branchNo)
	{
		this.branchNo = branchNo;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address)
	{
		this.address = address;
	}

	/**
	 * @param tradeStatus the tradeStatus to set
	 */
	public void setTradeStatus(String tradeStatus)
	{
		this.tradeStatus = tradeStatus;
	}

	/**
	 * @param txnStatus the txnStatus to set
	 */
	public void setTxnStatus(String txnStatus)
	{
		this.txnStatus = txnStatus;
	}

	/**
	 * @param useDefaultSwift the useDefaultSwift to set
	 */
	public void setUseDefaultSwift(boolean useDefaultSwift)
	{
		this.useDefaultSwift = useDefaultSwift;
	}

	/**
	 * @param swiftAddress the swiftAddress to set
	 */
	public void setSwiftAddress(String swiftAddress)
	{
		this.swiftAddress = swiftAddress;
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
	 * @param bankCode the bankCode to set
	 */
	public void setBankCode(String bankCode)
	{
		this.bankCode = bankCode;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	
}

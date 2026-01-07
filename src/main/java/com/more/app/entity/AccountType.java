package com.more.app.entity;

import com.more.app.annotations.Auditable;
import com.more.app.annotations.UIAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;

@Entity
public class AccountType extends AbstractPojo
{
	
	@Auditable(enableAudit=true, fieldNo=4)
	@UIAction(label = "Account Type Code",errorlabel="Account Type Code is required")
	@Column(nullable = false, length = 10)
	private String code;
	
	@Auditable(enableAudit=true, fieldNo=5)
	@UIAction(label = "Description",errorlabel="Description is required")
	@Column(nullable = false, length = 35)
	private String description;
	
	@Auditable(enableAudit=true, fieldNo=6)
	@UIAction(label = "Account Role",errorlabel="Account Role is required")
	@Column(nullable = false, length = 35)
	private String accountRole;

	@Auditable(enableAudit=true, fieldNo=7)
	@UIAction(label = "Allow Direct Posting",errorlabel="Allow Direct Posting")
	@Column(length = 1)
	private boolean directPosting;

	/**
	 * @return the code
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code)
	{
		this.code = code;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return the accountRole
	 */
	public String getAccountRole()
	{
		return accountRole;
	}

	/**
	 * @param accountRole the accountRole to set
	 */
	public void setAccountRole(String accountRole)
	{
		this.accountRole = accountRole;
	}

	/**
	 * @return the directPosting
	 */
	public boolean isDirectPosting()
	{
		return directPosting;
	}

	/**
	 * @param directPosting the directPosting to set
	 */
	public void setDirectPosting(boolean directPosting)
	{
		this.directPosting = directPosting;
	}
}

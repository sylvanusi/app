package com.more.app.entity;

import com.more.app.annotations.Auditable;
import com.more.app.annotations.UIAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class AccountOfficer extends AbstractPojo {
	@UIAction(label = "Full Name", errorlabel = "Full Name is mandatory")
	@Column(nullable = false, length = 35)
	@Auditable(enableAudit = true, fieldNo = 3)
	private String fullName;

	@UIAction(label = "Address", errorlabel = "Address is mandatory")
	@Column(nullable = false, length = 140)
	@Auditable(enableAudit = true, fieldNo = 4)
	private String address;

	@UIAction(label = "Department", errorlabel = "Department is mandatory")
	@Column(nullable = true, length = 35)
	@Auditable(enableAudit = true, fieldNo = 5)
	private String department;

	@UIAction(label = "Email", errorlabel = "Email is mandatory")
	@Column(nullable = false, length = 35)
	@Auditable(enableAudit = true, fieldNo = 6)
	private String email;

	@UIAction(label = "Phone Number", errorlabel = "Phone Number is mandatory")
	@Column(nullable = false, length = 15)
	@Auditable(enableAudit = true, fieldNo = 7)
	private String phoneNo;

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the phoneNo
	 */
	public String getPhoneNo() {
		return phoneNo;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param phoneNo the phoneNo to set
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
}

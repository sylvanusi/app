package com.more.app.entity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import org.hibernate.annotations.Formula;

import com.more.app.annotations.UIAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

@Entity
public class Holiday extends AbstractPojo {
	@UIAction(label = "Holiday Title", errorlabel = "Title is required")
	@Column(nullable = false, unique = true, length = 50)
	private String title;

	@UIAction(label = "Date", errorlabel = "Date is required")
	@Column(nullable = false)
	private LocalDate holidayDate;

	@UIAction(label = "Date")
	@Transient
	private String holidayDateString;

	@UIAction(label = "Recurring")
	private boolean recurring;

	@UIAction(label = "Branch Holiday")
	private boolean branchHoliday;

	@UIAction(label = "Branch")
	@Transient
	private Branch branch;

	@UIAction(label = "Branch")
	private String branchId;

	@UIAction(label = "Branch Name")
	@Formula("(select a.name from branch a where a.id = branch_Id)")
	private String branchName;

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the holidayDate
	 */
	public LocalDate getHolidayDate() {
		return holidayDate;
	}

	/**
	 * @return the recurring
	 */
	public boolean isRecurring() {
		return recurring;
	}

	/**
	 * @return the branchHoliday
	 */
	public boolean isBranchHoliday() {
		return branchHoliday;
	}

	/**
	 * @return the branch
	 */
	public Branch getBranch() {
		return branch;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param holidayDate the holidayDate to set
	 */
	public void setHolidayDate(LocalDate holidayDate) {
		this.holidayDate = holidayDate;
	}

	/**
	 * @param recurring the recurring to set
	 */
	public void setRecurring(boolean recurring) {
		this.recurring = recurring;
	}

	/**
	 * @param branchHoliday the branchHoliday to set
	 */
	public void setBranchHoliday(boolean branchHoliday) {
		this.branchHoliday = branchHoliday;
	}

	/**
	 * @param branch the branch to set
	 */
	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		if (null != getBranch())
			branchName = getBranch().getName();
		return branchName;
	}

	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * @return the holidayDateString
	 */
	public String getHolidayDateString() {
		if (null != getHolidayDate())
			holidayDateString = getHolidayDate().toString();
		return holidayDateString;
	}

	/**
	 * @param holidayDateString the holidayDateString to set
	 */
	public void setHolidayDateString(String holidayDateString) {
		this.holidayDateString = holidayDateString;
	}
}

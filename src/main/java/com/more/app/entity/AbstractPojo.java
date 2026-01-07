package com.more.app.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;

@MappedSuperclass
public abstract class AbstractPojo implements Serializable {
	private static final long serialVersionUID = -7289994339186082141L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	//@Temporal(TemporalType.DATE)
	private LocalDate systemCreateDate = LocalDate.now();

	@SuppressWarnings("deprecation")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime systemCreateTimeStamp = LocalDateTime.now();

	@Column(name = "AUDIT_USER")
	public String auditUser;

	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AUDIT_TIMESTAMP")
	protected Calendar auditTimestamp;

	public String getAuditUser() {
		return this.auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	public Calendar getAuditTimestamp() {
		return this.auditTimestamp;
	}

	public void setAuditTimestamp(Calendar auditTimestamp) {
		this.auditTimestamp = auditTimestamp;
	}

	@PrePersist
	@PreUpdate
	public void updateAuditInfo() {
		// setAuditUser(null != CurrentUser.get() ? CurrentUser.get() : getAuditUser());
		setAuditTimestamp(Calendar.getInstance());
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getSystemCreateDate() {
		return this.systemCreateDate;
	}

	public void setSystemCreateDate(LocalDate systemCreateDate) {
		this.systemCreateDate = systemCreateDate;
	}

	public LocalDateTime getSystemCreateTimeStamp() {
		return this.systemCreateTimeStamp;
	}

	public void setSystemCreateTimeStamp(LocalDateTime systemCreateTimeStamp) {
		this.systemCreateTimeStamp = systemCreateTimeStamp;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
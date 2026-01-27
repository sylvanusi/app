package com.more.app.entity.product;

import com.more.app.entity.AbstractPojo;

import jakarta.persistence.Entity;

@Entity
public class LcMaster extends AbstractPojo {
	private static final long serialVersionUID = -3559711307824157622L;

	private Long registerId;
	
	
	private String eventStatus;


	public Long getRegisterId() {
		return registerId;
	}

	public void setRegisterId(Long registerId) {
		this.registerId = registerId;
	}

	

}

package com.more.app.entity;

import com.more.app.util.annotations.Auditable;
import com.more.app.util.annotations.UIAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class ProductModule extends AbstractPojo
{
	private static final long serialVersionUID = 1233419975551454813L;

	@UIAction(label = "Code",errorlabel="Code is mandatory")
	@Column(nullable = false, length =8, unique=true)
	@Auditable(enableAudit=true, fieldNo=4)
	private String code;
	
	@UIAction(label = "Module Name",errorlabel="Module Name is mandatory")
	@Column(nullable = false, length =35, unique=true)
	@Auditable(enableAudit=true, fieldNo=4)
	private String name;

	/**
	 * @return the code
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code)
	{
		this.code = code;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
}

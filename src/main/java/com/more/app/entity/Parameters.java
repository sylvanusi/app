package com.more.app.entity;

import com.more.app.annotations.UIAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQuery(
	    name = "Parameters.findAllOrderedDesc",
	    query = "SELECT p FROM Parameters p order by p.id desc")
public class Parameters extends AbstractPojo
{
	private static final long serialVersionUID = 4209382967233231423L;
	
	@UIAction(label = "Code",errorlabel="Code is required")
	@Column(nullable = false, length=5)
	private String paramCode;
	
	@UIAction(label = "Value",errorlabel="Value is required")
	@Column(nullable = false, length=35)
	private String paramValue;
	
	@UIAction(label = "Description",errorlabel="Description is required")
	@Column(nullable = false, length=140)
	private String paramDescription;

	/**
	 * @return the paramCode
	 */
	public String getParamCode()
	{
		return paramCode;
	}

	/**
	 * @return the paramValue
	 */
	public String getParamValue()
	{
		return paramValue;
	}

	/**
	 * @param paramCode the paramCode to set
	 */
	public void setParamCode(String paramCode)
	{
		this.paramCode = paramCode;
	}

	/**
	 * @param paramValue the paramValue to set
	 */
	public void setParamValue(String paramValue)
	{
		this.paramValue = paramValue;
	}

	/**
	 * @return the paramDescription
	 */
	public String getParamDescription()
	{
		return paramDescription;
	}

	/**
	 * @param paramDescription the paramDescription to set
	 */
	public void setParamDescription(String paramDescription)
	{
		this.paramDescription = paramDescription;
	}

}

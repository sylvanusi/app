package com.more.app.entity;

import com.more.app.annotations.Auditable;
import com.more.app.annotations.UIAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Transient;

@Entity
public class ProductType extends AbstractPojo
{
	private static final long serialVersionUID = 1233419975551454813L;

	@UIAction(label = "Code",errorlabel="Code is mandatory")
	@Column(nullable = false, length =8, unique=true)
	@Auditable(enableAudit=true, fieldNo=4)
	private String code;
	
	@UIAction(label = "Product Type",errorlabel="Product Type is mandatory")
	@Column(nullable = false, length =35, unique=true)
	@Auditable(enableAudit=true, fieldNo=5)
	private String productType;
	
	@Auditable(enableAudit=true, fieldNo=6)
	@UIAction(label = "Module",errorlabel="Module is required")
	@JoinColumn(referencedColumnName = "id", nullable = false)
	private ProductModule module;
	
	@UIAction(label = "Module",errorlabel="Module is required")
	@Transient
	private String moduleName;

	/**
	 * @return the code
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * @return the productType
	 */
	public String getProductType()
	{
		return productType;
	}

	/**
	 * @return the module
	 */
	public ProductModule getModule()
	{
		return module;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code)
	{
		this.code = code;
	}

	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType)
	{
		this.productType = productType;
	}

	/**
	 * @param module the module to set
	 */
	public void setModule(ProductModule module)
	{
		this.module = module;
	}

	/**
	 * @return the moduleName
	 */
	public String getModuleName()
	{
		if(null != getModule())
			moduleName = getModule().getName();
		return moduleName;
	}

	/**
	 * @param moduleName the moduleName to set
	 */
	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}

	
}

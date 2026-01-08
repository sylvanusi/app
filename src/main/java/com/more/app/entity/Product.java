package com.more.app.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.more.app.util.annotations.Auditable;
import com.more.app.util.annotations.UIAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

@Entity
@NamedQuery(
	    name = "Product.findAllOrderedDesc",
	    query = "SELECT p FROM Product p order by p.productCode asc")
public class Product extends AbstractPojo
{
	@Auditable(enableAudit=true, fieldNo=6)
	@UIAction(label = "Product Code",errorlabel="Product Code is mandatory")
	@Column(nullable = false, length =8, unique=true)
	private String productCode;
	
	@Auditable(enableAudit=true, fieldNo=7)
	@UIAction(label = "Product Name",errorlabel="Product Name is mandatory")
	@Column(nullable = false, length =35, unique=true)
	private String productName;

	@Auditable(enableAudit=true, fieldNo=8)
	@UIAction(label = "Description",errorlabel="Description is mandatory")
	@Column(nullable = false, length =140, unique=true)
	private String description;
	
	@Auditable(enableAudit=true, fieldNo=9)
	@UIAction(label = "Product Type",errorlabel="Product Type is required")
	@JoinColumn(referencedColumnName = "id", nullable = false)
	private ProductType type;
	
	@UIAction(label = "Module",errorlabel="Module is required")
	@Transient 
	private ProductModule module;
	
	@Auditable(enableAudit=true, fieldNo=10)
	@UIAction(label = "Status",errorlabel="Status is mandatory")
	@Column(nullable = true, length =1)
	private String status = "I";
	
	@OneToMany(cascade = CascadeType.ALL)
	private Set<ProductEventCheckList> productEventCheckList = new HashSet<>();
	
	@ElementCollection
	private List<String> allowedCurrencies = new ArrayList<>();

	public String getProductCode()
	{
		return productCode;
	}

	public String getProductName()
	{
		return productName;
	}

	public String getDescription()
	{
		return description;
	}

	public ProductType getType()
	{
		return type;
	}

	public ProductModule getModule()
	{
		if(null != type)
			return type.getModule();
		return module;
	}

	public String getStatus()
	{
		return status;
	}

	public Set<ProductEventCheckList> getProductEventCheckList()
	{
		return productEventCheckList;
	}

	public void setProductCode(String productCode)
	{
		this.productCode = productCode;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setType(ProductType type)
	{
		this.type = type;
	}

	public void setModule(ProductModule module)
	{
		this.module = (null != type) ? type.getModule() : module;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}
	
	public void setProductEventCheckList(Set<ProductEventCheckList> productEventCheckList)
	{
		this.productEventCheckList = productEventCheckList;
	}

	public List<String> getAllowedCurrencies() {
		return allowedCurrencies;
	}

	public void setAllowedCurrencies(List<String> allowedCurrencies) {
		this.allowedCurrencies = allowedCurrencies;
	}
}

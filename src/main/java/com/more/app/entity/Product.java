package com.more.app.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.Formula;

import com.more.app.util.annotations.Auditable;
import com.more.app.util.annotations.UIAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

@Entity
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
	@Transient
	private ProductType type;
	
	@Auditable(enableAudit=true, fieldNo=9)
	@UIAction(label = "Product Type",errorlabel="Product Type is required")
	@JoinColumn(referencedColumnName = "id", nullable = false)
	private Long typeId;
	
	@UIAction(label = "Product Type",errorlabel="Product Type is required")
	@Formula("(select a.product_Type from Product_Type a where a.id = type_Id)")
	private String typeCode;
	
	@UIAction(label = "Module",errorlabel="Module is required")
	@Transient 
	private ProductModule module;
	
	@UIAction(label = "Module",errorlabel="Module is required")
	@Formula("(select a.code from Product_Module a, Product_Type b where a.id = b.module_Id and b.id = type_Id)")
	private String moduleCode;
	
	@Auditable(enableAudit=true, fieldNo=10)
	@UIAction(label = "Status",errorlabel="Status is mandatory")
	@Column(nullable = true, length =1)
	private String status = "I";
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<ProductEventCheckList> productEventCheckList = new HashSet<>();
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> allowedCurrencies = new ArrayList<>();
	
	@Auditable(enableAudit=true, fieldNo=11)
	@UIAction(label = "Last Sequence No",errorlabel="Last Sequence No is mandatory")
	@Column(nullable = true, length =16)
	private Long lastSequenceNumber = 0L;
	
	@Auditable(enableAudit=true, fieldNo=11)
	@UIAction(label = "Reference Length",errorlabel="Reference Length No is mandatory")
	@Column(nullable = false)
	private int referenceLength = 0;

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

	public Long getLastSequenceNumber() {
		return lastSequenceNumber;
	}

	public void setLastSequenceNumber(Long lastSequenceNumber) {
		this.lastSequenceNumber = lastSequenceNumber;
	}

	public int getReferenceLength() {
		return referenceLength;
	}

	public void setReferenceLength(int referenceLength) {
		this.referenceLength = referenceLength;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
}

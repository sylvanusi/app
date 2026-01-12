package com.more.app.entity;

import java.util.Objects;

import com.more.app.util.annotations.Auditable;
import com.more.app.util.annotations.UIAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Transient;

@Entity
public class ProductEventChargeDefination extends AbstractPojo
{
	@UIAction(label = "Product", errorlabel = "Product is mandatory")
	@JoinColumn(nullable = false, referencedColumnName = "id")
	@Auditable(enableAudit = true, fieldNo = 4)
	private Product product;
	
	@UIAction(label = "Event", errorlabel = "Event is mandatory")
	@JoinColumn(nullable = false, referencedColumnName = "id")
	@Auditable(enableAudit = true, fieldNo = 5)
	private ProductTypeEvent event;
	
	@UIAction(label = "Charge", errorlabel = "Charge is mandatory")
	@JoinColumn(nullable = false, referencedColumnName = "id")
	@Auditable(enableAudit = true, fieldNo = 6)
	private ChargeDefination chargeDefination;
	
	@UIAction(label = "Charge Name", errorlabel = "Charge Name is mandatory")
	@Auditable(enableAudit = true, fieldNo = 10)
	@Transient
	private String chargeName;

	@UIAction(label = "Charge Code", errorlabel = "Charge Code is mandatory")
	@Auditable(enableAudit = true, fieldNo = 11)
	@Transient
	private String chargeCode;

	@UIAction(label = "Basis Amount", errorlabel = "Basis Amount is mandatory")
	@Column(nullable = true)
	@Auditable(enableAudit = true, fieldNo = 7)
	private String basisAmount;
	
	@UIAction(label = "Charge From", errorlabel = "Charge From is mandatory")
	@Column(nullable = true)
	@Auditable(enableAudit = true, fieldNo = 8)
	private String chargeFromField;
	
	@UIAction(label = "Charge To", errorlabel = "Charge To is mandatory")
	@Column(nullable = true)
	@Auditable(enableAudit = true, fieldNo = 9)
	private String chargeToField;

	public Product getProduct()
	{
		return product;
	}

	public ProductTypeEvent getEvent()
	{
		return event;
	}

	public ChargeDefination getChargeDefination()
	{
		return chargeDefination;
	}

	public String getBasisAmount()
	{
		return basisAmount;
	}

	public String getChargeFromField()
	{
		return chargeFromField;
	}

	public String getChargeToField()
	{
		return chargeToField;
	}

	public void setProduct(Product product)
	{
		this.product = product;
	}

	public void setEvent(ProductTypeEvent event)
	{
		this.event = event;
	}

	public void setChargeDefination(ChargeDefination chargeDefination)
	{
		this.chargeDefination = chargeDefination;
	}

	public void setBasisAmount(String basisAmount)
	{
		this.basisAmount = basisAmount;
	}

	public void setChargeFromField(String chargeFromField)
	{
		this.chargeFromField = chargeFromField;
	}

	public void setChargeToField(String chargeToField)
	{
		this.chargeToField = chargeToField;
	}

	public String getChargeName()
	{
		return Objects.nonNull(getChargeDefination()) ? getChargeDefination().getChargeName() : chargeName;
	}

	public String getChargeCode()
	{
		return Objects.nonNull(getChargeDefination()) ? getChargeDefination().getChargeCode() :chargeCode;
	}

	public void setChargeName(String chargeName)
	{
		this.chargeName = chargeName;
	}

	public void setChargeCode(String chargeCode)
	{
		this.chargeCode = chargeCode;
	}	
}

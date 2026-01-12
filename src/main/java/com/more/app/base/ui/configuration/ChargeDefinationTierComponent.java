package com.more.app.base.ui.configuration;

import java.util.ArrayList;
import java.util.List;

import com.more.app.entity.ChargeDefination;
import com.more.app.entity.ChargeDefinationTier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;

public class ChargeDefinationTierComponent extends VerticalLayout
{
	private static final long serialVersionUID = 5729344163082485892L;
	private Div tierItemsDiv = new Div();
	private List<TierComponent> tierComponentList = new ArrayList<>();
	private Button addbtn = new Button("+ Add");
	private ChargeDefination chargeDefination;
	private H1 titlelabel = new H1("Tiers");

	public ChargeDefinationTierComponent()
	{
		titlelabel.getElement().getStyle().set("font-weight", "bold");

		add(titlelabel, tierItemsDiv, addbtn);

		addbtn.addClickListener(event ->
		{
			addButtonAction();
		});

		setMargin(false);
		setPadding(true);
		setWidthFull();
	}

	public void setChargeDefinationTierComponent(ChargeDefination chargeDefination)
	{
		this.chargeDefination = chargeDefination;

		if (null != chargeDefination)
		{
			if (chargeDefination.getChargeTiers().isEmpty())
				addButtonAction();
			if (!chargeDefination.getChargeTiers().isEmpty())
				addButtonAction(chargeDefination);
		}
	}

	class TierComponent extends HorizontalLayout
	{
		private static final long serialVersionUID = 1L;
		private ChargeDefinationTier entity;
		private Binder<ChargeDefinationTier> binder = new Binder<>(ChargeDefinationTier.class);
		private TierComponent tierField;

		Button removeBtn = new Button(new Icon(VaadinIcon.MINUS_CIRCLE_O));
		private NumberField lowerLimit = new NumberField();
		private NumberField upperLimit = new NumberField();
		private NumberField chargeAmount = new NumberField();
		private NumberField percent = new NumberField();
		private NumberField minTierAmount = new NumberField();
		private NumberField maxTierAmount = new NumberField();

		public TierComponent(ChargeDefinationTier entity)
		{
			this.entity = entity;
			this.tierField = this;
			tierComponentList.add(tierField);
			binder.setBean(entity);
			setWidthFull();
			System.out.println("UPPER LIMIT " + binder.getBean().getUpperLimitAmount());

			lowerLimit.setWidthFull();
			upperLimit.setWidthFull();
			chargeAmount.setWidthFull();
			minTierAmount.setWidthFull();
			maxTierAmount.setWidthFull();

			add(removeBtn, lowerLimit, upperLimit, chargeAmount, percent, minTierAmount, maxTierAmount);

			removeBtn.addClickListener(event -> removeButtonAction());

			binder.forField(lowerLimit).withValidator(val -> {
				if (val == null || upperLimit.getValue() == null)
					return true;
				else {
					if (val.compareTo(upperLimit.getValue()) > 0)
						return false;
					else
						return true;
				}
			}, "Cannot be greater than Upper Limit Amount").bind(ChargeDefinationTier::getLowerLimitAmount,
					ChargeDefinationTier::setLowerLimitAmount);
			binder.forField(upperLimit).withValidator(val -> {
				if (val == null || lowerLimit.getValue() == null)
					return true;
				else {
					if (val.compareTo(lowerLimit.getValue()) <= 0)
						return false;
					else
						return true;
				}
			}, "Cannot be less than Lower Limit Amount").bind(ChargeDefinationTier::getUpperLimitAmount,
					ChargeDefinationTier::setUpperLimitAmount);
			binder.forField(chargeAmount).bind(ChargeDefinationTier::getChargeAmount,
					ChargeDefinationTier::setChargeAmount);
			binder.forField(percent).bind(ChargeDefinationTier::getChargePercent,
					ChargeDefinationTier::setChargePercent);
			binder.forField(minTierAmount).withValidator(val -> {
				if (val == null || maxTierAmount.getValue() == null)
					return true;
				else {
					if (val.compareTo(maxTierAmount.getValue()) > 0)
						return false;
					else
						return true;
				}
			}, "Cannot be greater than Maximum Amount").bind(ChargeDefinationTier::getMinTierChargeAmount,
					ChargeDefinationTier::setMinTierChargeAmount);
			binder.forField(maxTierAmount).withValidator(val -> {
				if (val == null || minTierAmount.getValue() == null)
					return true;
				else {
					if (val.compareTo(minTierAmount.getValue()) <= 0)
						return false;
					else
						return true;
				}
			}, "Cannot be less than Minimum Amount").bind(ChargeDefinationTier::getMaxTierChargeAmount,
					ChargeDefinationTier::setMaxTierChargeAmount);

			if (null != chargeDefination && "Amount".equals(chargeDefination.getCalculationMethod()))
				percent.setVisible(false);
			if (null != chargeDefination && "Percent".equals(chargeDefination.getCalculationMethod()))
				chargeAmount.setVisible(false);
			tierItemsDiv.add(tierField);
		}

		private void setLabels(TierComponent tierComponet)
		{
			tierComponet.lowerLimit.setLabel("Lower Limit");
			tierComponet.upperLimit.setLabel("Upper Limit");
			tierComponet.chargeAmount.setLabel("Charge Amount");
			tierComponet.percent.setLabel("Percentage %");
			tierComponet.minTierAmount.setLabel("Min Tier Amount");
			tierComponet.maxTierAmount.setLabel("Max Tier Amount");
			removeBtn.setEnabled(false);
			tierComponet.setVerticalComponentAlignment(Alignment.BASELINE, removeBtn, lowerLimit, upperLimit,
					chargeAmount, percent, minTierAmount, maxTierAmount);
		}

		private void removeButtonAction()
		{
			chargeDefination.getChargeTiers().remove(entity);
			tierItemsDiv.remove(tierField);
			tierComponentList.remove(tierField);
			if (!tierComponentList.isEmpty())
				tierComponentList.get(0).setLabels(tierComponentList.get(0));
		}
	}

	private void addButtonAction()
	{
		ChargeDefinationTier tier = new ChargeDefinationTier();
		chargeDefination.getChargeTiers().add(tier);
		new TierComponent(tier);
		tierComponentList.get(0).setLabels(tierComponentList.get(0));
	}

	void addButtonAction(ChargeDefination chargeDefination)
	{
		this.chargeDefination = chargeDefination;
		tierComponentList.clear();
		tierItemsDiv.removeAll();
		for (ChargeDefinationTier tier : chargeDefination.getChargeTiers())
		{
			new TierComponent(tier);
		}
		tierComponentList.get(0).setLabels(tierComponentList.get(0));
	}

	void doCalculationTypeAction(ChargeDefination chargeDefination)
	{
		titlelabel.setVisible(true);
		addbtn.setVisible(true);
		tierItemsDiv.setVisible(true);
		setChargeDefinationTierComponent(chargeDefination);
		if ("Amount".equals(chargeDefination.getCalculationMethod()))
		{
			for (TierComponent comp : tierComponentList)
			{
				comp.percent.setVisible(false);
				comp.minTierAmount.setVisible(false);
				comp.maxTierAmount.setVisible(false);
				comp.chargeAmount.setVisible(true);
			}
		}
		if ("Percent".equals(chargeDefination.getCalculationMethod()))
		{
			tierComponentList.forEach(comp ->
			{
				comp.chargeAmount.setVisible(false);
				comp.percent.setVisible(true);
				comp.minTierAmount.setVisible(true);
				comp.maxTierAmount.setVisible(true);
			});
		}

	}

	void doClearTierFields()
	{
		tierItemsDiv.removeAll();
		tierComponentList.clear();
		if (null != chargeDefination)
			chargeDefination.getChargeTiers().clear();
		tierItemsDiv.setVisible(false);
		titlelabel.setVisible(false);
		addbtn.setVisible(false);
	}
}

package com.more.app.base.ui.configuration;

import java.util.ArrayList;
import java.util.List;

import com.more.app.base.ui.DialogSelectEntity;
import com.more.app.base.ui.setup.CurrencyView;
import com.more.app.entity.AbstractPojo;
import com.more.app.entity.Currency;
import com.more.app.repository.CurrencyRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class AllowedCurrencyComponent extends VerticalLayout 
{
	private static final long serialVersionUID = 1L;
	private List<AllowedCurrencyItem> fieldList = new ArrayList<>();
	private H5 titlelabel = new H5("Allowed Currency");
	private Button addbtn = new Button("+ Add");
	private Div itemsDiv = new Div();
	private CurrencyRepository currencyRepo;

	public AllowedCurrencyComponent(CurrencyRepository currencyRepo) {
		this.currencyRepo = currencyRepo;
		titlelabel.getElement().getStyle().set("font-weight", "bold");

		add(titlelabel, itemsDiv, addbtn);

		addbtn.addClickListener(event ->
		{
			addButtonAction("");
		});

		setSpacing(true);
		setMargin(true);
		setPadding(true);
		setWidthFull();
	}
	
	public void setAllowedCurrency(List<String> ccies)
	{
		addButtonAction(ccies);
	}
	
	public List<String> getAllowedCurrency()
	{
		if(!fieldList.isEmpty())
		{
			List<String> ccies = new ArrayList<>();
			for(AllowedCurrencyItem field: fieldList)
			{
				ccies.add(field.currencyTF.getValue());
			}
			return ccies;
		}
		return new ArrayList<>();
	}

	private void addButtonAction(String ccy)
	{
		new AllowedCurrencyItem(ccy);
		fieldList.get(0).setLabels(fieldList.get(0));
	}
	private void addButtonAction(List<String> ccies)
	{
		fieldList.clear();
		itemsDiv.removeAll();
		for (String ccy : ccies)
		{
			new AllowedCurrencyItem(ccy);
		}
		if(!fieldList.isEmpty())
		fieldList.get(0).setLabels(fieldList.get(0));
	}

	class AllowedCurrencyItem extends HorizontalLayout implements DialogSelectEntity {
		private static final long serialVersionUID = 1L;
		Button removeBtn = new Button(new Icon(VaadinIcon.MINUS_CIRCLE_O));
		private TextField currencyTF = new TextField();
		
		private AllowedCurrencyItem field;

		public AllowedCurrencyItem(String ccy) 
		{
			this.field = this;
			fieldList.add(field);
			currencyTF = new TextField();
			currencyTF.setMaxLength(50);
			currencyTF.setWidth("350px");
			currencyTF.setRequired(true);
			currencyTF.setRequiredIndicatorVisible(true);
			if(ccy != null && !ccy.isEmpty())
				currencyTF.setValue(ccy);
			
			add(removeBtn, currencyTF, getCurrency);
			setVerticalComponentAlignment(Alignment.BASELINE, removeBtn, currencyTF, getCurrency);
			itemsDiv.add(field);
			removeBtn.addClickListener(event -> removeButtonAction());

			setMargin(true);
			setSpacing(true);
		}

		Button getCurrency = new Button("Get Currency", new Icon(VaadinIcon.SEARCH), event -> {
			Dialog dg = new Dialog();
			
			CurrencyView cv = new CurrencyView(field, dg, getCurrencyRepo());
			dg.add(cv);
			dg.open();
		});

		public void setSelectedEntity(AbstractPojo pojo) {

			if (pojo instanceof Currency)
			{
				currencyTF.setValue(((Currency) pojo).getCode());
				currencyTF.setReadOnly(true);
			}
		}
		
		private void setLabels(AllowedCurrencyItem field)
		{
			field.currencyTF.setLabel("Currency");
		}
		
		private void removeButtonAction()
		{
			itemsDiv.remove(field);
			fieldList.remove(field);
			if (!fieldList.isEmpty())
				fieldList.get(0).setLabels(fieldList.get(0));
		}

	}

	public CurrencyRepository getCurrencyRepo() {
		return currencyRepo;
	}

	public void setCurrencyRepo(CurrencyRepository currencyRepo) {
		this.currencyRepo = currencyRepo;
	}
	
	
	
}

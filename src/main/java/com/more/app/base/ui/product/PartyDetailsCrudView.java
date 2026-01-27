package com.more.app.base.ui.product;

import com.more.app.entity.product.Party;
import com.more.app.util.annotations.UILabelUtil;
import com.vaadin.flow.component.AbstractCompositeField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

import ch.carnet.kasparscherrer.EmptyFormLayoutItem;


public class PartyDetailsCrudView extends AbstractCompositeField<FormLayout, PartyDetailsCrudView, Party>
{
	private Party entity;

	public PartyDetailsCrudView(Party entity)
	{
		super(null);
		this.entity = entity;

		initializeComponents(entity);
		binder.readBean(entity);

		swiftPartyTypeRadioGroup.setLabel(UILabelUtil.getFieldLabel(entity, "swiftPartyType"));
		swiftPartyTypeRadioGroup.setItems("A", "B", "D", "F", "K", "No Option");
		// swiftPartyTypeRadioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
		swiftPartyTypeRadioGroup.setValue("A");

		swiftPartyTypeField.setLabel("Party Option");

		// This should be populated from the Currency Entity code variable.
		swiftPartyTypeField.setItems("A", "B", "D", "F", "K", "No Option");

		// The empty selection item is the first item that maps to an null item.
		// As the item is not selectable, using it also as placeholder
		swiftPartyTypeField.setPlaceholder("Select an option");
		swiftPartyTypeField.setEmptySelectionCaption("Select an option");
		swiftPartyTypeField.setEmptySelectionAllowed(true);

		accountField.setLabel(UILabelUtil.getFieldLabel(entity, "account"));
		accountField.setMaxLength(34);

		bicCodeField.setLabel(UILabelUtil.getFieldLabel(entity, "bicCode"));
		bicCodeField.setMaxLength(11);

		nameField.setLabel(UILabelUtil.getFieldLabel(entity, "name"));
		nameField.setMaxLength(35);

		address1Field.setLabel(UILabelUtil.getFieldLabel(entity, "address1"));
		address1Field.setMaxLength(35);

		address2Field.setLabel(UILabelUtil.getFieldLabel(entity, "address2"));
		address2Field.setMaxLength(35);

		address3Field.setLabel(UILabelUtil.getFieldLabel(entity, "address3"));
		address3Field.setMaxLength(35);

		locationField.setLabel(UILabelUtil.getFieldLabel(entity, "location"));
		locationField.setMaxLength(35);

		getAccountBtn.setText("Get Account");

		addPartyBtn.setText("Add Party");
		setColspan(addPartyBtn, 2);
		addPartyBtn.getElement().getStyle().set("color", "white");
		addPartyBtn.getElement().getStyle().set("background", "green");

		HorizontalLayout hl = new HorizontalLayout();
		hl.setSizeFull();
		hl.add(swiftPartyTypeRadioGroup);

		HorizontalLayout hl1 = new HorizontalLayout();
		hl1.add(accountField, nameField, getAccountBtn);

		hl1.setVerticalComponentAlignment(Alignment.BASELINE, accountField, nameField, getAccountBtn);

		HorizontalLayout hl2 = new HorizontalLayout();
		hl2.add(address1Field, address2Field, address3Field);

		getContent().setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
		getContent().setMaxWidth("1000px");
		getContent().add(swiftPartyTypeField, new EmptyFormLayoutItem(), accountField, getAccountBtn, bicCodeField,
				new EmptyFormLayoutItem(), nameField, address1Field, address2Field, address3Field, locationField,
				new EmptyFormLayoutItem(), addPartyBtn);

		getAccountBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>()
		{

			@Override
			public void onComponentEvent(ClickEvent<Button> event)
			{
				Dialog dg = new Dialog();
				dg.open();

			}
		});

		addPartyBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>()
		{

			@Override
			public void onComponentEvent(ClickEvent<Button> event)
			{
				try
				{
					binder.writeBean(entity);
					setComponentValue(entity);					
				} catch (ValidationException e)
				{
					e.printStackTrace();
				}

			}
		});

	}

	private Select<String> swiftPartyTypeField;
	private TextField accountField, bicCodeField, nameField, address1Field, address2Field, address3Field, locationField;
	private RadioButtonGroup<String> swiftPartyTypeRadioGroup;
	private Button getAccountBtn, addPartyBtn;
	private Binder<Party> binder = new Binder<>(Party.class);



	private void setColspan(Component component, int colspan)
	{
		component.getElement().setAttribute("colspan", Integer.toString(colspan));
	}
	
	@Override
	protected void setPresentationValue(Party newPresentationValue)
	{
		 this.entity = newPresentationValue;
	}
	
	private void setComponentValue(Party p)
	{
		this.setValue(p);
	}

	private void initializeComponents(Party entity)
	{
		swiftPartyTypeField = new Select<String>();
		swiftPartyTypeField.setItems("A", "B", "D", "F", "K", "No Option");

		accountField = new TextField();
		bicCodeField = new TextField();
		nameField = new TextField();
		address1Field = new TextField();
		address2Field = new TextField();
		address3Field = new TextField();
		locationField = new TextField();
		swiftPartyTypeRadioGroup = new RadioButtonGroup<>();
		getAccountBtn = new Button();
		addPartyBtn = new Button();

		binder.bind(swiftPartyTypeField, "swiftPartyType");

		binder.bind(accountField, "account");

		binder.bind(bicCodeField, "bicCode");

		binder.bind(nameField, "name");

		binder.bind(address1Field, "address1");

		binder.bind(address2Field, "address2");

		binder.bind(address3Field, "address3");

		binder.bind(locationField, "location");

	}

	// swiftPartyType, account, bicCode, name, address1, address2, address3,
	// location

}

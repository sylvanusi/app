package com.more.app.base.ui.product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.more.app.entity.product.CancellationReason;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.Binder;

public class CancellationReasonComponent extends VerticalLayout
{
	private static final long serialVersionUID = 4390860223641693865L;
	private Button addBtn = new Button("Add New Reason");
	private Div tierItemsDiv = new Div();
	List<CancellationReasonField> fieldList = new ArrayList<>();
	private H5 titlelabel = new H5();
	private boolean forCancellation;
	public CancellationReasonComponent(String title, boolean forCancellation)
	{
		this.forCancellation = forCancellation;
		setMargin(true);
		setSpacing(true);
		setPadding(false);
		setWidthFull();
		titlelabel.setText(title);
		titlelabel.getElement().getStyle().set("font-weight", "bold");
		add(titlelabel, tierItemsDiv, addBtn);
		addBtn.addClickListener(event ->
		{
			addButtonAction();
		});
	}
	
	public void setCancellationReasons(Set<CancellationReason> reasons)
	{
		addButtonAction(reasons);
	}
	
	private class CancellationReasonField extends HorizontalLayout
	{
		private Button removebtn = new Button(VaadinIcon.MINUS.create());
		private TextArea reasonTA = new TextArea();
		private CancellationReason reasonEntity;
		private CancellationReasonField field;
		private Binder<CancellationReason> binder = new Binder<>();
		
		CancellationReasonField(CancellationReason reasonEntity)
		{
			this.reasonEntity = reasonEntity;
			this.field = this;
			fieldList.add(field);
			binder.setBean(reasonEntity);
			binder.forField(reasonTA).bind(CancellationReason::getReason, CancellationReason::setReason);
			
			add(removebtn, reasonTA);
			setVerticalComponentAlignment(Alignment.CENTER, removebtn, reasonTA);
			tierItemsDiv.add(field);
			removebtn.addClickListener(event -> removeButtonAction());
			
			setMargin(true);
			setSpacing(true);
		}
		
		
		private void setLabels(CancellationReasonField field)
		{
			field.reasonTA.setLabel("Reason for cancellation");
		}
		
		private void removeButtonAction()
		{
			tierItemsDiv.remove(field);
			fieldList.remove(field);
			if (!fieldList.isEmpty())
				fieldList.get(0).setLabels(fieldList.get(0));
		}
	}
	

	private void addButtonAction()
	{
		CancellationReason reason = new CancellationReason();
		new CancellationReasonField(reason);
		fieldList.get(0).setLabels(fieldList.get(0));
	}
	
	private void addButtonAction(Set<CancellationReason> reasonsToSet)
	{
		fieldList.clear();
		tierItemsDiv.removeAll();
		for (CancellationReason reason : reasonsToSet)
		{
			new CancellationReasonField(reason);
		}
		fieldList.get(0).setLabels(fieldList.get(0));
	}
	
	public Set<CancellationReason> getCancellationReasions()
	{
		if(!fieldList.isEmpty())
		{
			Set<CancellationReason> reasons = new HashSet<>();
			for(CancellationReasonField field: fieldList)
			{
				reasons.add(field.binder.getBean());
			}
			return reasons;
		}
		return new HashSet<>();
	}
}

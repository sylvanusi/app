package com.more.app.base.ui.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.more.app.entity.Product;
import com.more.app.entity.ProductEventSwift;
import com.more.app.entity.ProductTypeEvent;
import com.more.app.entity.enums.SwiftMessageType;
import com.more.app.repository.productsetup.ProductEventSwiftRepository;
import com.more.app.repository.productsetup.ProductTypeEventRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.TextRenderer;

public class ProductEventSwiftConfigurationView extends VerticalLayout
{
	private static final long serialVersionUID = 1L;
	private Div itemsDiv = new Div();
	private List<ProductEventSwiftField> fieldList = new ArrayList<>();

	private Binder<Product> binder = new Binder<>(Product.class);
	private Button addBtn;
	private H5 swiftFieldlbl = new H5("Product Swift Event Configuration");
	private H5 eventlbl = new H5("Events");
	private RadioButtonGroup<ProductTypeEvent> eventRB;
	private Product entity;
	private ProductEventSwiftRepository repository;
	private ProductTypeEventRepository productTypeEventRepo;


	public ProductEventSwiftConfigurationView()
	{
		eventlbl.getElement().getStyle().set("font-weight", "bold");
		swiftFieldlbl.getElement().getStyle().set("font-weight", "bold");

		eventRB = new RadioButtonGroup<>();
		eventRB.setRenderer(new TextRenderer<>(ProductTypeEvent::getEventDescription));
		eventRB.addThemeVariants(RadioGroupVariant.AURA_HORIZONTAL);

		Hr hr1 = new Hr();
		hr1.setSizeFull();
		Hr hr2 = new Hr();
		hr2.setSizeFull();
		
		addBtn = new Button("Add Swift Message");
		add(eventlbl, hr1, eventRB, hr2, swiftFieldlbl, itemsDiv, addBtn);
		eventRB.addValueChangeListener(event ->
		{
			fieldList.clear();
			itemsDiv.removeAll();
			if (Objects.isNull(event.getValue()))
				return;

			List<ProductEventSwift> items =repository.findByProductIdAndEventId(entity.getId(),event.getValue().getId());
			if (items.isEmpty())
				return;
			items.forEach(item ->
			{
				addButtonAction(item);
			});
		});

		addBtn.addClickListener(evt ->
		{
			addButtonAction();
		});
		setSpacing(true);
		setPadding(false);
		setMargin(true);
	}

	public void updateFields(Product product)
	{
		this.entity = product;
		if (Objects.nonNull(entity))
		{
			binder.setBean(product);
			List<ProductTypeEvent> evtList = productTypeEventRepo.findByProductTypeId(product.getType().getId());
			eventRB.setItems(evtList);
			eventRB.setValue(evtList.get(0));
		}
	}

	private void addButtonAction()
	{
		ProductEventSwift evtSwiftEntity = new ProductEventSwift();
		evtSwiftEntity.setProduct(entity);
		evtSwiftEntity.setEvent(eventRB.getValue());
		new ProductEventSwiftField(evtSwiftEntity);
		fieldList.get(0).setLabels(fieldList.get(0));
	}
	private void addButtonAction(ProductEventSwift evtSwiftEntity)
	{
		new ProductEventSwiftField(evtSwiftEntity);
		fieldList.get(0).setLabels(fieldList.get(0));
	}

	class ProductEventSwiftField extends HorizontalLayout
	{
		private static final long serialVersionUID = 1L;
		private Button addSwiftBtn;
		private Binder<ProductEventSwift> binder = new Binder<>(ProductEventSwift.class);
		private ProductEventSwiftField field;
		private ComboBox<SwiftMessageType> swiftMessageTypeCB = new ComboBox<>();
		private Checkbox forIncomingCB = new Checkbox();
		private Checkbox forOutgoingCB = new Checkbox();
		private Button removeBtn = new Button(new Icon(VaadinIcon.MINUS_CIRCLE_O));
		private ProductEventSwift fieldEntity;
		
		ProductEventSwiftField(ProductEventSwift fieldEntity)
		{
			this.field = this;
			fieldList.add(field);
			this.fieldEntity = fieldEntity;
			binder.setBean(fieldEntity);
			addSwiftBtn = new Button("Save");
			
			setSpacing(true);
			setMargin(true);
			
			swiftMessageTypeCB.setItems(SwiftMessageType.values());
			add(removeBtn, swiftMessageTypeCB, forIncomingCB, forOutgoingCB, addSwiftBtn);
			setVerticalComponentAlignment(Alignment.BASELINE, removeBtn, swiftMessageTypeCB, forIncomingCB, forOutgoingCB, addSwiftBtn);

			itemsDiv.add(field);
			removeBtn.addClickListener(event -> removeButtonAction());
			
			addSwiftBtn.addClickListener(event ->
			{
				if (binder.validate().isOk())
				{
					ProductEventSwift entity = binder.getBean();
					entity.setProductId(entity.getProduct().getId());
					entity.setEventId(entity.getEvent().getId());
					repository.save(binder.getBean());
					Notification.show("Swift Message Type Saved successfully", 3000, Position.TOP_CENTER);

					//NotificationUtil.createSavedNotification("Event Swift Item Saved");
				}
			});
			
			binder.forField(swiftMessageTypeCB).asRequired().bind("swiftMessageType");
			binder.forField(forIncomingCB).bind("incoming");
			binder.forField(forOutgoingCB).bind("outgoing");

			field.forIncomingCB.setLabel("Incoming ");
			field.forOutgoingCB.setLabel("Outgoing");
		}

		private void setLabels(ProductEventSwiftField field)
		{
			field.swiftMessageTypeCB.setLabel("Swift Message Type");
		}
		
		private void removeButtonAction()
		{
			repository.delete(field.fieldEntity);
			Notification.show("Swift Message Type removed successfully", 3000, Position.TOP_CENTER);
			itemsDiv.remove(field);
			fieldList.remove(field);
			if (!fieldList.isEmpty())
				fieldList.get(0).setLabels(fieldList.get(0));
		}
	}

	public void setRepository(ProductEventSwiftRepository repository) {
		this.repository = repository;
	}

	public void setProductTypeEventRepo(ProductTypeEventRepository productTypeEventRepo) {
		this.productTypeEventRepo = productTypeEventRepo;
	}
	
	
	
}

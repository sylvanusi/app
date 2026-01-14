package com.more.app.base.ui.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.more.app.base.ui.DialogSelectEntity;
import com.more.app.entity.AbstractPojo;
import com.more.app.entity.ChargeDefination;
import com.more.app.entity.Product;
import com.more.app.entity.ProductEventChargeDefination;
import com.more.app.entity.ProductTypeEvent;
import com.more.app.repository.productsetup.ChargeDefinationRepository;
import com.more.app.repository.productsetup.ProductTypeEventRepository;
import com.more.app.util.annotations.UIActionUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.TextRenderer;

public class ProductEventChargeConfigurationView extends VerticalLayout {
	private static final long serialVersionUID = 1L;
	private Div itemsDiv = new Div();
	private List<ProductEventChargeField> fieldList = new ArrayList<>();
	private Binder<Product> binder = new Binder<>(Product.class);
	private Button addBtn;
	private H5 chargeFieldlbl = new H5("Product Charge Event");
	private H5 eventlbl = new H5("Events");
	private RadioButtonGroup<ProductTypeEvent> eventRB;
	private ProductEventChargeField eventChargeField;
	private Product entity;
	
	private ProductTypeEventRepository productTypeEventRepo;
	private ChargeDefinationRepository chargeDefRepository;

	public ProductEventChargeConfigurationView() {
		eventlbl.getElement().getStyle().set("font-weight", "bold");
		chargeFieldlbl.getElement().getStyle().set("font-weight", "bold");

		eventRB = new RadioButtonGroup<>();
		eventRB.setRenderer(new TextRenderer<>(ProductTypeEvent::getEventDescription));
		eventRB.addThemeVariants(RadioGroupVariant.AURA_HORIZONTAL);

		addBtn = new Button("Add Charge");
		
		Hr hr1 = new Hr();
		hr1.setSizeFull();
		Hr hr2 = new Hr();
		hr2.setSizeFull();

		add(eventlbl, hr1, eventRB, hr2, chargeFieldlbl, itemsDiv, addBtn);

		eventRB.addValueChangeListener(event -> {
			fieldList.clear();
			itemsDiv.removeAll();
			if (Objects.isNull(event.getValue()))
				return;

			List<ProductEventChargeDefination> items = new ArrayList();
			// new
			// ProductDAO().findProductEventChargeDefinationForEvent(entity,event.getValue());
			if (items.isEmpty())
				return;
			items.forEach(item -> {
				addButtonAction(item);
			});
		});

		addBtn.addClickListener(evt -> {
			addButtonAction();
		});
		setSpacing(true);
		setPadding(false);
		setMargin(true);
	}

	public void updateFields(Product product) {
		this.entity = product;
		if (Objects.nonNull(entity)) {
			binder.setBean(product);
			List<ProductTypeEvent> evtList = productTypeEventRepo.findByProductTypeId(product.getType().getId());
			eventRB.setItems(evtList);

			eventRB.setValue(evtList.get(0));
		}
	}

	private void addButtonAction() {
		ProductEventChargeDefination evtChargeEntity = new ProductEventChargeDefination();
		evtChargeEntity.setProduct(entity);
		evtChargeEntity.setEvent(eventRB.getValue());
		new ProductEventChargeField(evtChargeEntity);
		fieldList.get(0).setLabels(fieldList.get(0));
	}

	private void addButtonAction(ProductEventChargeDefination evtChargeEntity) {
		new ProductEventChargeField(evtChargeEntity);
		fieldList.get(0).setLabels(fieldList.get(0));
	}

	class ProductEventChargeField extends HorizontalLayout implements DialogSelectEntity {
		private Button addChargeBtn;
		private Binder<ProductEventChargeDefination> binder = new Binder<>(ProductEventChargeDefination.class);
		private ProductEventChargeField field;
		private TextField chargeNameTF = new TextField();
		private ComboBox<String> basisAmountCB = new ComboBox<>();
		private ComboBox<String> chargeFromFieldCB = new ComboBox<>();
		private ComboBox<String> chargeToFieldCB = new ComboBox<>();
		private Button removeBtn = new Button(new Icon(VaadinIcon.MINUS_CIRCLE_O));
		private ProductEventChargeDefination fieldentity;

		ProductEventChargeField(ProductEventChargeDefination fieldentity) {
			this.field = this;
			fieldList.add(field);
			this.fieldentity = fieldentity;
			binder.setBean(fieldentity);
			
			setMargin(true);
			setSpacing(true);

			addChargeBtn = new Button("Save");
			// addChargeBtn.addClassName(Button);

			add(removeBtn, chargeNameTF, getCharge, basisAmountCB, chargeFromFieldCB, chargeToFieldCB, addChargeBtn);
			setVerticalComponentAlignment(Alignment.BASELINE, removeBtn, chargeNameTF, getCharge, basisAmountCB,
					chargeFromFieldCB, chargeToFieldCB, addChargeBtn);

			itemsDiv.add(field);
			removeBtn.addClickListener(event -> removeButtonAction());

			addChargeBtn.addClickListener(event -> {
				if (binder.validate().isOk()) {
					// FacadeFactory.getFacade().store(binder.getBean());
					Notification.show("Charge saved successfully");
				}
			});

			calculationMethodAction(fieldentity.getChargeDefination());

			binder.forField(chargeNameTF).asRequired().bind("chargeName");
			binder.forField(basisAmountCB).bind("basisAmount");
			binder.forField(chargeFromFieldCB).bind("chargeFromField");
			binder.forField(chargeToFieldCB).bind("chargeToField");
		}

		Button getCharge = new Button("", new Icon(VaadinIcon.SEARCH), event -> {
			Dialog dg = new Dialog();
			ChargeDefinationView cv = new ChargeDefinationView(field, dg, getChargeDefRepository());
			dg.add(cv);
			dg.open();
		});

		public void setSelectedEntity(AbstractPojo pojo) {
			if (pojo instanceof ChargeDefination) {
				ChargeDefination charge = (ChargeDefination) pojo;
				chargeNameTF.setValue(((ChargeDefination) pojo).getChargeName());
				chargeNameTF.setReadOnly(true);
				field.binder.getBean().setChargeDefination(charge);
				calculationMethodAction((ChargeDefination) pojo);
			}
		}

		private void calculationMethodAction(ChargeDefination charge) {
			field.basisAmountCB.clear();
			field.chargeFromFieldCB.clear();
			field.chargeToFieldCB.clear();
			field.chargeFromFieldCB.setEnabled(false);
			field.chargeToFieldCB.setEnabled(false);
			// chargeFromFieldCB.setEnabled(!"One Off".equals(charge.getChargeFrequency()));
			// chargeToFieldCB.setEnabled(!"One Off".equals(charge.getChargeFrequency()));
			if ("Percent".equals(charge.getCalculationMethod())) {
				field.basisAmountCB.setEnabled(true);
				field.basisAmountCB
						.setItems(UIActionUtil.getBasisAmountFieldLabel(fieldentity.getEvent().getEntityClass()));
			} else {
				field.basisAmountCB.clear();
				field.basisAmountCB.setItems(new ArrayList());
				field.basisAmountCB.setEnabled(false);
			}

		}

		private void setLabels(ProductEventChargeField field) {
			field.chargeNameTF.setLabel("Charge");
			field.basisAmountCB.setLabel("Basis Amount");
			field.chargeFromFieldCB.setLabel("Charge From");
			field.chargeToFieldCB.setLabel("Charge To");
		}

		private void removeButtonAction() {
			// FacadeFactory.getFacade().delete(field.fieldentity);
			Notification.show("Charge removed successfully");
			itemsDiv.remove(field);
			fieldList.remove(field);
			if (!fieldList.isEmpty())
				fieldList.get(0).setLabels(fieldList.get(0));
		}
	}
	
	public void setProductTypeEventRepo(ProductTypeEventRepository productTypeEventRepo) {
		this.productTypeEventRepo = productTypeEventRepo;
	}

	public ChargeDefinationRepository getChargeDefRepository() {
		return chargeDefRepository;
	}

	public void setChargeDefRepository(ChargeDefinationRepository chargeDefRepository) {
		this.chargeDefRepository = chargeDefRepository;
	}
}

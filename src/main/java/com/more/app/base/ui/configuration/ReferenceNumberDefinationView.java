package com.more.app.base.ui.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.more.app.entity.Product;
import com.more.app.entity.ProductReferenceNumberDefination;
import com.more.app.entity.enums.ReferenceNumberItem;
import com.more.app.repository.productsetup.ProductReferenceNumberDefinationRepository;
import com.more.app.util.ReferenceNumberItemUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class ReferenceNumberDefinationView extends VerticalLayout {
	private static final long serialVersionUID = 1L;
	private Div itemsDiv = new Div();
	private List<ReferenceNumberItemsField> fieldList = new ArrayList<>();

	private Binder<Product> binder = new Binder<>(Product.class);
	private Button addBtn, saveBtn;
	private H5 lbl = new H5("Reference Number Defination");
	private Product entity;
	private TextField sampleReftf = new TextField("Sample Reference");
	private IntegerField tfRefLength = new IntegerField("Reference Length");
	private int lastPosition = 0;

	private ProductReferenceNumberDefinationRepository repository;

	public ReferenceNumberDefinationView(ProductReferenceNumberDefinationRepository repository) {
		this.repository = repository;
		lbl.getElement().getStyle().set("font-weight", "bold");
		addBtn = new Button("Add Reference Item");
		saveBtn = new Button("Save Reference Items");

		tfRefLength.setEnabled(false);
		add(lbl, itemsDiv, addBtn, new HorizontalLayout(tfRefLength, sampleReftf), saveBtn);

		addBtn.addClickListener(evt -> {
			addButtonAction();
		});

		saveBtn.addClickListener(evt -> {
			saveButtonAction();
		});
		setSpacing(true);
		setMargin(true);
	}

	public void updateFields(Product product) {
		this.entity = product;
		if (Objects.nonNull(entity)) {
			itemsDiv.removeAll();
			fieldList.clear();
			tfRefLength.setValue(entity.getReferenceLength());
			List<ProductReferenceNumberDefination> refNoItems = repository.findByProductId(product.getId());
			lastPosition = refNoItems.size();
			if (refNoItems.isEmpty())
				addButtonAction();
			else
				refNoItems.forEach(item -> {
					addButtonAction(item);
				});
		}
	}

	private void saveButtonAction() {
		if (!isInValid()) {
			List<ProductReferenceNumberDefination> lisToSave = fieldList.stream().map(field -> {
				ProductReferenceNumberDefination item  = field.binder.getBean();
				item.setProductId(item.getProduct().getId());
				return item;})
					.collect(Collectors.toList());
			repository.saveAll(lisToSave);
			Notification.show("Reference No Items Saved", 5000, Position.TOP_CENTER);
		}
	}

	private void addButtonAction() {
		if (fieldList.size() > 0 && isInValid()) {
			Notification.show("Ensure that the Reference items are valid");
			return;
		}
		lastPosition = lastPosition + 1;
		ProductReferenceNumberDefination refNoEntity = new ProductReferenceNumberDefination();
		refNoEntity.setProduct(entity);
		refNoEntity.setPosition(lastPosition);
		new ReferenceNumberItemsField(refNoEntity);
		fieldList.get(0).setLabels(fieldList.get(0));
	}

	private void addButtonAction(ProductReferenceNumberDefination refNoItem) {
		refNoItem.setRefItemValue(ReferenceNumberItemUtil.getReferenceNumberItemValue(refNoItem.getItem(), entity));
		refNoItem.setItemLength(refNoItem.getItem().getLength());
		new ReferenceNumberItemsField(refNoItem);
		fieldList.get(0).setLabels(fieldList.get(0));
	}

	class ReferenceNumberItemsField extends HorizontalLayout {
		private static final long serialVersionUID = 1L;
		private Binder<ProductReferenceNumberDefination> binder = new Binder<>(ProductReferenceNumberDefination.class);

		private ProductReferenceNumberDefination item;
		private ReferenceNumberItemsField field;
		private ComboBox<ReferenceNumberItem> refNoItemsCB = new ComboBox<>();
		private IntegerField itemLength = new IntegerField();
		private TextField refItemValue = new TextField();
		private IntegerField indexStartPosition = new IntegerField();
		private IntegerField indexEndPosition = new IntegerField();
		private IntegerField sequenceField = new IntegerField();
		private Button removeBtn = new Button(new Icon(VaadinIcon.MINUS_CIRCLE_O));

		ReferenceNumberItemsField(ProductReferenceNumberDefination item) {
			this.item = item;
			this.field = this;
			fieldList.add(field);
			binder.setBean(item);
			
			setSpacing(true);
			setMargin(true);

			refNoItemsCB.setItems(ReferenceNumberItem.values());
			refNoItemsCB.setItemLabelGenerator(ReferenceNumberItem::getDescription);

			sequenceField.setEnabled(false);

			add(removeBtn, sequenceField, refNoItemsCB, itemLength, refItemValue, indexStartPosition, indexEndPosition);
			setVerticalComponentAlignment(Alignment.BASELINE, removeBtn, refNoItemsCB, refItemValue, indexStartPosition,
					indexEndPosition);

			itemsDiv.add(field);
			removeBtn.addClickListener(event -> removeButtonAction(field));

			binder.forField(refNoItemsCB).asRequired().bind("item");
			binder.forField(sequenceField).bind("position");
			binder.forField(refItemValue).bind("refItemValue");
			binder.forField(itemLength).bind("itemLength");
			binder.forField(indexStartPosition).asRequired().bind("refNoStartIndex");
			binder.forField(indexEndPosition).asRequired().bind("refNoStopIndex");

			binder.validate();

			refNoItemsCB.addValueChangeListener(evt -> {
				ReferenceNumberItem refNoItem = evt.getValue();
				refItemValue.clear();
				refItemValue.setEnabled(true);
				if (Objects.nonNull(refNoItem)) {
					String code = ReferenceNumberItemUtil.getReferenceNumberItemValue(refNoItem, entity);
					refItemValue.setValue(code);
					refItemValue.setEnabled(code.trim().length() == 0);
					itemLength.setValue(refNoItem.getLength());
					itemLength.setEnabled(!(code.trim().length() > 0));
					if (fieldList.size() == 1) {
						indexStartPosition.setValue(1);
						if (code.trim().length() > 0)
							indexEndPosition.setValue(code.trim().length());
					}
					if (fieldList.size() > 1) {
						int index = fieldList.indexOf(field);
						ReferenceNumberItemsField prevField = fieldList.get(index - 1);
						indexStartPosition.setValue(prevField.item.getRefNoStopIndex() + 1);
						if (code.trim().length() > 0)
							indexEndPosition.setValue(prevField.item.getRefNoStopIndex() + code.trim().length());
					}

				}
			});

			refItemValue.addValueChangeListener(evt -> {
				String sampleRef = "";
				fieldList.sort((c1, c2) -> c1.item.getPosition() - c2.item.getPosition());

				for (ReferenceNumberItemsField field : fieldList) {
					sampleRef = sampleRef + field.refItemValue.getValue();
				}
				sampleReftf.setValue(sampleRef);
			});
		}

		private void setLabels(ReferenceNumberItemsField field) {
			field.refNoItemsCB.setLabel("Reference Item");
			field.itemLength.setLabel("Item length");
			field.refItemValue.setLabel("Item Value");
			field.indexStartPosition.setLabel("Start From");
			field.indexEndPosition.setLabel("End At");
			field.sequenceField.setLabel("Item Position");
			field.removeBtn.setEnabled(false);
		}

		private void removeButtonAction(ReferenceNumberItemsField field) {
			int position = field.item.getPosition();
			int fieldPosition = fieldList.indexOf(field) + 1;
			lastPosition = lastPosition - 1;
			itemsDiv.remove(field);
			fieldList.remove(field);
			repository.delete(field.item);
			if (!fieldList.isEmpty())
				fieldList.get(0).setLabels(fieldList.get(0));

			if (fieldList.size() > 1) {
				fieldList.sort((c1, c2) -> c1.item.getPosition() - c2.item.getPosition());
				fieldList.forEach(flist -> {
					if (flist.item.getPosition() > position)
						flist.sequenceField.setValue(flist.item.getPosition() - 1);

				});
			}
		}
	}

	private boolean isInValid() {
		return fieldList.stream().anyMatch(f -> Boolean.FALSE.equals(f.binder.validate().isOk()));
	}

	public ProductReferenceNumberDefinationRepository getRepository() {
		return repository;
	}

	public void setRepository(ProductReferenceNumberDefinationRepository repository) {
		this.repository = repository;
	}
}

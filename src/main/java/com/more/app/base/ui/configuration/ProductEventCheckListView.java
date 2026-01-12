package com.more.app.base.ui.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.more.app.entity.Product;
import com.more.app.entity.ProductEventCheckList;
import com.more.app.entity.ProductType;
import com.more.app.entity.ProductTypeEvent;
import com.more.app.repository.productsetup.ProductTypeEventRepository;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;

@Component
public class ProductEventCheckListView extends VerticalLayout {
	private static final long serialVersionUID = -9193763725550154635L;
	private Div itemsDiv = new Div();
	List<ProductEventCheckListComponent> itemsComponentList = new ArrayList<>();
	private H4 titlelabel = new H4("Product Event Configuration Check List");
	private Hr hr = new Hr();
	private Product product;

	private ProductTypeEventRepository pteRepository;

	public ProductEventCheckListView(ProductTypeEventRepository pteRepository) {
		this.pteRepository = pteRepository;
		titlelabel.getElement().getStyle().set("font-weight", "bold");
		hr.setSizeFull();
		titlelabel.setVisible(false);
		hr.setVisible(false);
		add(titlelabel, hr, itemsDiv);
		setMargin(true);
		setSizeFull();
	}

	public void setProductEventCheckListComponent(Product product) {
		this.product = product;
		titlelabel.setVisible(false);
		hr.setVisible(false);
		itemsDiv.removeAll();
		itemsComponentList.clear();
		if (null != product) {
			titlelabel.setVisible(true);
			hr.setVisible(true);
			product.getProductEventCheckList().forEach(e -> new ProductEventCheckListComponent(e));
		}
	}

	public void setProductEventCheckListComponent(ProductType productType, ProductTypeEventRepository pteRepository) {
		titlelabel.setVisible(false);
		hr.setVisible(false);
		itemsDiv.removeAll();
		itemsComponentList.clear();
		if (null != productType) {
			List<ProductTypeEvent> evtList = pteRepository.findByProductTypeId(productType.getId());
			if (!evtList.isEmpty()) {
				titlelabel.setVisible(true);
				hr.setVisible(true);
				evtList.forEach(evt -> {
					ProductEventCheckList item = new ProductEventCheckList();
					item.setEvent(evt);
					item.setEventId(evt.getId());
					product.getProductEventCheckList().add(item);
					new ProductEventCheckListComponent(item);
				});
			}
		}

	}

	class ProductEventCheckListComponent extends HorizontalLayout {
		private static final long serialVersionUID = 1L;
		Binder<ProductEventCheckList> binder = new Binder<>(ProductEventCheckList.class);
		private ProductEventCheckListComponent field;
		private ProductEventCheckList entity;

		private H5 eventlbl = new H5();
		private Checkbox defineCharges = new Checkbox("Configure Charges");
		private Checkbox definePosting = new Checkbox("Configure Posting");
		private Checkbox configureSwift = new Checkbox("Configure Swift");
		private Checkbox defineDocument = new Checkbox("Define Document");
		private Checkbox configureWorklow = new Checkbox("Configure Workflow");

		public ProductEventCheckListComponent(ProductEventCheckList entity) {
			this.entity = entity;
			this.field = this;
			entity.setConfigureWorklow(true);
			binder.setBean(entity);
			itemsComponentList.add(field);
			setSpacing(true);
			setMargin(true);
			setWidthFull();
			eventlbl.setText("EVENT: " + entity.getEventName());
			eventlbl.setWidth("140px");
			configureWorklow.setValue(true);
			configureWorklow.setEnabled(false);
			add(eventlbl, defineCharges, definePosting, configureSwift, defineDocument, configureWorklow);
			
			defineCharges.setWidth("200px");
			definePosting.setWidth("200px");
			configureSwift.setWidth("200px");
			defineDocument.setWidth("200px");
			configureWorklow.setWidth("200px");

			binder.forField(defineCharges).bind(ProductEventCheckList::isDefineCharges,
					ProductEventCheckList::setDefineCharges);
			binder.forField(definePosting).bind(ProductEventCheckList::isDefinePosting,
					ProductEventCheckList::setDefinePosting);
			binder.forField(configureSwift).bind(ProductEventCheckList::isConfigureSwift,
					ProductEventCheckList::setConfigureSwift);
			binder.forField(defineDocument).bind(ProductEventCheckList::isDefineDocument,
					ProductEventCheckList::setDefineDocument);
			binder.forField(configureWorklow).bind(ProductEventCheckList::isConfigureWorklow,
					ProductEventCheckList::setConfigureWorklow);
			itemsDiv.add(field);
		}

	}

}

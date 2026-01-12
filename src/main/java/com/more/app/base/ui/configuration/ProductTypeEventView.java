package com.more.app.base.ui.configuration;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.more.app.base.ui.BaseView;
import com.more.app.base.ui.DialogSelectEntity;
import com.more.app.base.ui.LeftAlignedLayout;
import com.more.app.entity.ProductType;
import com.more.app.entity.ProductTypeEvent;
import com.more.app.repository.productsetup.ProductModuleRepository;
import com.more.app.repository.productsetup.ProductTypeEventRepository;
import com.more.app.repository.productsetup.ProductTypeRepository;
import com.more.app.util.annotations.UIActionUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = ProductTypeEventView.pageUrlString, layout = LeftAlignedLayout.class)
public class ProductTypeEventView extends BaseView<ProductTypeEvent>
{
	private static final long serialVersionUID = 4566008868498338184L;
	public static final String pageUrlString = "producttypeev-view";
	public static final Long resourceAdd = Long.valueOf(49L);
	public static final Long resourceEdit = Long.valueOf(50L);
	public static final Long resourceView = Long.valueOf(51L);

	private TextField eventCodeTF;
	private TextField productTypeTF;
	private Button prodTypeBtn;

	private ProductType productType;
	
	@Autowired
	private ProductTypeEventRepository repository;
	@Autowired
	private ProductTypeRepository prodTyperepository;
	@Autowired
	private ProductModuleRepository moduleRepo;

	public ProductTypeEventView()
	{
		super();
		view = this;
	}

	public ProductTypeEventView(DialogSelectEntity dialog, Dialog dg)
	{
		super(dialog, dg);
	}

	public void loadComponents()
	{
		prodTypeBtn = new Button("get ProductType", new Icon(VaadinIcon.SEARCH));
		eventCodeTF = new TextField(UIActionUtil.getFieldLabel(ProductTypeEvent.class, "eventCode"));
		eventCodeTF.setMaxLength(12);

		productTypeTF = new TextField(UIActionUtil.getFieldLabel(ProductTypeEvent.class, "productType"));
		productTypeTF.setMaxLength(35);

		grid.addColumn(ProductTypeEvent::getEventCode)
				.setHeader(UIActionUtil.getFieldLabel(ProductTypeEvent.class, "eventCode"));
		grid.addColumn(ProductTypeEvent::getEventDescription)
				.setHeader(UIActionUtil.getFieldLabel(ProductTypeEvent.class, "eventDescription"));
		grid.addColumn(ProductTypeEvent::getProductTypeDisplay)
				.setHeader(UIActionUtil.getFieldLabel(ProductTypeEvent.class, "productTypeDisplay"));

		hlsearch.add(eventCodeTF, productTypeTF, prodTypeBtn, searchb);
		hlsearch.setVerticalComponentAlignment(Alignment.BASELINE, eventCodeTF, productTypeTF, prodTypeBtn, searchb);

		addBaseComponentsandStyle();

		searchb.addClickListener(event ->
		{
			if(null == productType)
			{
				Notification n = new Notification();
				n.addThemeVariants(NotificationVariant.LUMO_ERROR);
				Div content = new Div();
				// content.getClassNames().add("savenofication");
				content.setText("Select Product Type");
				n.add(content);
				n.setDuration(5000);
				n.setPosition(Position.TOP_CENTER);
				n.open();
				return;
			}
			
			grid.setItems(new ArrayList<ProductTypeEvent>());
			String code = eventCodeTF.getValue();
			grid.setItems(repository.findByProductTypeIdAndEventCodeStartsWith(productType.getId(), code));
		});

		prodTypeBtn.addClickListener(event ->
			{
				productType = null;

				Dialog dg = new Dialog();
				ProductTypeView cview = new ProductTypeView(dg, prodTyperepository, moduleRepo);
				cview.select.setVisible(false);
				Button selectItem = new Button("Select Item");
				selectItem.setSizeFull();
				selectItem.addClickListener(evt ->
					{
						if (!cview.grid.getSelectedItems().isEmpty())
						{
							Object[] arr = cview.grid.getSelectedItems().toArray();
							ProductType productM = (ProductType) arr[0];
							productType = productM;
							productTypeTF.setValue(productType.getProductType());
							dg.close();
						}
				});
				cview.add(selectItem);
				dg.add(cview);
				dg.open();
			}
		);

		hl.remove(addb, editb);
	}

	public void reloadView()
	{
		grid.setItems(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
		grid.setItems(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
	}

	@Override
	public boolean getAddPermission()
	{
		return true;
	}

	@Override
	public boolean getEditPermission()
	{
		return true;
	}

	@Override
	public boolean getViewPermission()
	{
		return true;
	}

	@Override
	public void navigationAction(String param)
	{
		getUI().ifPresent(ui -> ui.navigate(ProductTypeEventCrudView.class, param));
	}

	@Override
	public String getDialogTitle()
	{
		return "Search Product Type Event";
	}
}

package com.more.app.base.ui.configuration;

import org.springframework.beans.factory.annotation.Autowired;

import com.more.app.base.ui.BaseCrudComponent;
import com.more.app.base.ui.CrudPageView;
import com.more.app.entity.AbstractPojo;
import com.more.app.entity.ProductTypeEvent;
import com.more.app.repository.productsetup.ProductTypeEventRepository;
import com.more.app.util.annotations.UIActionUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = ProductTypeEventCrudView.pageUrlString, layout = CrudPageView.class)
public class ProductTypeEventCrudView extends BaseCrudComponent<ProductTypeEvent> implements HasUrlParameter<String>
{
	private static final long serialVersionUID = -5511035586185573430L;
	public static final String pageUrlString = "prodtucttypeevt-view";
	private Binder<ProductTypeEvent> binder = new Binder<>(ProductTypeEvent.class);
	

	@Autowired
	private ProductTypeEventRepository repository;

	public ProductTypeEventCrudView()
	{
		super();
		vl.add(eventCodeTF, eventDescriptionTF, productTypeDisplayTF);
	}

	private TextField eventCodeTF, eventDescriptionTF, productTypeDisplayTF;

	@Override
	public void setSelectedEntity(AbstractPojo pojo)
	{
	}

	@Override
	public void clearSelectedEntity(ProductTypeEvent pojo)
	{
	}

	@Override
	public void initializeComponents()
	{
		eventCodeTF = new TextField();
		eventCodeTF.setMaxLength(12);
		eventCodeTF.setRequired(true);
		eventCodeTF.setRequiredIndicatorVisible(true);

		eventDescriptionTF = new TextField();
		eventDescriptionTF.setMaxLength(50);
		eventDescriptionTF.setWidth("350px");
		eventDescriptionTF.setRequired(true);
		eventDescriptionTF.setRequiredIndicatorVisible(true);

		productTypeDisplayTF = new TextField();
		productTypeDisplayTF.setMaxLength(50);
		productTypeDisplayTF.setWidth("350px");
		productTypeDisplayTF.setRequired(true);
		productTypeDisplayTF.setRequiredIndicatorVisible(true);
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
		if (entity != null)
		{
			binder.setBean(entity);
			eventCodeTF.setLabel(UIActionUtil.getFieldLabel(entity, "eventCode"));
			eventDescriptionTF.setLabel(UIActionUtil.getFieldLabel(entity, "eventDescription"));
			productTypeDisplayTF.setLabel(UIActionUtil.getFieldLabel(entity, "productTypeDisplay"));

			binder.forField(eventCodeTF).asRequired().bind("eventCode");
			binder.forField(eventDescriptionTF).asRequired().bind("eventDescription");
			binder.forField(productTypeDisplayTF).asRequired().bind("productTypeDisplay");
		}
		if (getPageMode().equals("ADD") || getPageMode().equals("ADDNEW") || getPageMode().equals("EDIT"))
		{
			confirmButton.setVisible(true);
			addewButton.setVisible(true);
		}
	}

	@Override
	public boolean fullsize()
	{
		return true;
	}

	@Override
	public String getPageTitle()
	{
		return "Event";
	}

	@Override
	public String getPageMode()
	{
		return pageMode;
	}

	@Override
	public Binder<ProductTypeEvent> getBinder()
	{
		return binder;
	}

	@Override
	public ProductTypeEvent getEntity()
	{
		return entity;
	}

	@Override
	public void setParameter(BeforeEvent beforeEvent, String parameter)
	{
		if (parameter != null && !parameter.isEmpty())
		{
			String params[] = parameter.split(",");
			pageMode = params[0];
			setPageMode(pageMode);
			if ("ADD".equals(pageMode))
				entity = new ProductTypeEvent();
			else
			{
				Long id = Long.valueOf(params[1]);
				if (id == -1L)
					beforeEvent.rerouteTo(getCloseNavigationClass());
				else
					entity = repository.findById(id).orElse(null);
			}
		} else
			getUI().get().navigate(getCloseNavigationClass());
	}

	@Override
	public Class<ProductTypeEventView> getCloseNavigationClass()
	{
		return ProductTypeEventView.class;
	}

	@Override
	public void saveRecord(ProductTypeEvent entity) {
		repository.save(entity);
		
	}
}

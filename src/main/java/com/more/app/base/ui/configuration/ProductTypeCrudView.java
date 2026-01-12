package com.more.app.base.ui.configuration;

import org.springframework.beans.factory.annotation.Autowired;

import com.more.app.base.ui.BaseCrudComponent;
import com.more.app.base.ui.CrudPageView;
import com.more.app.entity.AbstractPojo;
import com.more.app.entity.ProductType;
import com.more.app.repository.productsetup.ProductTypeRepository;
import com.more.app.util.annotations.UIActionUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = ProductTypeCrudView.pageUrlString, layout = CrudPageView.class)
public class ProductTypeCrudView extends BaseCrudComponent<ProductType> implements HasUrlParameter<String>
{
	private static final long serialVersionUID = -5511035586185573430L;
	public static final String pageUrlString = "producttype";
	private Binder<ProductType> binder = new Binder<>(ProductType.class);
	
	@Autowired
	private ProductTypeRepository repository;

	public ProductTypeCrudView()
	{
		super();
		vl.add(codeTF, productTypeTF, moduleNameTF);
	}

	private TextField codeTF, productTypeTF, moduleNameTF;

	@Override
	public void setSelectedEntity(AbstractPojo pojo)
	{
	}

	@Override
	public void clearSelectedEntity(ProductType pojo)
	{
	}

	@Override
	public void initializeComponents()
	{
		codeTF = new TextField();
		codeTF.setMaxLength(12);
		codeTF.setRequired(true);
		codeTF.setRequiredIndicatorVisible(true);

		productTypeTF = new TextField();
		productTypeTF.setMaxLength(50);
		productTypeTF.setWidth("350px");
		productTypeTF.setRequired(true);
		productTypeTF.setRequiredIndicatorVisible(true);

		moduleNameTF = new TextField();
		moduleNameTF.setMaxLength(50);
		moduleNameTF.setWidth("350px");
		moduleNameTF.setRequired(true);
		moduleNameTF.setRequiredIndicatorVisible(true);
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
		if (entity != null)
		{
			binder.setBean(entity);
			codeTF.setLabel(UIActionUtil.getFieldLabel(entity, "code"));
			productTypeTF.setLabel(UIActionUtil.getFieldLabel(entity, "productType"));
			moduleNameTF.setLabel(UIActionUtil.getFieldLabel(entity, "moduleName"));

			binder.forField(codeTF).asRequired().bind("code");
			binder.forField(productTypeTF).asRequired().bind("productType");// .asRequired("Country Name is required")
			binder.forField(moduleNameTF).asRequired().bind("moduleName");
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
		return false;
	}

	@Override
	public String getPageTitle()
	{
		return "Product Type";
	}

	@Override
	public String getPageMode()
	{
		return pageMode;
	}

	@Override
	public Binder<ProductType> getBinder()
	{
		return binder;
	}

	@Override
	public ProductType getEntity()
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
				entity = new ProductType();
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
	public Class<ProductTypeView> getCloseNavigationClass()
	{
		return ProductTypeView.class;
	}

	@Override
	public void saveRecord(ProductType entity) {
		repository.save(entity);
	}

}

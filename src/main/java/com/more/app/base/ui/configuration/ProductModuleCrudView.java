package com.more.app.base.ui.configuration;

import org.springframework.beans.factory.annotation.Autowired;

import com.more.app.base.ui.BaseCrudComponent;
import com.more.app.base.ui.CrudPageView;
import com.more.app.entity.AbstractPojo;
import com.more.app.entity.ProductModule;
import com.more.app.repository.productsetup.ProductModuleRepository;
import com.more.app.util.annotations.UIActionUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = ProductModuleCrudView.pageUrlString, layout = CrudPageView.class)
public class ProductModuleCrudView extends BaseCrudComponent<ProductModule> implements HasUrlParameter<String>
{
	private static final long serialVersionUID = -5511035586185573430L;
	public static final String pageUrlString = "module";
	private Binder<ProductModule> binder = new Binder<>(ProductModule.class);

	@Autowired
	private ProductModuleRepository repository;
	public ProductModuleCrudView()
	{
		super();
		vl.add(codeTF, nameTF);
	}

	private TextField codeTF, nameTF;

	@Override
	public void setSelectedEntity(AbstractPojo pojo)
	{
	}

	@Override
	public void clearSelectedEntity(ProductModule pojo)
	{
	}

	@Override
	public void initializeComponents()
	{
		codeTF = new TextField();
		codeTF.setMaxLength(2);
		codeTF.setRequired(true);
		codeTF.setRequiredIndicatorVisible(true);

		nameTF = new TextField();
		nameTF.setMaxLength(50);
		nameTF.setWidth("350px");
		nameTF.setRequired(true);
		nameTF.setRequiredIndicatorVisible(true);
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
		if (entity != null)
		{
			binder.setBean(entity);
			codeTF.setLabel(UIActionUtil.getFieldLabel(entity, "code"));
			nameTF.setLabel(UIActionUtil.getFieldLabel(entity, "name"));

			binder.forField(codeTF).asRequired().bind("code");
			binder.forField(nameTF).asRequired().bind("name");
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
		return "Module";
	}

	@Override
	public String getPageMode()
	{
		return pageMode;
	}

	@Override
	public Binder<ProductModule> getBinder()
	{
		return binder;
	}

	@Override
	public ProductModule getEntity()
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
				entity = new ProductModule();
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
	public Class<ProductModuleView> getCloseNavigationClass()
	{
		return ProductModuleView.class;
	}

	@Override
	public void saveRecord(ProductModule entity) {
		repository.save(entity);
	}
}

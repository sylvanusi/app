package com.more.app.base.ui.setup;

import org.springframework.beans.factory.annotation.Autowired;

import com.more.app.base.ui.BaseCrudComponent;
import com.more.app.base.ui.CrudPageView;
import com.more.app.entity.AbstractPojo;
import com.more.app.entity.Country;
import com.more.app.repository.CountryRepository;
import com.more.app.util.annotations.UIActionUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = CountryCrudView.pageUrlString, layout = CrudPageView.class)
public class CountryCrudView extends BaseCrudComponent<Country> implements HasUrlParameter<String>
{
	private static final long serialVersionUID = -5511035586185573430L;
	public static final String pageUrlString = "country";
	private Binder<Country> binder = new Binder<>(Country.class);

	@Autowired
	private CountryRepository repo;
	
	public CountryCrudView()
	{
		super();
		vl.add(codeTF, countryNameTF);
	}

	private TextField codeTF, countryNameTF;

	@Override
	public void setSelectedEntity(AbstractPojo pojo)
	{
	}

	@Override
	public void clearSelectedEntity(Country pojo)
	{
	}

	@Override
	public void initializeComponents()
	{
		codeTF = new TextField();
		codeTF.setMaxLength(2);
		codeTF.setRequired(true);
		codeTF.setRequiredIndicatorVisible(true);

		countryNameTF = new TextField();
		countryNameTF.setMaxLength(50);
		countryNameTF.setWidth("350px");
		countryNameTF.setRequired(true);
		countryNameTF.setRequiredIndicatorVisible(true);
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
		if (entity != null)
		{
			binder.setBean(entity);
			codeTF.setLabel(UIActionUtil.getFieldLabel(entity, "code"));
			countryNameTF.setLabel(UIActionUtil.getFieldLabel(entity, "countryName"));

			binder.forField(codeTF).asRequired("Code is required").bind("code");
			binder.forField(countryNameTF).asRequired("Country Code is required").bind("countryName");
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
		return "Country";
	}

	@Override
	public String getPageMode()
	{
		return pageMode;
	}

	@Override
	public Binder<Country> getBinder()
	{
		return binder;
	}

	@Override
	public Country getEntity()
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
				entity = new Country();
			else
			{
				Long id = Long.valueOf(params[1]);
				if (id == -1L)
					beforeEvent.rerouteTo(getCloseNavigationClass());
				else
					entity = repo.findById(id).orElse(null);
			}
		} else
			getUI().get().navigate(CountryView.class);
	}

	@Override
	public Class<CountryView> getCloseNavigationClass()
	{
		return CountryView.class;
	}

	@Override
	public void saveRecord(Country entity) {
		repo.save(entity);
	}
}

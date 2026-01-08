package com.more.app.base.ui.security;

import org.springframework.beans.factory.annotation.Autowired;

import com.more.app.base.ui.BaseCrudComponent;
import com.more.app.base.ui.CrudPageView;
import com.more.app.entity.AbstractPojo;
import com.more.app.entity.AppRole;
import com.more.app.repository.AppRoleRepository;
import com.more.app.util.annotations.UIActionUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = AppRoleCrudView.pageUrlString, layout = CrudPageView.class)
public class AppRoleCrudView extends BaseCrudComponent<AppRole> implements HasUrlParameter<String>
{
	private static final long serialVersionUID = -5511035586185573430L;
	public static final String pageUrlString = "role";
	private Binder<AppRole> binder = new Binder<>(AppRole.class);
	
	@Autowired
	private AppRoleRepository appRoleRepository;

	public AppRoleCrudView()
	{
		super();
		vl.add(nameTF, descriptionTF, statusTF);
	}

	private TextField nameTF;
	Select<String> statusTF;
	private TextArea descriptionTF;

	@Override
	public void setSelectedEntity(AbstractPojo pojo)
	{
	}

	@Override
	public void clearSelectedEntity(AppRole pojo)
	{
	}

	@Override
	public void setEventEntityFields(AppRole entity)
	{
		if (pageMode.equals("ADD"))
			entity.setStatus("Inactive");
	}

	@Override
	public void initializeComponents()
	{

		nameTF = new TextField();
		nameTF.setMaxLength(35);
		nameTF.setRequired(true);
		nameTF.setRequiredIndicatorVisible(true);

		descriptionTF = new TextArea();
		descriptionTF.setMaxLength(70);
		descriptionTF.setWidth("350px");
		descriptionTF.setRequired(true);
		descriptionTF.setRequiredIndicatorVisible(true);

		statusTF = new Select();
		statusTF.setWidth("350px");
		statusTF.setRequiredIndicatorVisible(true);
		statusTF.setItems("Active", "Inactive");
		statusTF.setEmptySelectionAllowed(true);
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
		if (entity != null)
		{
			binder.setBean(entity);
			nameTF.setLabel(UIActionUtil.getFieldLabel(entity, "name"));
			descriptionTF.setLabel(UIActionUtil.getFieldLabel(entity, "description"));
			statusTF.setLabel(UIActionUtil.getFieldLabel(entity, "status"));

			binder.forField(nameTF).bind("name");
			binder.forField(descriptionTF).bind("description");
			binder.forField(statusTF).bind("status");
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
		return "Role";
	}


	@Override
	public String getPageMode()
	{
		return pageMode;
	}

	@Override
	public Binder<AppRole> getBinder()
	{
		return binder;
	}

	@Override
	public AppRole getEntity()
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
				entity = new AppRole();
			else
			{
				Long id = Long.valueOf(params[1]);
				if (id == -1L)
					beforeEvent.rerouteTo(getCloseNavigationClass());
				else
					entity = appRoleRepository.findById(id).orElse(null);
			}
		} else
			getUI().get().navigate(getCloseNavigationClass());
	}

	@Override
	public Class<AppRoleView> getCloseNavigationClass()
	{
		return AppRoleView.class;
	}

	@Override
	public void saveRecord(AppRole entity) {
		appRoleRepository.save(entity);
	}
	

	
}

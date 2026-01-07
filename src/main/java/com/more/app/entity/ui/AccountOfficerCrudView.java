package com.more.app.entity.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.more.app.annotations.UIActionUtil;
import com.more.app.base.ui.BaseCrudComponent;
import com.more.app.base.ui.CrudPageView;
import com.more.app.entity.AbstractPojo;
import com.more.app.entity.AccountOfficer;
import com.more.app.repository.AccountOfficerRepository;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = AccountOfficerCrudView.pageUrlString, layout = CrudPageView.class)
public class AccountOfficerCrudView extends BaseCrudComponent<AccountOfficer> implements HasUrlParameter<String>
{
	private static final long serialVersionUID = -5511035586185573430L;
	public static final String pageUrlString = "accountofficer";
	private Binder<AccountOfficer> binder = new Binder<>(AccountOfficer.class);
	
	@Autowired
	private AccountOfficerRepository repository;

	public AccountOfficerCrudView()
	{
		super();
		vl.add(fullNameTF, addressTA,phoneNoTF,emailTF,departmentTF);
	}

	private TextField fullNameTF,departmentTF,emailTF,phoneNoTF;
	private TextArea addressTA;

	@Override
	public void setSelectedEntity(AbstractPojo pojo)
	{
	}

	@Override
	public void clearSelectedEntity(AccountOfficer pojo)
	{
	}
	
	@Override
	public void initializeComponents()
	{

		fullNameTF = new TextField();
		fullNameTF.setMaxLength(35);
		fullNameTF.setRequired(true);
		fullNameTF.setRequiredIndicatorVisible(true);

		addressTA = new TextArea();
		addressTA.setMaxLength(140);
		addressTA.setWidth("350px");
		addressTA.setRequired(true);
		addressTA.setRequiredIndicatorVisible(true);

		phoneNoTF = new TextField();
		phoneNoTF.setMaxLength(35);
		phoneNoTF.setRequired(true);
		phoneNoTF.setRequiredIndicatorVisible(true);
		
		emailTF = new TextField();
		emailTF.setMaxLength(35);
		emailTF.setRequired(true);
		emailTF.setRequiredIndicatorVisible(true);
		
		departmentTF = new TextField();
		departmentTF.setMaxLength(35);
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
		if (entity != null)
		{
			binder.setBean(entity);
			fullNameTF.setLabel(UIActionUtil.getFieldLabel(entity, "fullName"));
			addressTA.setLabel(UIActionUtil.getFieldLabel(entity, "address"));
			phoneNoTF.setLabel(UIActionUtil.getFieldLabel(entity, "phoneNo"));
			emailTF.setLabel(UIActionUtil.getFieldLabel(entity, "email"));
			departmentTF.setLabel(UIActionUtil.getFieldLabel(entity, "department"));

			binder.forField(fullNameTF).asRequired().bind("fullName");
			binder.forField(addressTA).asRequired().bind("address");
			binder.forField(phoneNoTF).asRequired().bind("phoneNo");
			binder.forField(emailTF).asRequired().bind("email");
			binder.forField(departmentTF).bind("department");
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
		return "Account Officer";
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
				entity = new AccountOfficer();
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
	public Class<AccountOfficerView> getCloseNavigationClass()
	{
		return AccountOfficerView.class;
	}

	@Override
	public String getPageMode()
	{
		return pageMode;
	}

	@Override
	public Binder<AccountOfficer> getBinder()
	{
		return binder;
	}

	@Override
	public AccountOfficer getEntity()
	{
		return entity;
	}

	@Override
	public void saveRecord(AccountOfficer entity) {
		repository.save(entity);
		
	}
}

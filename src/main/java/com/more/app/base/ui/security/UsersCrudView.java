package com.more.app.base.ui.security;

import org.springframework.beans.factory.annotation.Autowired;

import com.more.app.base.ui.BaseCrudComponent;
import com.more.app.base.ui.CrudPageView;
import com.more.app.entity.AbstractPojo;
import com.more.app.entity.User;
import com.more.app.repository.UserRepository;
import com.more.app.util.annotations.UIActionUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = UsersCrudView.pageUrlString, layout = CrudPageView.class)
public class UsersCrudView extends BaseCrudComponent<User> implements HasUrlParameter<String>
{
	private static final long serialVersionUID = -5511035586185573430L;
	public static final String pageUrlString = "users";
	public Binder<User> binder = new Binder<>(User.class);
	private User entity;
	private String pageMode;
	
	@Autowired
	private UserRepository repository;

	public UsersCrudView()
	{
		super();
		vl.add(nameTF, staffNoTF, emailTF, usernameTF, accountlockCB);
	}

	private TextField nameTF, staffNoTF, usernameTF, passwordTF;
	private EmailField emailTF;
	private Checkbox accountlockCB;

	@Override
	public void setSelectedEntity(AbstractPojo pojo)
	{
	}

	@Override
	public void clearSelectedEntity(User pojo)
	{
	}

	@Override
	public void initializeComponents()
	{
		nameTF = new TextField();
		nameTF.setMaxLength(50);
		nameTF.setWidth("350px");
		nameTF.setRequired(true);
		nameTF.setRequiredIndicatorVisible(true);

		staffNoTF = new TextField();
		staffNoTF.setMaxLength(10);
		staffNoTF.setWidth("350px");
		staffNoTF.setRequired(true);
		staffNoTF.setRequiredIndicatorVisible(true);

		emailTF = new EmailField();
		emailTF.setMaxLength(35);
		emailTF.setWidth("350px");
		emailTF.setRequiredIndicatorVisible(true);

		usernameTF = new TextField();
		usernameTF.setMaxLength(35);
		usernameTF.setWidth("350px");
		usernameTF.setRequired(true);
		usernameTF.setRequiredIndicatorVisible(true);

		passwordTF = new TextField();
		passwordTF.setMaxLength(35);
		passwordTF.setWidth("350px");
		passwordTF.setRequired(true);
		passwordTF.setRequiredIndicatorVisible(true);
		passwordTF.setEnabled(false);

		accountlockCB = new Checkbox();
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
		if (entity != null)
		{
			binder.setBean(entity);
			nameTF.setLabel(UIActionUtil.getFieldLabel(entity, "name"));
			staffNoTF.setLabel(UIActionUtil.getFieldLabel(entity, "staffNo"));
			emailTF.setLabel(UIActionUtil.getFieldLabel(entity, "email"));
			usernameTF.setLabel(UIActionUtil.getFieldLabel(entity, "username"));
			passwordTF.setLabel(UIActionUtil.getFieldLabel(entity, "password"));
			accountlockCB.setLabel(UIActionUtil.getFieldLabel(entity, "accountLocked"));

			binder.forField(nameTF).asRequired().bind("name");
			binder.forField(emailTF).asRequired().bind("email");
			binder.forField(staffNoTF).asRequired().bind("staffNo");
			binder.forField(usernameTF).asRequired().bind("username");
			binder.forField(passwordTF).asRequired().bind("password");
			binder.forField(accountlockCB).bind("accountLocked");
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
		return "Application User";
	}

	@Override
	public void setEventEntityFields(User pojo)
	{
	}

	@Override
	public String getPageMode()
	{
		return pageMode;
	}

	@Override
	public Binder<User> getBinder()
	{
		return binder;
	}

	@Override
	public User getEntity()
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
				entity = new User();
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
	public Class<UsersView> getCloseNavigationClass()
	{
		return UsersView.class;
	}

	@Override
	public void saveRecord(User entity) {
		repository.save(entity);
	}
}

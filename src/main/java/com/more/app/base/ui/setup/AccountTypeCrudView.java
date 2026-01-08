package com.more.app.base.ui.setup;

import org.springframework.beans.factory.annotation.Autowired;

import com.more.app.base.ui.BaseCrudComponent;
import com.more.app.base.ui.CrudPageView;
import com.more.app.entity.AbstractPojo;
import com.more.app.entity.AccountType;
import com.more.app.repository.AccountTypeRepository;
import com.more.app.util.annotations.UIActionUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = AccountTypeCrudView.pageUrlString, layout = CrudPageView.class)
public class AccountTypeCrudView extends BaseCrudComponent<AccountType> implements HasUrlParameter<String>
{
	private static final long serialVersionUID = 7123664526703976224L;
	public static final String pageUrlString = "accounttype";
	private Binder<AccountType> binder = new Binder<>(AccountType.class);

	public AccountTypeCrudView()
	{
		super();
		vl.add(codeTF, descriptionTF,accountRoleSF, directPostingCB);
	}

	private TextField codeTF, descriptionTF;
	private Checkbox directPostingCB;
	private Select accountRoleSF;
	

	@Autowired
	private AccountTypeRepository accountTypeRepository;

	@Override
	public void initializeComponents()
	{
		codeTF = new TextField();
		codeTF.setMaxLength(2);
		codeTF.setRequired(true);
		codeTF.setRequiredIndicatorVisible(true);

		descriptionTF = new TextField();
		descriptionTF.setMaxLength(35);
		descriptionTF.setMinWidth("250px");
		descriptionTF.setRequired(true);
		descriptionTF.setRequiredIndicatorVisible(true);

		accountRoleSF = new Select();
		accountRoleSF.setItems("Asset", "Liability", "Income", "Expense", "Contigent Asset", "Contigent Liability","Customer Account", "Nostro Account");

		directPostingCB = new Checkbox();
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
		if (entity != null)
		{
			binder.setBean(entity);
			codeTF.setLabel(UIActionUtil.getFieldLabel(entity, "code"));
			descriptionTF.setLabel(UIActionUtil.getFieldLabel(entity, "description"));
			accountRoleSF.setLabel(UIActionUtil.getFieldLabel(entity, "accountRole"));
			directPostingCB.setLabel(UIActionUtil.getFieldLabel(entity, "directPosting"));

			binder.forField(codeTF).bind("code");
			binder.forField(descriptionTF).bind("description");
			binder.forField(accountRoleSF).bind("accountRole");
			binder.forField(directPostingCB).bind("directPosting");
		}
		if (getPageMode().equals("ADD") || getPageMode().equals("ADDNEW") || getPageMode().equals("EDIT"))
		{
			confirmButton.setVisible(true);
			addewButton.setVisible(true);
		}
	}

	@Override
	public void setSelectedEntity(AbstractPojo pojo)
	{
	}

	@Override
	public void clearSelectedEntity(AccountType pojo)
	{
	}

	@Override
	public boolean fullsize()
	{
		return false;
	}

	@Override
	public String getPageTitle()
	{
		return "Account Type";
	}

	@Override
	public String getPageMode()
	{
		return pageMode;
	}

	@Override
	public Binder<AccountType> getBinder()
	{
		return binder;
	}

	@Override
	public AccountType getEntity()
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
				entity = new AccountType();
			else
			{
				Long id = Long.valueOf(params[1]);
				if (id == -1L)
					beforeEvent.rerouteTo(getCloseNavigationClass());
				else
					entity = accountTypeRepository.findById(id).orElse(null);
			}
		} else
			getUI().get().navigate(getCloseNavigationClass());
	}

	@Override
	public Class<AccountTypeView> getCloseNavigationClass()
	{
		return AccountTypeView.class;
	}

	@Override
	public void saveRecord(AccountType entity) {
		accountTypeRepository.save(entity);
		
	}
}

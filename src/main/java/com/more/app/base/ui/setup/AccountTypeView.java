package com.more.app.base.ui.setup;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.base.ui.BaseView;
import com.more.app.base.ui.DialogSelectEntity;
import com.more.app.base.ui.LeftAlignedLayout;
import com.more.app.entity.AccountType;
import com.more.app.repository.AccountRepository;
import com.more.app.repository.AccountTypeRepository;
import com.more.app.util.annotations.UIActionUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = AccountTypeView.pageUrlString, layout = LeftAlignedLayout.class)
public class AccountTypeView extends BaseView<AccountType>
{
	private static final long serialVersionUID = 2209861811013802787L;
	public static final String pageUrlString = "accounttype-view";
	public static final Long resourceAdd = Long.valueOf(37L);
	public static final Long resourceEdit = Long.valueOf(38L);
	public static final Long resourceView = Long.valueOf(39L);

	private TextField codeTF;
	private TextField descriptionTF;
	public Select accountRoleSF;
	private boolean forIncome;

	@Autowired
	private AccountTypeRepository accountTypeRepository;
	
	public AccountTypeView()
	{
		super();
		view = this;
	}

	public AccountTypeView(DialogSelectEntity dialog, Dialog dg)
	{
		super(dialog, dg);
	}

	public AccountTypeView(DialogSelectEntity dialog, Dialog dg, boolean forIncome)
	{
		super(dialog, dg);
		this.forIncome = forIncome;
	}
	
	
	public <T> AccountTypeView(DialogSelectEntity dialog, Dialog dg, JpaRepository repository)
	{
		super(dialog, dg, repository);
		this.accountTypeRepository = (AccountTypeRepository) repository;
	}
	

	@Override
	public void loadComponents()
	{
		accountRoleSF = new Select();
		accountRoleSF.setEmptySelectionAllowed(true);
		accountRoleSF.setLabel(UIActionUtil.getFieldLabel(AccountType.class, "accountRole"));
		accountRoleSF.setItems("Asset", "Liability", "Income", "Expense", "Contigent Asset", "Contigent Liability",
				"Customer Account", "Nostro Account");

		codeTF = new TextField(UIActionUtil.getFieldLabel(AccountType.class, "code"));
		codeTF.setMaxLength(50);

		descriptionTF = new TextField(UIActionUtil.getFieldLabel(AccountType.class, "description"));
		descriptionTF.setMaxLength(35);

		grid.addColumn(AccountType::getCode).setHeader(UIActionUtil.getFieldLabel(AccountType.class, "code"));
		grid.addColumn(AccountType::getDescription)
				.setHeader(UIActionUtil.getFieldLabel(AccountType.class, "description"));
		grid.addColumn(AccountType::getAccountRole)
				.setHeader(UIActionUtil.getFieldLabel(AccountType.class, "accountRole"));

		hlsearch.add(accountRoleSF, codeTF, descriptionTF, searchb);
		hlsearch.setVerticalComponentAlignment(Alignment.BASELINE, accountRoleSF, codeTF, descriptionTF, searchb);

		addBaseComponentsandStyle();

		searchb.addClickListener(event ->
			{
				String accountRole = null;
				grid.setItems(new ArrayList<AccountType>());
				String code = codeTF.getValue();
				String description = descriptionTF.getValue();
				if (null != accountRoleSF.getValue())
					accountRole = (String) accountRoleSF.getValue();
				grid.setItems(accountTypeRepository.findByCodeStartsWithAndDescriptionStartsWithAndAccountRoleStartsWith(code, description,accountRole));
			});
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
		//grid.setItems(AccountTypeDAO.findAllOrderedDesc());
		grid.setItems(accountTypeRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));

		if (forIncome)
		{
			//grid.setItems(new ArrayList<AccountType>());
			//grid.setItems(AccountTypeDAO.findIncomeAccountType());
			accountRoleSF.setEmptySelectionAllowed(false);
			accountRoleSF.removeAll();
			accountRoleSF.setItems("Income");
			accountRoleSF.setValue("Income");
		}
	}

	@Override
	public void reloadView()
	{
		grid.setItems(accountTypeRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
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
		getUI().ifPresent(ui -> ui.navigate(AccountTypeCrudView.class, param));
	}

	@Override
	public String getDialogTitle()
	{
		return "Search Account Type";
	}
}

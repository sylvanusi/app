package com.more.app.base.ui.setup;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.base.ui.BaseView;
import com.more.app.base.ui.DialogSelectEntity;
import com.more.app.base.ui.LeftAlignedLayout;
import com.more.app.entity.Account;
import com.more.app.entity.AccountType;
import com.more.app.entity.Customer;
import com.more.app.repository.AccountRepository;
import com.more.app.util.annotations.UIActionUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = AccountView.pageUrlString, layout = LeftAlignedLayout.class)
public class AccountView extends BaseView<Account>
{
	private static final long serialVersionUID = -1847298920450197638L;
	public static final String pageUrlString = "account-view";
	public static final Long resourceAdd = Long.valueOf(40L);
	public static final Long resourceEdit = Long.valueOf(41L);
	public static final Long resourceView = Long.valueOf(42L);
	private TextField accountTypeTF;
	private TextField customerNameTF;
	private Button searchCust;
	private Button searchActy;
	private Customer customer;
	private AccountType accountType;
	
	@Autowired
	private AccountRepository repository;
	
	public <T> AccountView(DialogSelectEntity dialog, Dialog dg, JpaRepository repository)
	{
		super(dialog, dg, repository);
		this.repository = (AccountRepository) repository;
	}

	public AccountView()
	{
		super();
		view = this;
	}

	public AccountView(DialogSelectEntity dialog, Dialog dg)
	{
		super(dialog, dg);
	}

	@Override
	public void loadComponents()
	{
		searchCust = new Button(new Icon(VaadinIcon.SEARCH));
		searchActy = new Button(new Icon(VaadinIcon.SEARCH));

		customerNameTF = new TextField(UIActionUtil.getFieldLabel(Account.class, "customerName"));
		customerNameTF.setMaxLength(35);

		accountTypeTF = new TextField(UIActionUtil.getFieldLabel(Account.class, "accountType"));
		accountTypeTF.setMaxLength(2);

		grid.addColumn(Account::getCustomerName).setHeader(UIActionUtil.getFieldLabel(Account.class, "customerName"));
		grid.addColumn(Account::getAccountTypeCode)
				.setHeader(UIActionUtil.getFieldLabel(Account.class, "accountTypeCode"));
		grid.addColumn(Account::getBranchCode).setHeader(UIActionUtil.getFieldLabel(Account.class, "branchCode"));
		grid.addColumn(Account::getCurrency).setHeader(UIActionUtil.getFieldLabel(Account.class, "currency"));
		grid.addColumn(Account::getAccountName).setHeader(UIActionUtil.getFieldLabel(Account.class, "accountName"));
		grid.addColumn(Account::getAccountNo).setHeader(UIActionUtil.getFieldLabel(Account.class, "accountNo"));

		hlsearch.add(customerNameTF, searchCust, accountTypeTF, searchActy, searchb);
		hlsearch.setVerticalComponentAlignment(Alignment.BASELINE, customerNameTF, searchCust, accountTypeTF,
				searchActy, searchb);

		addBaseComponentsandStyle();

		searchb.addClickListener(new ComponentEventListener<ClickEvent<Button>>()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event)
			{
				grid.setItems(new ArrayList<Account>());
				grid.setItems(repository.findByCustomerIdStartsWithAndAccountTypeIdStartsWith(customer.getId(), accountType.getId()));
			}

		});

		searchCust.addClickListener(event ->
		{
			customer = null;
			CustomerView cview = new CustomerView();
			cview.getHl().setVisible(false);
			Button selectItem = new Button("Select Item");
			selectItem.setSizeFull();
			selectItem.addClickListener(evt ->
			{
				if (!cview.grid.getSelectedItems().isEmpty())
				{
					Object[] arr = cview.grid.getSelectedItems().toArray();
					Customer cust = (Customer) arr[0];
					customer = cust;
					customerNameTF.setValue(cust.getFullName());
				}
			});
			cview.add(selectItem);
			Dialog dg = new Dialog();
			dg.add(cview);
			dg.open();
		});

		searchActy.addClickListener(event ->
		{
			accountType = null;
			AccountTypeView cview = new AccountTypeView();
			cview.getHl().setVisible(false);
			Button selectItem = new Button("Select Item");
			selectItem.setSizeFull();
			selectItem.addClickListener(evt ->
			{
				if (!cview.grid.getSelectedItems().isEmpty())
				{
					Object[] arr = cview.grid.getSelectedItems().toArray();
					AccountType act = (AccountType) arr[0];
					accountType = act;
					accountTypeTF.setValue(accountType.getCode());
				}

			});
			cview.add(selectItem);
			Dialog dg = new Dialog();
			dg.add(cview);
			dg.open();
		});
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
		grid.setItems(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
	}

	@Override
	public void reloadView()
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
		getUI().ifPresent(ui -> ui.navigate(AccountCrudView.class, param));
	}

	@Override
	public String getDialogTitle()
	{
		return "Search Account";
	}
}

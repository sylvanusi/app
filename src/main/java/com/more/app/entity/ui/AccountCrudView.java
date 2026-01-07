package com.more.app.entity.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.more.app.annotations.UIActionUtil;
import com.more.app.base.ui.BaseCrudComponent;
import com.more.app.base.ui.CrudPageView;
import com.more.app.entity.AbstractPojo;
import com.more.app.entity.Account;
import com.more.app.entity.AccountType;
import com.more.app.entity.Branch;
import com.more.app.entity.Currency;
import com.more.app.entity.Customer;
import com.more.app.repository.AccountRepository;
import com.more.app.repository.AccountTypeRepository;
import com.more.app.repository.BranchRepository;
import com.more.app.repository.CurrencyRepository;
import com.more.app.repository.CustomerRepository;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = AccountCrudView.pageUrlString, layout = CrudPageView.class)
public class AccountCrudView extends BaseCrudComponent<Account> implements HasUrlParameter<String>
{
	private static final long serialVersionUID = 7123664526703976224L;
	public static final String pageUrlString = "account";
	private Binder<Account> binder = new Binder<>(Account.class);

	private AccountType accountType;
	private Customer customer;
	private Branch branch;
	private Currency currency;
	
	@Autowired
	private AccountRepository repository;
	
	@Autowired
	private CustomerRepository customerrepository;
	
	@Autowired
	private BranchRepository branchrepository;
	
	@Autowired
	private AccountTypeRepository acctTyperepository;
	
	@Autowired
	private CurrencyRepository currencyrepository;

	public AccountCrudView()
	{
		super();
		
		Button getCustomer = new Button("Get Customer", new Icon(VaadinIcon.SEARCH), event ->
		{
			Dialog dg = new Dialog();
			CustomerView v = new CustomerView(ui, dg, customerrepository);
			dg.add(v);
			dg.open();
		});

		HorizontalLayout customerhl = new HorizontalLayout();
		customerhl.add(customerNameTF, getCustomer);
		customerhl.setVerticalComponentAlignment(Alignment.BASELINE, customerNameTF, getCustomer);
		
		Button getAccountType = new Button("Get Account Type", new Icon(VaadinIcon.SEARCH), event ->
		{
			Dialog dg = new Dialog();
			AccountTypeView v = new AccountTypeView(ui, dg,acctTyperepository);
			dg.add(v);
			dg.open();
		});

		HorizontalLayout accountTypehl = new HorizontalLayout();
		accountTypehl.add(accountTypeCodeTF, getAccountType);
		accountTypehl.setVerticalComponentAlignment(Alignment.BASELINE, accountTypeCodeTF, getAccountType);
		
		
		Button getBranch = new Button("Get Branch", new Icon(VaadinIcon.SEARCH), event ->
		{
			Dialog dg = new Dialog();
			BranchView v = new BranchView(ui, dg, branchrepository);
			dg.add(v);
			dg.open();
		});

		HorizontalLayout branchl = new HorizontalLayout();
		branchl.add(branchCodeTF, getBranch);
		branchl.setVerticalComponentAlignment(Alignment.BASELINE, branchCodeTF, getBranch);

		
		Button getCurrency = new Button("Get Currency", new Icon(VaadinIcon.SEARCH), event ->
		{
			Dialog dg = new Dialog();
			CurrencyView v = new CurrencyView(ui, dg, currencyrepository);
			dg.add(v);
			dg.open();
		});

		HorizontalLayout currencyhl = new HorizontalLayout();
		currencyhl.add(currencyTF, getCurrency);
		currencyhl.setVerticalComponentAlignment(Alignment.BASELINE, currencyTF, getCurrency);

		vl.add(customerhl, accountTypehl, branchl, currencyhl, accountNameTF, accountNoTF,
				boAccountNoTF, ibanTF, countryTF);
	}

	private TextField customerNameTF, accountTypeCodeTF, branchCodeTF, currencyTF, accountNameTF, accountNoTF,
	boAccountNoTF, ibanTF, countryTF;

	@Override
	public void initializeComponents()
	{
		customerNameTF = new TextField();
		customerNameTF.setMaxLength(35);
		customerNameTF.setMinWidth("250px");
		customerNameTF.setRequired(true);
		customerNameTF.setRequiredIndicatorVisible(true);

		accountTypeCodeTF = new TextField();
		accountTypeCodeTF.setMaxLength(2);
		accountTypeCodeTF.setMinWidth("250px");
		accountTypeCodeTF.setRequired(true);
		accountTypeCodeTF.setRequiredIndicatorVisible(true);

		branchCodeTF = new TextField();
		branchCodeTF.setMaxLength(10);
		branchCodeTF.setMinWidth("250px");
		branchCodeTF.setRequired(true);
		branchCodeTF.setRequiredIndicatorVisible(true);

		currencyTF = new TextField();
		currencyTF.setMaxLength(3);
		currencyTF.setMinWidth("250px");
		currencyTF.setRequired(true);
		currencyTF.setRequiredIndicatorVisible(true);

		accountNameTF = new TextField();
		accountNameTF.setMaxLength(35);
		accountNameTF.setMinWidth("250px");
		accountNameTF.setRequired(true);
		accountNameTF.setRequiredIndicatorVisible(true);

		accountNoTF = new TextField();
		accountNoTF.setMaxLength(34);
		accountNoTF.setMinWidth("250px");
		accountNoTF.setRequired(true);
		accountNoTF.setRequiredIndicatorVisible(true);

		boAccountNoTF = new TextField();
		boAccountNoTF.setMaxLength(34);
		boAccountNoTF.setMinWidth("250px");

		ibanTF = new TextField();
		ibanTF.setMaxLength(34);
		ibanTF.setMinWidth("250px");

		countryTF = new TextField();
		countryTF.setMinWidth("250px");
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
		if (null != entity.getAccountTypeId())
		{
			AccountType acctType = acctTyperepository.findById(entity.getAccountTypeId()).get();
			entity.setAccountType(acctType);
			entity.setAccountTypeCode(acctType.getCode());
		}
		if (null != entity.getBranchId())
		{
			Branch branch = branchrepository.findById(entity.getBranchId()).get();
			entity.setBranch(branch);
			entity.setBranchCode(branch.getCode());
		}
		
		if (null != entity.getCustomerId()) {
			Customer customer = customerrepository.findById(entity.getCustomerId()).get();
			if (null != customer) {
				entity.setCustomer(customer);
				entity.setCustomerName(customer.getFullName());
			}
		}

		
		
		if (entity != null)
		{
			binder.setBean(entity);
			customerNameTF.setLabel(UIActionUtil.getFieldLabel(entity, "customerName"));
			accountTypeCodeTF.setLabel(UIActionUtil.getFieldLabel(entity, "accountTypeCode"));
			branchCodeTF.setLabel(UIActionUtil.getFieldLabel(entity, "branchCode"));
			currencyTF.setLabel(UIActionUtil.getFieldLabel(entity, "currency"));
			accountNameTF.setLabel(UIActionUtil.getFieldLabel(entity, "accountName"));
			accountNoTF.setLabel(UIActionUtil.getFieldLabel(entity, "accountNo"));
			boAccountNoTF.setLabel(UIActionUtil.getFieldLabel(entity, "boAccountNo"));
			ibanTF.setLabel(UIActionUtil.getFieldLabel(entity, "iban"));
			countryTF.setLabel(UIActionUtil.getFieldLabel(entity, "country"));

			binder.forField(customerNameTF).asRequired("").bind("customerName");
			binder.forField(accountTypeCodeTF).asRequired("").bind("accountTypeCode");
			binder.forField(branchCodeTF).asRequired("").bind("branchCode");
			binder.forField(currencyTF).asRequired("").bind("currency");
			binder.forField(accountNameTF).asRequired("").bind("accountName");
			binder.forField(accountNoTF).asRequired("").bind("accountNo");
			binder.forField(boAccountNoTF).bind("boAccountNo");
			binder.forField(ibanTF).bind("iban");
			binder.forField(countryTF).bind("country");
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
		if (pojo instanceof Customer)
		{
			((AccountCrudView) ui).setCustomer((Customer) pojo);
			customerNameTF.setValue(getCustomer().getFullName());
			customerNameTF.setReadOnly(true);
			getBinder().getBean().setCustomer(getCustomer());
			getBinder().getBean().setCustomerName(getCustomer().getFullName());
			getBinder().getBean().setCustomerId(getCustomer().getId());
		}
		if (pojo instanceof AccountType)
		{
			((AccountCrudView) ui).setAccountType((AccountType) pojo);
			accountTypeCodeTF.setValue(getAccountType().getCode());
			accountTypeCodeTF.setReadOnly(true);
			getBinder().getBean().setAccountType(getAccountType());
			getBinder().getBean().setAccountTypeId(getAccountType().getId());
			getBinder().getBean().setAccountTypeCode(getAccountType().getCode());
		}
		if (pojo instanceof Branch)
		{
			((AccountCrudView) ui).setBranch((Branch) pojo);
			branchCodeTF.setValue(getBranch().getCode());
			branchCodeTF.setReadOnly(true);
			getBinder().getBean().setBranch(getBranch());
			getBinder().getBean().setBranchId(getBranch().getId());
		}
		if (pojo instanceof Currency)
		{
			((AccountCrudView) ui).setCurrency((Currency) pojo);
			currencyTF.setValue(getCurrency().getCode());
			currencyTF.setReadOnly(true);
			getBinder().getBean().setCurrency(getCurrency().getCode());
			//getBinder().getBean().setCurrencyId(getCurrency().getId());
		}
	}

	@Override
	public void clearSelectedEntity(Account entity)
	{
		entity.setCustomer(null);
		entity.setAccountType(null);
		entity.setBranch(null);
	}

	@Override
	public boolean fullsize()
	{
		return false;
	}

	@Override
	public String getPageTitle()
	{
		return "Account";
	}

	public AccountType getAccountType()
	{
		return accountType;
	}

	public Customer getCustomer()
	{
		return customer;
	}

	public Branch getBranch()
	{
		return branch;
	}

	public void setAccountType(AccountType accountType)
	{
		this.accountType = accountType;
	}

	public void setCustomer(Customer customer)
	{
		this.customer = customer;
	}

	public void setBranch(Branch branch)
	{
		this.branch = branch;
	}

	public Currency getCurrency()
	{
		return currency;
	}

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}

	@Override
	public String getPageMode()
	{
		return pageMode;
	}

	@Override
	public Binder<Account> getBinder()
	{
		return binder;
	}

	@Override
	public Account getEntity()
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
				entity = new Account();
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
	public Class<AccountView> getCloseNavigationClass()
	{
		return AccountView.class;
	}

	@Override
	public void saveRecord(Account entity) {
		repository.save(entity);
	}
}

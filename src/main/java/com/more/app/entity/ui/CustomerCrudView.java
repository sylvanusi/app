package com.more.app.entity.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.more.app.annotations.UIActionUtil;
import com.more.app.base.ui.BaseCrudComponent;
import com.more.app.base.ui.CrudPageView;
import com.more.app.entity.AbstractPojo;
import com.more.app.entity.AccountOfficer;
import com.more.app.entity.Country;
import com.more.app.entity.Customer;
import com.more.app.entity.SwiftBic;
import com.more.app.repository.AccountOfficerRepository;
import com.more.app.repository.CountryRepository;
import com.more.app.repository.CustomerRepository;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

import ch.carnet.kasparscherrer.EmptyFormLayoutItem;

@Route(value = CustomerCrudView.pageUrlString, layout = CrudPageView.class)
public class CustomerCrudView extends BaseCrudComponent<Customer> implements HasUrlParameter<String>
{
	private static final long serialVersionUID = -4998392385704501954L;
	public static final String pageUrlString = "customer";
	private Binder<Customer> binder = new Binder<>(Customer.class);
	private AccountOfficer accountOfficer;
	private Country country;
	private SwiftBic swiftBic;
	
	@Autowired
	private CustomerRepository repository;

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private AccountOfficerRepository accOfficerRepository;
	
	public CustomerCrudView()
	{
		super();

		Button getCountry = new Button("Get Country", new Icon(VaadinIcon.SEARCH), event ->
		{
			Dialog dg = new Dialog();
			CountryView cv = new CountryView(ui, dg, countryRepository);
			dg.add(cv);
			dg.open();
		});

		HorizontalLayout ctryhl = new HorizontalLayout();
		ctryhl.add(countryNameTF, getCountry);
		ctryhl.setVerticalComponentAlignment(Alignment.BASELINE, countryNameTF, getCountry);

		Button getAccountOf = new Button("Get Account Officer", new Icon(VaadinIcon.SEARCH), event ->
		{
			Dialog dg = new Dialog();
			AccountOfficerView cv = new AccountOfficerView(ui, dg, accOfficerRepository);
			dg.add(cv);
			dg.open();
		});

		HorizontalLayout actohl = new HorizontalLayout();
		actohl.add(accountOfficerNameTF, getAccountOf);
		actohl.setVerticalComponentAlignment(Alignment.BASELINE, accountOfficerNameTF, getAccountOf);

		Button getSwift = new Button("Get Swift BIC", new Icon(VaadinIcon.SEARCH), event ->
		{
			//Dialog dg = new Dialog();
			//SwiftBicView cv = new SwiftBicView(ui, dg);
			//dg.add(cv);
			//dg.open();
		});

		HorizontalLayout sbhl = new HorizontalLayout();
		sbhl.add(swiftAddressTF, getSwift);
		sbhl.setVerticalComponentAlignment(Alignment.BASELINE, swiftAddressTF, getSwift);

		formLayout.setMaxWidth("1000px");
		formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));

		VerticalLayout vlin = new VerticalLayout();
		vlin.add(address1TF, address2TF, address3TF, address4TF);
		vlin.setSizeFull();
		vlin.setMargin(false);

		formLayout.add(customerTypeTF, fullNameTF, customerNoTF, aliasTF, vlin, new EmptyFormLayoutItem(), cityTF,
				phoneNoTF, emailTF, ctryhl, phoneNoTF, actohl, new EmptyFormLayoutItem(), correspondentBox, sbhl);
		vl.add(formLayout);
	}

	private TextField fullNameTF, customerNoTF, aliasTF, address1TF, address2TF, address3TF, address4TF, cityTF,
			phoneNoTF, emailTF, accountOfficerNameTF, countryNameTF, swiftAddressTF;
	private Select<Boolean> correspondentBox;
	private Select<String> customerTypeTF;

	private FormLayout formLayout = new FormLayout();

	@Override
	public void initializeComponents()
	{
		customerTypeTF = new Select<String>();
		customerTypeTF.setItems("", "Customer", "Bank");
		customerTypeTF.setRequiredIndicatorVisible(true);

		fullNameTF = new TextField();
		fullNameTF.setMaxLength(50);
		fullNameTF.setRequired(true);
		fullNameTF.setRequiredIndicatorVisible(true);

		customerNoTF = new TextField();
		customerNoTF.setMaxLength(35);
		customerNoTF.setRequired(true);
		customerNoTF.setRequiredIndicatorVisible(true);

		aliasTF = new TextField();
		aliasTF.setMaxLength(15);
		aliasTF.setRequired(true);
		aliasTF.setRequiredIndicatorVisible(true);

		correspondentBox = new Select<Boolean>();
		correspondentBox.setEmptySelectionAllowed(true);
		correspondentBox.setItems(true, false);

		address1TF = new TextField();
		address1TF.setMaxLength(35);
		address1TF.setRequired(true);
		address1TF.setRequiredIndicatorVisible(true);
		address1TF.setSizeFull();

		address2TF = new TextField();
		address2TF.setMaxLength(35);
		address2TF.setSizeFull();

		address3TF = new TextField();
		address3TF.setMaxLength(35);
		address3TF.setSizeFull();

		address4TF = new TextField();
		address4TF.setMaxLength(35);
		address4TF.setSizeFull();

		cityTF = new TextField();
		cityTF.setMaxLength(35);

		phoneNoTF = new TextField();
		phoneNoTF.setMaxLength(35);
		phoneNoTF.setRequired(true);
		phoneNoTF.setRequiredIndicatorVisible(true);

		emailTF = new TextField();
		emailTF.setMaxLength(35);
		emailTF.setRequired(true);
		emailTF.setRequiredIndicatorVisible(true);

		accountOfficerNameTF = new TextField();
		accountOfficerNameTF.setMaxLength(35);

		countryNameTF = new TextField();
		countryNameTF.setMaxLength(35);
		countryNameTF.setRequired(true);
		countryNameTF.setRequiredIndicatorVisible(true);

		swiftAddressTF = new TextField();
		swiftAddressTF.setMaxLength(35);
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
		if (entity != null)
		{
			binder.setBean(entity);
			customerTypeTF.setLabel(UIActionUtil.getFieldLabel(entity, "customerType"));
			customerNoTF.setLabel(UIActionUtil.getFieldLabel(entity, "customerNo"));
			fullNameTF.setLabel(UIActionUtil.getFieldLabel(entity, "fullName"));
			aliasTF.setLabel(UIActionUtil.getFieldLabel(entity, "alias"));
			correspondentBox.setLabel(UIActionUtil.getFieldLabel(Customer.class, "correspondent"));
			address1TF.setLabel(UIActionUtil.getFieldLabel(entity, "address1"));
			cityTF.setLabel(UIActionUtil.getFieldLabel(entity, "city"));
			phoneNoTF.setLabel(UIActionUtil.getFieldLabel(entity, "phoneNo"));
			emailTF.setLabel(UIActionUtil.getFieldLabel(entity, "email"));
			accountOfficerNameTF.setLabel(UIActionUtil.getFieldLabel(entity, "accountOfficerName"));
			countryNameTF.setLabel(UIActionUtil.getFieldLabel(entity, "country"));
			swiftAddressTF.setLabel(UIActionUtil.getFieldLabel(entity, "swiftAddress"));

			binder.forField(customerTypeTF).asRequired().bind("customerType");
			binder.forField(fullNameTF).asRequired().bind("fullName");
			binder.forField(customerNoTF).asRequired().bind("customerNo");
			binder.forField(aliasTF).asRequired().bind("alias");
			binder.forField(correspondentBox).bind("correspondent");
			binder.forField(address1TF).asRequired().bind("address1");
			binder.forField(address2TF).bind("address2");
			binder.forField(address3TF).bind("address3");
			binder.forField(address4TF).bind("address4");
			binder.forField(cityTF).bind("city");
			binder.forField(phoneNoTF).asRequired().bind("phoneNo");
			binder.forField(emailTF).asRequired().bind("email");
			binder.forField(accountOfficerNameTF).bind("accountOfficerName");
			binder.forField(countryNameTF).asRequired().bind("countryName");
			binder.forField(swiftAddressTF).bind("swiftAddress");
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
		if (pojo instanceof AccountOfficer)
		{
			((CustomerCrudView) ui).setAccountOfficer((AccountOfficer) pojo);
			accountOfficerNameTF.setValue(getAccountOfficer().getFullName());
			accountOfficerNameTF.setReadOnly(true);
			getBinder().getBean().setAccountOfficer(getAccountOfficer());
			getBinder().getBean().setAccountOfficerId(getAccountOfficer().getId());
		}
		if (pojo instanceof Country)
		{
			((CustomerCrudView) ui).setCountry((Country) pojo);
			countryNameTF.setValue(getCountry().getCountryName());
			countryNameTF.setReadOnly(true);
			getBinder().getBean().setCountry(getCountry());
			getBinder().getBean().setCountryId(getCountry().getId());
		}
		if (pojo instanceof SwiftBic)
		{
			((CustomerCrudView) ui).setSwiftBic((SwiftBic) pojo);
			swiftAddressTF.setValue(getSwiftBic().getSwiftBic());
			swiftAddressTF.setReadOnly(true);
		}
	}

	@Override
	public void setEventEntityFields(Customer entity)
	{
		if (null != getAccountOfficer())
			entity.setAccountOfficer(getAccountOfficer());
		if (null != getCountry())
			entity.setCountry(getCountry());
	}

	@Override
	public void clearSelectedEntity(Customer pojo)
	{

		entity.setAccountOfficer(null);
		entity.setCountry(null);
	}

	@Override
	public boolean fullsize()
	{
		return false;
	}

	@Override
	public String getPageTitle()
	{
		return "Customer Details";
	}

	/**
	 * @return the accountOfficer
	 */
	public AccountOfficer getAccountOfficer()
	{
		return accountOfficer;
	}

	/**
	 * @return the country
	 */
	public Country getCountry()
	{
		return country;
	}

	/**
	 * @param accountOfficer the accountOfficer to set
	 */
	public void setAccountOfficer(AccountOfficer accountOfficer)
	{
		this.accountOfficer = accountOfficer;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(Country country)
	{
		this.country = country;
	}

	/**
	 * @return the swiftBic
	 */
	public SwiftBic getSwiftBic()
	{
		return swiftBic;
	}

	public void setSwiftBic(SwiftBic swiftBic)
	{
		this.swiftBic = swiftBic;
	}

	@Override
	public String getPageMode()
	{
		return pageMode;
	}

	@Override
	public Binder<Customer> getBinder()
	{
		return binder;
	}

	@Override
	public Customer getEntity()
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
				entity = new Customer();
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
	public Class<CustomerView> getCloseNavigationClass()
	{
		return CustomerView.class;
	}

	@Override
	public void saveRecord(Customer entity) {
		repository.save(entity);
	}
}

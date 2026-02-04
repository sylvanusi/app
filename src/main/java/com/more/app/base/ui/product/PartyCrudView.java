package com.more.app.base.ui.product;

import org.springframework.beans.factory.annotation.Autowired;

import com.more.app.base.ui.BaseCrudComponent;
import com.more.app.base.ui.CrudPageView;
import com.more.app.base.ui.setup.AccountView;
import com.more.app.base.ui.setup.CountryView;
import com.more.app.base.ui.setup.SwiftBicView;
import com.more.app.entity.AbstractPojo;
import com.more.app.entity.Country;
import com.more.app.entity.SwiftBic;
import com.more.app.entity.product.Party;
import com.more.app.repository.AccountRepository;
import com.more.app.repository.CountryRepository;
import com.more.app.repository.SwiftBicRepository;
import com.more.app.repository.product.PartyRepository;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

import ch.carnet.kasparscherrer.EmptyFormLayoutItem;

@Route(value = PartyCrudView.pageUrlString, layout = CrudPageView.class)
public class PartyCrudView extends BaseCrudComponent<Party> implements HasUrlParameter<String> {
	public static final String pageUrlString = "party-crud";
	
	@Autowired
	private PartyRepository repository;
	@Autowired
	private CountryRepository countryrepo;
	@Autowired
	private AccountRepository accountrepo;
	@Autowired
	private SwiftBicRepository bicrepo;

	private TextField account;
	private TextField bicCode;
	private TextField name;

	private TextField address1;
	private TextField address2;
	private TextField address3;

	private TextField location;

	private TextField identifierCode;
	private TextField identifierCountryCode;
	private TextField identifier;

	private ComboBox<String> swiftPartyType;
	private Binder<Party> binder;
	private FormLayout formLayout = new FormLayout();
	
	private Button getAccountBtn, getBicBtn, getCountryBtn;
	private HorizontalLayout hlCountry, hlBic;

	public PartyCrudView() {
		super();

		formLayout.setMaxWidth("1000px");
		formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
		
		hlBic.add(bicCode, getBicBtn);
		hlBic.setVerticalComponentAlignment(Alignment.BASELINE, bicCode, getBicBtn);

		hlCountry.add(identifierCountryCode, getCountryBtn);
		hlBic.setVerticalComponentAlignment(Alignment.BASELINE, identifierCountryCode, getCountryBtn);

		formLayout.add(swiftPartyType, new EmptyFormLayoutItem(), account, hlBic, name, address1,new EmptyFormLayoutItem(), address2,new EmptyFormLayoutItem(), address3, location, hlCountry, identifierCode,
				identifier);
		 vl.add(formLayout);
		
	}

	@Override
	public void initializeComponents() {
		binder = new Binder<>(Party.class);

		account = new TextField("Account No");
		getAccountBtn = new Button("Search", new Icon(VaadinIcon.SEARCH));
		bicCode = new TextField("BIC Code");
		getBicBtn = new Button("Search", new Icon(VaadinIcon.SEARCH));
		name = new TextField("Name");

		address1 = new TextField("Address Line 1");
		address2 = new TextField("Address Line 2");
		address3 = new TextField("Address Line 3");

		location = new TextField("Location");

		identifierCode = new TextField("Identifier Code");
		identifierCountryCode = new TextField("Country");
		getCountryBtn = new Button("Search", new Icon(VaadinIcon.SEARCH));
		identifier = new TextField("Identifier");

		swiftPartyType = new ComboBox<>("Party Option");

		account.setMaxLength(34);
		bicCode.setMaxLength(11);
		bicCode.setMaxWidth(50, Unit.PERCENTAGE);
		name.setMaxLength(34);
		address1.setMaxLength(34);
		address2.setMaxLength(34);
		address3.setMaxLength(34);
		location.setMaxLength(34);
		identifierCode.setMaxLength(34);
		identifierCode.setMaxWidth(30, Unit.PERCENTAGE);
		identifierCountryCode.setMaxLength(2);
		identifierCountryCode.setMaxWidth(30, Unit.PERCENTAGE);
		identifier.setMaxLength(34);

		swiftPartyType.setItems("","A", "F", "K");
		swiftPartyType.setMaxWidth(50, Unit.PERCENTAGE);
		
		hlCountry = new HorizontalLayout(); 
		hlBic = new HorizontalLayout(); 

		//add(swiftPartyType, account, bicCode, name, address1, address2, address3, location, identifierCode,
		//		identifierCountryCode, identifier);
		
		swiftPartyType.addValueChangeListener(event -> {
			if ("A".equals(event.getValue()))
			{
				
			}
			
			if ("F".equals(event.getValue())) {
				
			}
		});

		binder.forField(swiftPartyType).withValidator(party -> {


			if ("A".equals(party)) {
				return binder.getBean().getBicCode() != null;
			}

			if ("F".equals(party)) {
				return binder.getBean().getName() != null && binder.getBean().getAddress1() != null;
			}

			return true;

		}, "Party Option validation failed").bind(Party::getSwiftPartyType, Party::setSwiftPartyType);

		binder.forField(account).bind(Party::getAccount, Party::setAccount);
		//binder.forField(swiftPartyType).bind(Party::getSwiftPartyType, Party::setSwiftPartyType);

		binder.forField(bicCode).withValidator(SwiftValidator::bic, "Invalid BIC").bind(Party::getBicCode,
				Party::setBicCode);
		binder.forField(identifierCountryCode)
				.withValidator(SwiftValidator::country, "Country must be ISO 2-letter code")
				.bind(Party::getIdentifierCountryCode, Party::setIdentifierCountryCode);
		binder.forField(name).bind(Party::getName,
				Party::setName);
		binder.forField(address1)
				.bind(Party::getAddress1, Party::setAddress1);
		binder.forField(address2)
				.bind(Party::getAddress2, Party::setAddress2);
		binder.forField(address3)
				.bind(Party::getAddress3, Party::setAddress3);
		binder.forField(location)
				.bind(Party::getLocation, Party::setLocation);
		binder.forField(identifierCode).bind(Party::getIdentifierCode, Party::setIdentifierCode);
		binder.forField(identifier).bind(Party::getIdentifier, Party::setIdentifier);

		bicCode.setValueChangeMode(ValueChangeMode.EAGER);
		bicCode.addValueChangeListener(e -> bicCode.setValue(e.getValue().toUpperCase()));
		
		getCountryBtn.addClickListener(event -> {
			Dialog dg = new Dialog();
			CountryView cv = new CountryView(ui, dg, countryrepo);
			dg.add(cv);
			dg.open();
		});
		getBicBtn.addClickListener(event -> {
			Dialog dg = new Dialog();
			SwiftBicView cv = new SwiftBicView(ui, dg, bicrepo);
			dg.add(cv);
			dg.open();
		});
		
		getAccountBtn.addClickListener(event -> {
			Dialog dg = new Dialog();
			AccountView cv = new AccountView(ui, dg, accountrepo);
			dg.add(cv);
			dg.open();
		});

	}

	@Override
	public void setParameter(BeforeEvent event, String parameter) {
		if (parameter != null && !parameter.isEmpty()) {
			String params[] = parameter.split(",");
			pageMode = params[0];
			setPageMode(pageMode);
			if ("ADD".equals(pageMode))
				entity = new Party();
			else {
				Long id = Long.valueOf(params[1]);
				if (id == -1L)
					event.rerouteTo(getCloseNavigationClass());
				else
					entity = repository.findById(id).orElse(null);
			}
		} else
			getUI().get().navigate(getCloseNavigationClass());

	}

	@Override
	public Class<PartyView> getCloseNavigationClass() {
		return PartyView.class;
	}
	
	private Country country;
	private SwiftBic bic;


	@Override
	public void setSelectedEntity(AbstractPojo pojo) {
		if (pojo instanceof Country) {
			((PartyCrudView) ui).setCountry((Country) pojo);
			identifierCountryCode.setValue(getCountry().getCode());
			identifierCountryCode.setReadOnly(true);
		}
		if (pojo instanceof SwiftBic) {
			((PartyCrudView) ui).setBic((SwiftBic) pojo);
			bicCode.setValue(getBic().getSwiftBic());
			//identifierCountryCode.setReadOnly(true);
		}
	}

	@Override
	public void clearSelectedEntity(Party pojo) {
		setCountry(null);
		setBic(null);
	}

	@Override
	public boolean fullsize() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getPageTitle() {
		return "Party";
	}

	@Override
	public String getPageMode() {
		return pageMode;
	}

	@Override
	public Binder<Party> getBinder() {
		return binder;
	}

	@Override
	public Party getEntity() {
		return entity;
	}

	@Override
	public void saveRecord(Party entity) {
		repository.save(entity);
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		if (entity != null) {
			binder.setBean(entity);
			if (getPageMode().equals("ADD") || getPageMode().equals("ADDNEW") || getPageMode().equals("EDIT")) {
				confirmButton.setVisible(true);
				addewButton.setVisible(true);
			}
		}
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public SwiftBic getBic() {
		return bic;
	}

	public void setBic(SwiftBic bic) {
		this.bic = bic;
	}
}

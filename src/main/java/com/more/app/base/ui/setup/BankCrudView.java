package com.more.app.base.ui.setup;

import org.springframework.beans.factory.annotation.Autowired;

import com.more.app.base.ui.BaseCrudComponent;
import com.more.app.base.ui.CrudPageView;
import com.more.app.entity.AbstractPojo;
import com.more.app.entity.Bank;
import com.more.app.entity.Branch;
import com.more.app.entity.Country;
import com.more.app.entity.Currency;
import com.more.app.entity.Parameters;
import com.more.app.repository.BankRepository;
import com.more.app.repository.CountryRepository;
import com.more.app.repository.CurrencyRepository;
import com.more.app.repository.ParametersRepository;
import com.more.app.util.annotations.UIActionUtil;
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

@Route(value = BankCrudView.pageUrlString, layout = CrudPageView.class)
public class BankCrudView extends BaseCrudComponent<Bank> implements HasUrlParameter<String> {
	private static final long serialVersionUID = -5511035586185573430L;
	public static final String pageUrlString = "bank";
	private Country country;
	private Currency localCurrency;
	private Branch hOBranch;
	private Parameters bankGroupParam;

	@Autowired
	private BankRepository repo;

	@Autowired
	private CountryRepository countryrepo;

	@Autowired
	private CurrencyRepository currencyrepo;

	@Autowired
	private ParametersRepository parametersrepo;

	public BankCrudView() {

		super();

		Button getCountry = new Button("Get Country", new Icon(VaadinIcon.SEARCH), event -> {
			Dialog dg = new Dialog();
			CountryView cv = new CountryView(ui, dg, countryrepo);
			dg.add(cv);
			dg.open();
		});

		HorizontalLayout ctryhl = new HorizontalLayout();
		ctryhl.add(countryNameTF, getCountry);
		ctryhl.setVerticalComponentAlignment(Alignment.BASELINE, countryNameTF, getCountry);

		Button getCurrency = new Button("Get Currency", new Icon(VaadinIcon.SEARCH), event -> {
			Dialog dg = new Dialog();
			CurrencyView cv = new CurrencyView(ui, dg, currencyrepo);
			dg.add(cv);
			dg.open();
		});

		HorizontalLayout ccyhl = new HorizontalLayout();
		ccyhl.add(currencyTF, getCurrency);
		ccyhl.setVerticalComponentAlignment(Alignment.BASELINE, currencyTF, getCurrency);

		Button getBankGroupParam = new Button("Get Bank Group", new Icon(VaadinIcon.SEARCH), event -> {
			Dialog dg = new Dialog();
			ParametersView cv = new ParametersView(ui, dg, parametersrepo);
			dg.add(cv);
			dg.open();
		});

		HorizontalLayout bghl = new HorizontalLayout();
		bghl.add(bankGroupTF, getBankGroupParam);
		bghl.setVerticalComponentAlignment(Alignment.BASELINE, bankGroupTF, getBankGroupParam);

		vl.add(bghl, codeTF, nameTF, ctryhl, ccyhl, hOBranchNameTF, defaultSwiftAddressTF);
	}

	private TextField bankGroupTF, codeTF, nameTF, currencyTF, countryNameTF, hOBranchNameTF, defaultSwiftAddressTF;

	private Binder<Bank> binder = new Binder<>(Bank.class);

	public void initializeComponents() {

		bankGroupTF = new TextField();
		bankGroupTF.setMaxLength(10);
		bankGroupTF.setRequired(true);
		bankGroupTF.setRequiredIndicatorVisible(true);

		codeTF = new TextField();
		codeTF.setMaxLength(10);
		codeTF.setRequired(true);
		codeTF.setRequiredIndicatorVisible(true);

		nameTF = new TextField();
		nameTF.setMaxLength(35);
		nameTF.setRequired(true);
		nameTF.setRequiredIndicatorVisible(true);

		countryNameTF = new TextField();
		countryNameTF.setMaxLength(50);
		countryNameTF.setWidth("350px");
		countryNameTF.setRequired(true);
		countryNameTF.setRequiredIndicatorVisible(true);

		currencyTF = new TextField();
		currencyTF.setMaxLength(50);
		currencyTF.setWidth("350px");
		currencyTF.setRequired(true);
		currencyTF.setRequiredIndicatorVisible(true);

		hOBranchNameTF = new TextField();
		hOBranchNameTF.setMaxLength(50);
		hOBranchNameTF.setWidth("350px");

		defaultSwiftAddressTF = new TextField();
		defaultSwiftAddressTF.setMaxLength(11);
		defaultSwiftAddressTF.setWidth("350px");
		defaultSwiftAddressTF.setRequired(true);
		defaultSwiftAddressTF.setRequiredIndicatorVisible(true);

	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {

		if (entity != null) {
			if (null != entity.getCountryId()) {
				Country country = countryrepo.findById(entity.getCountryId()).get();
				if (null != country) {
					entity.setCountry(country);
					entity.setCountryName(country.getCountryName());
					entity.setCountryId(country.getId());
				}
			}

			if (null != entity.getLocalCurrencyId()) {
				Currency localCurrency = currencyrepo.findById(entity.getLocalCurrencyId()).get();
				if (null != localCurrency) {
					entity.setLocalCurrency(localCurrency);
					entity.setLocalCurrencyName(localCurrency.getCurrency());
				}
			}

			if (null != entity.getHoBranchId()) {
				hOBranch = entity.getHoBranch();
			}

			if (null != entity.getBankGroupParamId()) {
				Parameters param = parametersrepo.findById(entity.getBankGroupParamId()).get();
				if (null != param) {
					entity.setBankGroupParam(param);
					entity.setBankGroupParamValue(param.getParamValue());
				}
			}

			binder.setBean(entity);
			bankGroupTF.setLabel(UIActionUtil.getFieldLabel(entity, "bankGroupParamValue"));
			codeTF.setLabel(UIActionUtil.getFieldLabel(entity, "code"));
			nameTF.setLabel(UIActionUtil.getFieldLabel(entity, "name"));
			countryNameTF.setLabel(UIActionUtil.getFieldLabel(entity, "countryName"));
			currencyTF.setLabel(UIActionUtil.getFieldLabel(entity, "localCurrencyName"));
			hOBranchNameTF.setLabel(UIActionUtil.getFieldLabel(entity, "hoBranchName"));
			defaultSwiftAddressTF.setLabel(UIActionUtil.getFieldLabel(entity, "defaultSwiftAddress"));

			binder.forField(bankGroupTF).asRequired().bind("bankGroupParamValue");
			binder.forField(codeTF).asRequired().bind("code");
			binder.forField(nameTF).asRequired().bind("name");
			binder.forField(countryNameTF).asRequired().bind("countryName");
			binder.forField(currencyTF).asRequired().bind("localCurrencyName");
			binder.forField(hOBranchNameTF).bind("hoBranchName");
			binder.forField(defaultSwiftAddressTF).asRequired().bind("defaultSwiftAddress");

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

	/**
	 * @return the localCurrency
	 */
	public Currency getLocalCurrency() {
		return localCurrency;
	}

	/**
	 * @return the hOBranch
	 */
	public Branch gethOBranch() {
		return hOBranch;
	}

	/**
	 * @return the bankGroupParam
	 */
	public Parameters getBankGroupParam() {
		return bankGroupParam;
	}

	/**
	 * @param localCurrency the localCurrency to set
	 */
	public void setLocalCurrency(Currency localCurrency) {
		this.localCurrency = localCurrency;
	}

	/**
	 * @param hOBranch the hOBranch to set
	 */
	public void sethOBranch(Branch hOBranch) {
		this.hOBranch = hOBranch;
	}

	/**
	 * @param bankGroupParam the bankGroupParam to set
	 */
	public void setBankGroupParam(Parameters bankGroupParam) {
		this.bankGroupParam = bankGroupParam;
	}

	@Override
	public void setSelectedEntity(AbstractPojo pojo) {
		if (pojo instanceof Country) {
			((BankCrudView) ui).setCountry((Country) pojo);
			countryNameTF.setValue(getCountry().getCountryName());
			countryNameTF.setReadOnly(true);
			binder.getBean().setCountryName(getCountry().getCountryName());
			binder.getBean().setCountryId(getCountry().getId());
			binder.getBean().setCountry(getCountry());
		}
		if (pojo instanceof Currency) {
			((BankCrudView) ui).setLocalCurrency((Currency) pojo);
			currencyTF.setValue(getLocalCurrency().getCurrency());
			currencyTF.setReadOnly(true);
			entity.setLocalCurrency(getLocalCurrency());
			binder.getBean().setLocalCurrencyName(getLocalCurrency().getCurrency());
			binder.getBean().setLocalCurrencyId(getLocalCurrency().getId());
			binder.getBean().setLocalCurrency(getLocalCurrency());
		}
		if (pojo instanceof Parameters) {
			((BankCrudView) ui).setBankGroupParam((Parameters) pojo);
			bankGroupTF.setValue(getBankGroupParam().getParamValue());
			bankGroupTF.setReadOnly(true);
			entity.setBankGroupParam(getBankGroupParam());
			binder.getBean().setBankGroupParamValue(getBankGroupParam().getParamValue());
			binder.getBean().setBankGroupParamId(getBankGroupParam().getId());
			binder.getBean().setBankGroupParam(getBankGroupParam());
		}

	}

	@Override
	public void clearSelectedEntity(Bank entity) {
		entity.setCountry(null);
		entity.setLocalCurrency(null);
		entity.setBankGroupParam(null);

	}

	@Override
	public boolean fullsize() {
		return false;
	}

	@Override
	public String getPageTitle() {
		return "Bank";
	}

	@Override
	public String getPageMode() {
		return pageMode;
	}

	@Override
	public Binder<Bank> getBinder() {
		return binder;
	}

	@Override
	public Bank getEntity() {
		return entity;
	}

	@Override
	public void setParameter(BeforeEvent beforeEvent, String parameter) {
		if (parameter != null && !parameter.isEmpty()) {
			String params[] = parameter.split(",");
			pageMode = params[0];
			setPageMode(pageMode);
			if ("ADD".equals(pageMode))
				entity = new Bank();
			else {
				Long id = Long.valueOf(params[1]);
				if (id == -1L)
					beforeEvent.rerouteTo(getCloseNavigationClass());
				else
					entity = repo.findById(id).orElse(null);
			}
		} else
			getUI().get().navigate(getCloseNavigationClass());
	}

	@Override
	public Class<BankView> getCloseNavigationClass() {
		return BankView.class;
	}

	@Override
	public void saveRecord(Bank entity) {
		repo.save(entity);
	}
}
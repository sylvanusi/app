package com.more.app.base.ui.setup;

import org.springframework.beans.factory.annotation.Autowired;

import com.more.app.base.ui.BaseCrudComponent;
import com.more.app.base.ui.CrudPageView;
import com.more.app.entity.AbstractPojo;
import com.more.app.entity.Country;
import com.more.app.entity.Currency;
import com.more.app.repository.CountryRepository;
import com.more.app.repository.CurrencyRepository;
import com.more.app.util.annotations.UIActionUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = CurrencyCrudView.pageUrlString, layout = CrudPageView.class)
public class CurrencyCrudView extends BaseCrudComponent<Currency> implements HasUrlParameter<String> {
	private static final long serialVersionUID = -5511035586185573430L;
	public static final String pageUrlString = "currency";

	@Autowired
	private CurrencyRepository repo;

	@Autowired
	private CountryRepository countryrepo;

	private Country country;

	public CurrencyCrudView() {
		super();
		Button getCountry = new Button("Get Country", new Icon(VaadinIcon.FLAG_CHECKERED), event -> {
			Dialog dg = new Dialog();
			CountryView cv = new CountryView(ui, dg, countryrepo);
			dg.add(cv);
			dg.open();
		});

		HorizontalLayout ctryhl = new HorizontalLayout();
		ctryhl.add(countryNameTF, getCountry);
		ctryhl.setVerticalComponentAlignment(Alignment.BASELINE, countryNameTF, getCountry);

		vl.add(codeTF, currencyTF, ctryhl, numericCodeTF, minorUnitTF);
	}

	private TextField codeTF, currencyTF, countryNameTF;
	private IntegerField numericCodeTF, minorUnitTF;
	private Binder<Currency> binder = new Binder<>(Currency.class);

	@Override
	public void initializeComponents() {
		codeTF = new TextField();
		codeTF.setMaxLength(3);
		codeTF.setRequired(true);
		codeTF.setRequiredIndicatorVisible(true);

		currencyTF = new TextField();
		currencyTF.setMaxLength(50);
		currencyTF.setWidth("350px");
		currencyTF.setRequired(true);
		currencyTF.setRequiredIndicatorVisible(true);

		countryNameTF = new TextField();
		countryNameTF.setMaxLength(50);
		countryNameTF.setWidth("350px");
		countryNameTF.setRequired(true);
		countryNameTF.setRequiredIndicatorVisible(true);

		numericCodeTF = new IntegerField();
		numericCodeTF.setMax(999);

		minorUnitTF = new IntegerField();
		minorUnitTF.setMax(99);
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

			binder.setBean(entity);
			codeTF.setLabel(UIActionUtil.getFieldLabel(entity, "code"));
			currencyTF.setLabel(UIActionUtil.getFieldLabel(entity, "currency"));
			countryNameTF.setLabel(UIActionUtil.getFieldLabel(entity, "countryName"));
			numericCodeTF.setLabel(UIActionUtil.getFieldLabel(entity, "numericCode"));
			minorUnitTF.setLabel(UIActionUtil.getFieldLabel(entity, "minorUnit"));

			binder.forField(codeTF).asRequired("").bind("code");
			binder.forField(currencyTF).asRequired("").bind("currency");
			binder.forField(countryNameTF).asRequired("").bind("countryName");
			binder.forField(numericCodeTF).bind("numericCode");
			binder.forField(minorUnitTF).bind("minorUnit");
		}
		if (getPageMode().equals("ADD") || getPageMode().equals("ADDNEW") || getPageMode().equals("EDIT")) {
			confirmButton.setVisible(true);
			addewButton.setVisible(true);
		}
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public void setSelectedEntity(AbstractPojo pojo) {
		if (pojo instanceof Country) {
			((CurrencyCrudView) ui).setCountry((Country) pojo);
			countryNameTF.setValue(getCountry().getCountryName());
			countryNameTF.setReadOnly(true);
			binder.getBean().setCountryName(getCountry().getCountryName());
			binder.getBean().setCountryId(getCountry().getId());
			binder.getBean().setCountry(getCountry());
		}
	}

	@Override
	public void clearSelectedEntity(Currency pojo) {
		pojo.setCountryName(null);
		pojo.setCountry(null);
	}

	@Override
	public boolean fullsize() {
		return false;
	}

	@Override
	public String getPageTitle() {
		return "Currency";
	}

	@Override
	public String getPageMode() {
		return pageMode;
	}

	@Override
	public Binder<Currency> getBinder() {
		return binder;
	}

	@Override
	public Currency getEntity() {
		return entity;
	}

	@Override
	public void setParameter(BeforeEvent beforeEvent, String parameter) {

		System.out.println("CALLED B");
		if (parameter != null && !parameter.isEmpty()) {
			String params[] = parameter.split(",");
			pageMode = params[0];
			setPageMode(pageMode);
			if ("ADD".equals(pageMode))
				entity = new Currency();
			else {
				Long id = Long.valueOf(params[1]);
				if (id == -1L)
					beforeEvent.rerouteTo(getCloseNavigationClass());
				else
					entity = repo.findById(id).orElse(null);
			}
		} else
			getUI().get().navigate(CurrencyView.class);
	}

	@Override
	public Class<CurrencyView> getCloseNavigationClass() {
		return CurrencyView.class;
	}

	@Override
	public void saveRecord(Currency entity) {
		repo.save(entity);
	}
}
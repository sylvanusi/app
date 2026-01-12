package com.more.app.base.ui.configuration;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.more.app.base.ui.BaseCrudComponent;
import com.more.app.base.ui.CrudPageView;
import com.more.app.base.ui.setup.AccountTypeView;
import com.more.app.base.ui.setup.BankView;
import com.more.app.base.ui.setup.CurrencyView;
import com.more.app.entity.AbstractPojo;
import com.more.app.entity.AccountType;
import com.more.app.entity.Bank;
import com.more.app.entity.ChargeDefination;
import com.more.app.entity.Currency;
import com.more.app.repository.AccountTypeRepository;
import com.more.app.repository.BankRepository;
import com.more.app.repository.CurrencyRepository;
import com.more.app.repository.productsetup.ChargeDefinationRepository;
import com.more.app.util.annotations.UIActionUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

import ch.carnet.kasparscherrer.EmptyFormLayoutItem;

@Route(value = ChargeDefinationCrudView.pageUrlString, layout = CrudPageView.class)
public class ChargeDefinationCrudView extends BaseCrudComponent<ChargeDefination> implements HasUrlParameter<String>
{
	private static final long serialVersionUID = -5788207678525425281L;
	public static final String pageUrlString = "chargedef-view";
	private Binder<ChargeDefination> binder = new Binder<>(ChargeDefination.class);
	private Bank bank;
	private AccountType accountType;

	@Autowired
	private ChargeDefinationRepository repository;
	@Autowired
	private BankRepository bankRepository;
	@Autowired
	private AccountTypeRepository accTypeRepository;
	@Autowired
	private CurrencyRepository ccyRepository;
	
	@SuppressWarnings("unchecked")
	public ChargeDefinationCrudView()
	{
		super();

		Button getBank = new Button("", new Icon(VaadinIcon.SEARCH), event ->
		{
			Dialog dg = new Dialog();
			BankView v = new BankView(ui, dg, bankRepository);
			dg.add(v);
			dg.open();
		});

		HorizontalLayout bankhl = new HorizontalLayout();
		bankhl.add(bankNameTF, getBank);
		bankhl.setVerticalComponentAlignment(Alignment.BASELINE, bankNameTF, getBank);
		bankhl.setSizeFull();

		Button getAccountType = new Button("", new Icon(VaadinIcon.SEARCH), event ->
		{
			Dialog dg = new Dialog();
			AccountTypeView v = new AccountTypeView(ui, dg, true, accTypeRepository);
			dg.add(v);
			dg.open();
		});

		searchCcy = new Button("", new Icon(VaadinIcon.SEARCH), event ->
		{
			Dialog dg = new Dialog();
			CurrencyView v = new CurrencyView(ui, dg, ccyRepository);
			dg.add(v);
			dg.open();
		});
		searchCcy.setEnabled(false);
		HorizontalLayout acctTypehl = new HorizontalLayout();
		acctTypehl.add(accountTypeCodeTF, accountTypeDescTF, getAccountType);
		acctTypehl.setVerticalComponentAlignment(Alignment.BASELINE, accountTypeCodeTF, accountTypeDescTF,
				getAccountType);

		HorizontalLayout chargeDtlHl = new HorizontalLayout();
		chargeDtlHl.add(chargeCodeTF, chargeNameTF);
		chargeDtlHl.setVerticalComponentAlignment(Alignment.BASELINE, chargeCodeTF, chargeNameTF);

		HorizontalLayout caHL = new HorizontalLayout();
		caHL.add(chargeCcySF, searchCcy, chargeAmountBF);
		caHL.setVerticalComponentAlignment(Alignment.BASELINE, chargeCcySF, searchCcy, chargeAmountBF);

		HorizontalLayout mimaxHL = new HorizontalLayout();
		mimaxHL.add(minAmountBF, maxAmountBF);
		mimaxHL.setVerticalComponentAlignment(Alignment.BASELINE, minAmountBF, maxAmountBF);

		HorizontalLayout chinHl = new HorizontalLayout();
		chinHl.add(chargePercentBF, interestRateBF);
		chinHl.setVerticalComponentAlignment(Alignment.BASELINE, chargePercentBF, interestRateBF);

		formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));

		HorizontalLayout hl = new HorizontalLayout(tieredCB, appliesToProductCB, taxableCB);

		formLayout.add(bankhl, acctTypehl, chargeDtlHl, descriptionTA, chargeTypeRB, chargeFrequencySF,
				calculationMethodRB, new EmptyFormLayoutItem(), chargeInRB, new EmptyFormLayoutItem(), hl,
				new EmptyFormLayoutItem(), caHL, new EmptyFormLayoutItem(), chinHl, mimaxHL, tierComponent,
				new EmptyFormLayoutItem(), overallMinAmountBF, overallMaxAmountBF);
		vl.add(formLayout);

		calculationMethodRB.addValueChangeListener(event ->
		{
			if (event.getValue() != null)
			{
				doCalculationType(true);
			}
		});

		tieredCB.addValueChangeListener(event ->
		{
			if (null != event.getValue())
				doCalculationType(true);
		});

		chargeTypeRB.addValueChangeListener(event ->
		{
			if (null != event.getValue())
				doChargeType();
		});

		chargeInRB.addValueChangeListener(event ->
		{
			doChargeIn(event.getValue());
		});

		doCalculationType(false);
		doChargeType();
	}

	@Override
	public void initializeComponents()
	{
		bankNameTF = new TextField();
		bankNameTF.setMaxLength(35);
		bankNameTF.setWidthFull();
		// bankNameTF.setMinWidth("300px");
		bankNameTF.setRequired(true);
		bankNameTF.setRequiredIndicatorVisible(true);
		bankNameTF.setReadOnly(true);

		accountTypeCodeTF = new TextField();
		accountTypeCodeTF.setMaxLength(2);
		accountTypeCodeTF.setWidthFull();
		// accountTypeCodeTF.setMaxWidth("100px");
		accountTypeCodeTF.setRequired(true);
		accountTypeCodeTF.setRequiredIndicatorVisible(true);
		accountTypeCodeTF.setReadOnly(true);

		accountTypeDescTF = new TextField();
		// accountTypeDescTF.setLabel(UIActionUtil.getFieldLabel(entity,
		// "accountTypeDesc"));
		accountTypeDescTF.setMaxLength(35);
		accountTypeDescTF.setWidthFull();
		// accountTypeDescTF.setMaxWidth("250px");
		accountTypeDescTF.setRequired(true);
		accountTypeDescTF.setRequiredIndicatorVisible(true);
		accountTypeDescTF.setReadOnly(true);

		chargeCodeTF = new TextField();
		chargeCodeTF.setMaxLength(10);
		chargeCodeTF.setWidthFull();
		// chargeCodeTF.setMaxWidth("110px");
		chargeCodeTF.setRequired(true);
		chargeCodeTF.setRequiredIndicatorVisible(true);

		chargeNameTF = new TextField();
		chargeNameTF.setMaxLength(35);
		chargeNameTF.setWidthFull();
		// chargeNameTF.setMaxWidth("400px");
		chargeNameTF.setRequired(true);
		chargeNameTF.setRequiredIndicatorVisible(true);

		descriptionTA = new TextArea();
		descriptionTA.setMaxLength(70);
		descriptionTA.setWidthFull();
		// descriptionTA.setMaxWidth("350px");
		descriptionTA.setRequired(true);
		descriptionTA.setRequiredIndicatorVisible(true);

		chargeTypeRB = new RadioButtonGroup<>();
		chargeTypeRB.setItems("Standard", "Tax");

		calculationMethodRB = new RadioButtonGroup<>();
		calculationMethodRB.setItems("Amount", "Percent");

		chargeInRB = new RadioButtonGroup<>();
		chargeInRB.setItems("Local Currency", "Product Currency", "Schedule Currency");
		chargeInRB.setValue("Local Currency");

		// OneOff, daily, weekly monthly biannual annual
		chargeFrequencySF = new Select();
		chargeFrequencySF.setItems("", "One Off", "Daily", "Weekly", "Monthly", "Annual");
		chargeFrequencySF.setRequiredIndicatorVisible(true);
		chargeFrequencySF.setMaxWidth("150px");

		chargeCcySF = new TextField();
		chargeCcySF.setReadOnly(true);
		chargeCcySF.setEnabled(false);

		chargeAmountBF = new BigDecimalField();
		chargeAmountBF.setWidthFull();

		minAmountBF = new BigDecimalField();
		minAmountBF.setWidthFull();

		maxAmountBF = new BigDecimalField();
		maxAmountBF.setWidthFull();

		chargePercentBF = new NumberField();
		chargePercentBF.setWidthFull();

		interestRateBF = new NumberField();
		interestRateBF.setWidthFull();
		interestRateBF.setWidthFull();
		interestRateBF.setVisible(false);

		tieredCB = new Checkbox();

		taxableCB = new Checkbox();

		appliesToProductCB = new Checkbox();

		tierComponent = new ChargeDefinationTierComponent();
		tierComponent.setWidthFull();

		overallMinAmountBF = new BigDecimalField();
		overallMinAmountBF.setWidthFull();

		overallMaxAmountBF = new BigDecimalField();
		overallMaxAmountBF.setWidthFull();
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
		if (entity != null)
		{
			binder.setBean(entity);
			bankNameTF.setLabel(UIActionUtil.getFieldLabel(entity, "bankName"));
			accountTypeCodeTF.setLabel(UIActionUtil.getFieldLabel(entity, "accountTypeCode"));
			chargeCodeTF.setLabel(UIActionUtil.getFieldLabel(entity, "chargeCode"));
			chargeNameTF.setLabel(UIActionUtil.getFieldLabel(entity, "chargeName"));
			descriptionTA.setLabel(UIActionUtil.getFieldLabel(entity, "description"));
			chargeTypeRB.setLabel(UIActionUtil.getFieldLabel(entity, "chargeType"));
			calculationMethodRB.setLabel(UIActionUtil.getFieldLabel(entity, "calculationMethod"));
			chargeFrequencySF.setLabel(UIActionUtil.getFieldLabel(entity, "chargeFrequency"));
			chargeCcySF.setLabel(UIActionUtil.getFieldLabel(entity, "chargeCcy"));
			chargeAmountBF.setLabel(UIActionUtil.getFieldLabel(entity, "amountToCharge"));
			minAmountBF.setLabel(UIActionUtil.getFieldLabel(entity, "minAmount"));
			maxAmountBF.setLabel(UIActionUtil.getFieldLabel(entity, "maxAmount"));
			chargePercentBF.setLabel(UIActionUtil.getFieldLabel(entity, "chargePercent"));
			interestRateBF.setLabel(UIActionUtil.getFieldLabel(entity, "interestRate"));
			tieredCB.setLabel(UIActionUtil.getFieldLabel(entity, "tiered"));
			taxableCB.setLabel(UIActionUtil.getFieldLabel(entity, "taxable"));
			appliesToProductCB.setLabel(UIActionUtil.getFieldLabel(entity, "appliesToProduct"));
			overallMinAmountBF.setLabel(UIActionUtil.getFieldLabel(entity, "overallMinAmount"));
			overallMaxAmountBF.setLabel(UIActionUtil.getFieldLabel(entity, "overallMaxAmount"));
			chargeInRB.setLabel(UIActionUtil.getFieldLabel(entity, "chargeIn"));

			binder.forField(bankNameTF).asRequired().bind("bankName");
			binder.forField(accountTypeCodeTF).asRequired().bind("accountTypeCode");
			binder.forField(accountTypeDescTF).asRequired().bind("accountTypeDesc");
			binder.forField(chargeCodeTF).asRequired().bind("chargeCode");
			binder.forField(chargeNameTF).asRequired().bind("chargeName");
			binder.forField(descriptionTA).asRequired().bind("description");
			binder.forField(chargeTypeRB).asRequired().bind("chargeType");
			binder.forField(calculationMethodRB).asRequired().bind("calculationMethod");
			binder.forField(chargeInRB).asRequired().bind("chargeIn");
			binder.forField(chargeFrequencySF).asRequired().bind("chargeFrequency");
			binder.forField(chargeCcySF).withValidator(val ->
			{
				System.out.println(chargeInRB.getValue());
				System.out.println(chargeInRB.getValue());
				System.out.println(chargeInRB.getValue());
				System.out.println(chargeInRB.getValue());
				if ("Schedule Currency".equals(chargeInRB.getValue()))
					return null != val && val.trim().length() >0;
				return true;
			}, "Schedule Currency is Required").bind("chargeCcy");

			binder.forField(chargeAmountBF).withValidator(val ->
			{
				if (Boolean.TRUE.equals(tieredCB.getValue()))
					return true;
				else
				{
					if (!"Amount".equals(calculationMethodRB.getValue()))
						return true;
					else
						return null != val && val.compareTo(new BigDecimal(0)) > 0;
				}

			}, "Enter Amount greater than 0").bind("amountToCharge");
			binder.forField(minAmountBF).withValidator(val ->
			{
				if (val == null || maxAmountBF.getValue() == null)
					return true;
				else
				{
					if (val.compareTo(maxAmountBF.getValue()) > 0)
						return false;
					else
						return true;
				}
			}, "Cannot be greater than Maximum Amount").bind("minAmount");

			binder.forField(maxAmountBF).withValidator(val ->
			{
				if (val == null || minAmountBF.getValue() == null)
					return true;
				else
				{
					if (val.compareTo(minAmountBF.getValue()) <= 0)
						return false;
					else
						return true;
				}
			}, "Cannot be less than Minimum Amount").bind("maxAmount");
			binder.forField(chargePercentBF).bind("chargePercent");
			binder.forField(interestRateBF).bind("interestRate");
			binder.forField(tieredCB).bind("tiered");
			binder.forField(taxableCB).bind("taxable");
			binder.forField(appliesToProductCB).bind("appliesToProduct");
			binder.forField(overallMinAmountBF).withValidator(val ->
			{
				if (Boolean.FALSE.equals(tieredCB.getValue()))
					return true;
				else
				{
					if (null == val || null == overallMaxAmountBF.getValue())
						return true;
					if (val.compareTo(overallMaxAmountBF.getValue()) <= 0)
						return true;
					else
						return false;
				}
			}, "Cannot be greater than Overall Max Amount").bind("overallMinAmount");
			binder.forField(overallMaxAmountBF).withValidator(val ->
			{
				if (Boolean.FALSE.equals(tieredCB.getValue()))
					return true;
				else
				{
					if (null == val || null == overallMinAmountBF.getValue())
						return true;
					if (val.compareTo(overallMinAmountBF.getValue()) < 0)
						return false;
					else
						return true;
				}
			}, "Cannot be greater than Overall Max Amount").bind("overallMaxAmount");
			tierComponent.setChargeDefinationTierComponent(entity);
			doChargeIn(entity.getChargeIn());
		}
		if (getPageMode().equals("ADD") || getPageMode().equals("ADDNEW") || getPageMode().equals("EDIT"))
		{
			confirmButton.setVisible(true);
			addewButton.setVisible(true);
		}
	}

	private TextField bankNameTF, accountTypeCodeTF, accountTypeDescTF, chargeNameTF, chargeCodeTF, chargeCcySF;
	private TextArea descriptionTA;
	private Select<String> chargeFrequencySF;
	private BigDecimalField minAmountBF, maxAmountBF, chargeAmountBF, overallMinAmountBF, overallMaxAmountBF;
	private NumberField chargePercentBF, interestRateBF;
	private RadioButtonGroup<String> chargeTypeRB, calculationMethodRB, chargeInRB;
	private Checkbox tieredCB, appliesToProductCB, taxableCB;
	private ChargeDefinationTierComponent tierComponent;
	private Button searchCcy;

	private FormLayout formLayout = new FormLayout();

	@Override
	public void setSelectedEntity(AbstractPojo pojo)
	{
		if (pojo instanceof Bank)
		{
			((ChargeDefinationCrudView) ui).setBank((Bank) pojo);
			bankNameTF.setValue(getBank().getName());
			bankNameTF.setReadOnly(true);
			entity.setBank(getBank());
		}
		if (pojo instanceof AccountType)
		{
			((ChargeDefinationCrudView) ui).setAccountType((AccountType) pojo);
			accountTypeCodeTF.setValue(getAccountType().getCode());
			accountTypeCodeTF.setReadOnly(true);
			accountTypeDescTF.setValue(getAccountType().getDescription());
			accountTypeDescTF.setReadOnly(true);
			entity.setAccountType(getAccountType());
		}
		if (pojo instanceof Currency)
		{
			chargeCcySF.setValue(((Currency) pojo).getCode());
			chargeCcySF.setReadOnly(true);
		}
	}

	/**
	 * @return the bank
	 */
	public Bank getBank()
	{
		return bank;
	}

	/**
	 * @param bank the bank to set
	 */
	public void setBank(Bank bank)
	{
		this.bank = bank;
	}

	@Override
	public void clearSelectedEntity(ChargeDefination entity)
	{
		entity.setBank(null);
	}

	@Override
	public boolean fullsize()
	{
		return true;
	}

	@Override
	public String getPageTitle()
	{
		return "Charge Defination";
	}

	/**
	 * @return the accountType
	 */
	public AccountType getAccountType()
	{
		return accountType;
	}

	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(AccountType accountType)
	{
		this.accountType = accountType;
	}

	private void doCalculationType(boolean fromevent)
	{
		String calculationType = "";
		if (null != calculationMethodRB.getValue())
			calculationType = (String) calculationMethodRB.getValue();
		Boolean isTier = tieredCB.getValue();

		if (!isTier)
		{
			if (calculationType.equals("Amount"))
			{
				chargeAmountBF.setEnabled(true);
				minAmountBF.clear();
				maxAmountBF.clear();
				maxAmountBF.setEnabled(false);
				minAmountBF.setEnabled(false);
				chargePercentBF.setEnabled(false);
				interestRateBF.setEnabled(false);
			} else if (calculationType.equals("Percent"))
			{
				chargePercentBF.setEnabled(true);
				interestRateBF.setEnabled(false);
				chargeAmountBF.setEnabled(false);
				chargeAmountBF.clear();
				maxAmountBF.setEnabled(true);
				minAmountBF.setEnabled(true);
			} else if (calculationType.equals("Interest"))
			{
				chargePercentBF.setEnabled(false);
				interestRateBF.setEnabled(true);
				chargeAmountBF.setEnabled(false);
				minAmountBF.clear();
				maxAmountBF.clear();
				chargeAmountBF.clear();
				maxAmountBF.setEnabled(false);
				minAmountBF.setEnabled(false);
			} else
			{
				chargePercentBF.setEnabled(false);
				interestRateBF.setEnabled(false);
				chargeAmountBF.setEnabled(false);
				maxAmountBF.setEnabled(false);
				minAmountBF.setEnabled(false);
				minAmountBF.clear();
				maxAmountBF.clear();
				chargeAmountBF.clear();
			}
			tierComponent.doClearTierFields();
			overallMinAmountBF.clear();
			overallMaxAmountBF.clear();
			overallMinAmountBF.setEnabled(false);
			overallMaxAmountBF.setEnabled(false);
		} else
		{
			chargePercentBF.setEnabled(false);
			interestRateBF.setEnabled(false);
			chargeAmountBF.setEnabled(false);
			chargePercentBF.clear();
			chargeAmountBF.clear();
			minAmountBF.clear();
			maxAmountBF.clear();
			maxAmountBF.setEnabled(false);
			minAmountBF.setEnabled(false);
			if (fromevent)
			{
				getEntity().setCalculationMethod(calculationType);
				tierComponent.doCalculationTypeAction(getEntity());
			}
			overallMinAmountBF.setEnabled(true);
			overallMaxAmountBF.setEnabled(true);
		}
	}

	private void doChargeType()
	{
		String chargeType = "";
		if (null != chargeTypeRB.getValue())
			chargeType = (String) chargeTypeRB.getValue();
		if (chargeType == "Standard")
		{
			taxableCB.setValue(false);
			taxableCB.setEnabled(true);
		} else if (chargeType == "Tax")
		{
			taxableCB.setValue(false);
			taxableCB.setEnabled(false);
		} else
		{
		}
	}

	private void doChargeIn(String value)
	{
		if (null == value || value.equals("Local Currency") || value.equals("Product Currency"))
		{
			searchCcy.setEnabled(false);
			chargeCcySF.clear();
			chargeCcySF.setEnabled(false);
		} else
		{
			chargeCcySF.setEnabled(true);
			searchCcy.setEnabled(true);
		}
		binder.validate();
	}

	public void setEventEntityFields(ChargeDefination entity)
	{
		String calculationType = entity.getCalculationMethod();
		if (calculationType == "Amount")
		{
			entity.setChargePercent(null);
			entity.setInterestRate(null);
		} else if (calculationType == "Percent")
		{
			entity.setInterestRate(null);
			entity.setAmountToCharge(null);
		} else if (calculationType == "Interest")
		{
			entity.setChargePercent(null);
			entity.setAmountToCharge(null);
		}
	}

	@Override
	public String getPageMode()
	{
		return pageMode;
	}

	@Override
	public Binder<ChargeDefination> getBinder()
	{
		return binder;
	}

	@Override
	public ChargeDefination getEntity()
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
				entity = new ChargeDefination();
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
	public Class<ChargeDefinationView> getCloseNavigationClass()
	{
		return ChargeDefinationView.class;
	}

	@Override
	public void saveRecord(ChargeDefination entity) {
		repository.save(entity);
	}
}

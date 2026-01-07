package com.more.app.entity.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.more.app.annotations.UIActionUtil;
import com.more.app.base.ui.BaseCrudComponent;
import com.more.app.base.ui.CrudPageView;
import com.more.app.entity.AbstractPojo;
import com.more.app.entity.Bank;
import com.more.app.entity.Branch;
import com.more.app.repository.BankRepository;
import com.more.app.repository.BranchRepository;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

import ch.carnet.kasparscherrer.EmptyFormLayoutItem;

@Route(value = BranchCrudView.pageUrlString, layout = CrudPageView.class)
public class BranchCrudView extends BaseCrudComponent<Branch>  implements HasUrlParameter<String>
{
	private static final long serialVersionUID = -5511035586185573430L;
	public static final String pageUrlString = "branch";
	private Bank bank;
	

	@Autowired
	private BranchRepository branchRepo;

	@Autowired
	private BankRepository bankRepo;
	
	public BranchCrudView()
	{
		super();

		Button getBank = new Button("Get Bank", new Icon(VaadinIcon.SEARCH), event ->
		{
			Dialog dg = new Dialog();
			BankView v = new BankView(ui, dg, bankRepo);
			dg.add(v);
			dg.open();
		});

		HorizontalLayout bankhl = new HorizontalLayout();
		bankhl.add(bankNameTF, getBank);
		bankhl.setVerticalComponentAlignment(Alignment.BASELINE, bankNameTF, getBank);
		
		
		formLayout.setMaxWidth("1000px");
		formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));

		formLayout.add(bankhl, branchNoTF, codeTF, nameTF,accountingNoTF,new EmptyFormLayoutItem(), addressTA,cityTF,emailTF,phoneNoTF, useDefaultSwiftCB, swiftAddressTF);
		vl.add(formLayout);
	}
	
	private TextField bankNameTF, codeTF, nameTF, swiftAddressTF,cityTF, accountingNoTF, emailTF, phoneNoTF;
	private IntegerField branchNoTF;
	private TextArea addressTA;
	private Checkbox useDefaultSwiftCB;
	private RadioButtonGroup<String> tradeStatusCB;
	private RadioButtonGroup<String> txnStatusCB;

	private FormLayout formLayout = new FormLayout();
	private Binder<Branch> binder = new Binder<>(Branch.class);

	public void initializeComponents()
	{
		bankNameTF = new TextField();
		bankNameTF.setMaxLength(35);
		bankNameTF.setMinWidth("300px");
		bankNameTF.setRequired(true);
		bankNameTF.setRequiredIndicatorVisible(true);

		codeTF = new TextField();
		codeTF.setMaxLength(10);
		codeTF.setRequired(true);
		codeTF.setRequiredIndicatorVisible(true);

		branchNoTF = new IntegerField();
		branchNoTF.setMax(10000);
		branchNoTF.setRequiredIndicatorVisible(true);

		nameTF = new TextField();
		nameTF.setMaxLength(35);
		nameTF.setMinWidth("300px");
		nameTF.setRequired(true);
		nameTF.setRequiredIndicatorVisible(true);
		
		accountingNoTF = new TextField();
		accountingNoTF.setMaxLength(4);

		addressTA = new TextArea();
		// addressTA.setMaxLength(35);
		addressTA.setMinWidth("200px");
		addressTA.setMinHeight("100px");
		addressTA.setRequired(true);
		addressTA.setRequiredIndicatorVisible(true);
		
		cityTF = new TextField();
		cityTF.setMaxLength(35);
		cityTF.setMinWidth("300px");
		
		phoneNoTF = new TextField();
		phoneNoTF.setMaxLength(15);
		phoneNoTF.setMinWidth("300px");
		
		emailTF = new TextField();
		emailTF.setMaxLength(35);
		emailTF.setMinWidth("300px");

		swiftAddressTF = new TextField();
		swiftAddressTF.setMaxLength(11);

		useDefaultSwiftCB = new Checkbox();

		tradeStatusCB = new RadioButtonGroup<>();
		tradeStatusCB.setItems("Authorised", "Unauthorised");
		// tradeStatusCB.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

		txnStatusCB = new RadioButtonGroup<>();
		txnStatusCB.setItems("Open", "Close");
		// tradeStatusCB.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
		if (entity != null)
		{
			
			if (null != entity.getBankId()) {
				Bank bank = bankRepo.findById(entity.getBankId()).get();
				if (null != bank) {
					entity.setBank(bank);
					entity.setBankName(bank.getName());
				}
			}
						
			binder.setBean(entity);
			bankNameTF.setLabel(UIActionUtil.getFieldLabel(entity, "bankName"));
			codeTF.setLabel(UIActionUtil.getFieldLabel(entity, "code"));
			branchNoTF.setLabel(UIActionUtil.getFieldLabel(entity, "branchNo"));
			nameTF.setLabel(UIActionUtil.getFieldLabel(entity, "name"));
			accountingNoTF.setLabel(UIActionUtil.getFieldLabel(entity, "accountingNo"));
			addressTA.setLabel(UIActionUtil.getFieldLabel(entity, "address"));
			cityTF.setLabel(UIActionUtil.getFieldLabel(entity, "city"));
			phoneNoTF.setLabel(UIActionUtil.getFieldLabel(entity, "phoneNo"));
			emailTF.setLabel(UIActionUtil.getFieldLabel(entity, "email"));
			swiftAddressTF.setLabel(UIActionUtil.getFieldLabel(entity, "swiftAddress"));
			useDefaultSwiftCB.setLabel(UIActionUtil.getFieldLabel(entity, "useDefaultSwift"));
			tradeStatusCB.setLabel(UIActionUtil.getFieldLabel(entity, "tradeStatus"));
			txnStatusCB.setLabel(UIActionUtil.getFieldLabel(entity, "txnStatus"));

			binder.forField(bankNameTF).asRequired().bind("bankName");
			binder.forField(codeTF).asRequired().bind("code");
			binder.forField(branchNoTF).asRequired().bind("branchNo");
			binder.forField(nameTF).asRequired().bind("name");
			binder.forField(accountingNoTF).bind("accountingNo");
			binder.forField(addressTA).asRequired().bind("address");
			binder.forField(cityTF).bind("city");
			binder.forField(phoneNoTF).bind("phoneNo");
			binder.forField(emailTF).bind("email");
			binder.forField(swiftAddressTF).bind("swiftAddress");
			binder.forField(useDefaultSwiftCB).bind("useDefaultSwift");
			binder.forField(tradeStatusCB).bind("tradeStatus");
			binder.forField(txnStatusCB).bind("txnStatus");
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
		if (pojo instanceof Bank)
		{
			((BranchCrudView) ui).setBank((Bank) pojo);
			bankNameTF.setValue(getBank().getName());
			bankNameTF.setReadOnly(true);
			entity.setBank(getBank());
			entity.setBankId(getBank().getId());
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
	public void clearSelectedEntity(Branch pojo)
	{
		entity.setBank(null);
	}
	
	@Override
	public boolean fullsize()
	{
		return false;
	}

	@Override
	public String getPageTitle()
	{
		return "Branch";
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
				entity = new Branch();
			else
			{
				Long id = Long.valueOf(params[1]);
				if (id == -1L)
					beforeEvent.rerouteTo(getCloseNavigationClass());
				else
					entity = branchRepo.findById(id).orElse(null);
			}
		} else
			getUI().get().navigate(getCloseNavigationClass());
	}

	@Override
	public Class<BranchView> getCloseNavigationClass()
	{
		return BranchView.class;
	}

	@Override
	public String getPageMode()
	{
		return pageMode;
	}

	@Override
	public Binder<Branch> getBinder()
	{
		return binder;
	}

	@Override
	public Branch getEntity()
	{
		return entity;
	}

	@Override
	public void saveRecord(Branch entity) {
		branchRepo.save(entity);		
	}
}

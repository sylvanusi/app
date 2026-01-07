package com.more.app.entity.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.more.app.NullCheckUtil;
import com.more.app.annotations.UIActionUtil;
import com.more.app.base.ui.BaseCrudComponent;
import com.more.app.base.ui.CrudPageView;
import com.more.app.entity.AbstractPojo;
import com.more.app.entity.SwiftBic;
import com.more.app.repository.SwiftBicRepository;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = SwiftBicCrudView.pageUrlString, layout = CrudPageView.class)
public class SwiftBicCrudView extends BaseCrudComponent<SwiftBic> implements HasUrlParameter<String>
{
	private static final long serialVersionUID = -7910699565210806753L;
	public static final String pageUrlString = "swiftbic";
	private Binder<SwiftBic> binder = new Binder<>(SwiftBic.class);
	@Autowired
	private SwiftBicRepository repository;

	public SwiftBicCrudView()
	{
		super();
		vl.add(bankNameTF, countryNameTF, bankCodeTF, countryCodeTF, locationCodeTF, branchCodeTF, swiftBicTF);
	}

	private TextField bankNameTF, countryNameTF, bankCodeTF, countryCodeTF, locationCodeTF, branchCodeTF, swiftBicTF;

	@Override
	public void setSelectedEntity(AbstractPojo pojo)
	{

	}

	@Override
	public void clearSelectedEntity(SwiftBic entity)
	{

	}

	@Override
	public void setEventEntityFields(SwiftBic entity)
	{
		if (NullCheckUtil.checkNullAndEmptyString(entity.getBankCode().trim())
				&& NullCheckUtil.checkNullAndEmptyString(entity.getCountryCode().trim())
				&& NullCheckUtil.checkNullAndEmptyString(entity.getLocationCode().trim())
				&& NullCheckUtil.checkNullAndEmptyString(entity.getBranchCode().trim()))
		{
			entity.setSwiftBic(entity.getBankCode().trim() + entity.getCountryCode().trim()
					+ entity.getLocationCode().trim() + entity.getBranchCode().trim());
		}

	}

	@Override
	public void initializeComponents()
	{
		bankNameTF = new TextField();
		bankNameTF.setMaxLength(50);
		bankNameTF.setWidth("350px");
		bankNameTF.setRequired(true);
		bankNameTF.setRequiredIndicatorVisible(true);

		countryNameTF = new TextField();
		countryNameTF.setMaxLength(35);
		countryNameTF.setWidth("350px");
		countryNameTF.setRequired(true);
		countryNameTF.setRequiredIndicatorVisible(true);

		bankCodeTF = new TextField();
		bankCodeTF.setMaxLength(4);
		bankCodeTF.setRequired(true);
		bankCodeTF.setRequiredIndicatorVisible(true);
		// bankCodeTF.setWidth("50px");

		countryCodeTF = new TextField();
		countryCodeTF.setMaxLength(2);
		countryCodeTF.setRequired(true);
		countryCodeTF.setRequiredIndicatorVisible(true);

		locationCodeTF = new TextField();
		locationCodeTF.setMaxLength(2);
		locationCodeTF.setRequired(true);
		locationCodeTF.setRequiredIndicatorVisible(true);

		branchCodeTF = new TextField();
		branchCodeTF.setMaxLength(3);
		branchCodeTF.setRequired(true);
		branchCodeTF.setRequiredIndicatorVisible(true);
		
		swiftBicTF = new TextField();
		swiftBicTF.setMaxLength(11);
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
		if (entity != null)
		{
			binder.setBean(entity);
			bankNameTF.setLabel(UIActionUtil.getFieldLabel(entity, "bankName"));
			countryNameTF.setLabel(UIActionUtil.getFieldLabel(entity, "countryName"));
			bankCodeTF.setLabel(UIActionUtil.getFieldLabel(entity, "bankCode"));
			countryCodeTF.setLabel(UIActionUtil.getFieldLabel(entity, "countryCode"));
			locationCodeTF.setLabel(UIActionUtil.getFieldLabel(entity, "locationCode"));
			branchCodeTF.setLabel(UIActionUtil.getFieldLabel(entity, "branchCode"));
			swiftBicTF.setLabel(UIActionUtil.getFieldLabel(entity, "swiftBic"));

			binder.forField(bankNameTF).asRequired().bind("bankName");
			binder.forField(countryNameTF).asRequired().bind("countryName");		
			binder.forField(bankCodeTF).asRequired().bind("bankCode");
			binder.forField(countryCodeTF).asRequired().bind("countryCode");
			binder.forField(locationCodeTF).asRequired().bind("locationCode");
			binder.forField(branchCodeTF).asRequired().bind("branchCode");
			binder.forField(swiftBicTF).bind("swiftBic");
		}
		if (getPageMode().equals("ADD") || getPageMode().equals("ADDNEW") || getPageMode().equals("EDIT"))
		{
			confirmButton.setVisible(true);
			addewButton.setVisible(true);
		}
	}

	@Override
	public boolean fullsize()
	{
		return false;
	}

	@Override
	public String getPageTitle()
	{
		return "Swift BIC";
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
				entity = new SwiftBic();
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
	public Class<SwiftBicView> getCloseNavigationClass()
	{
		return SwiftBicView.class;
	}

	@Override
	public String getPageMode()
	{
		return pageMode;
	}

	@Override
	public Binder<SwiftBic> getBinder()
	{
		return binder;
	}

	@Override
	public SwiftBic getEntity()
	{
		return entity;
	}

	@Override
	public void saveRecord(SwiftBic entity) {
		repository.save(entity);	
	}
}

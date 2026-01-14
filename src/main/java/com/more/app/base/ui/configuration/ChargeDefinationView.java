package com.more.app.base.ui.configuration;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.base.ui.BaseView;
import com.more.app.base.ui.DialogSelectEntity;
import com.more.app.base.ui.LeftAlignedLayout;
import com.more.app.entity.ChargeDefination;
import com.more.app.repository.CountryRepository;
import com.more.app.repository.productsetup.ChargeDefinationRepository;
import com.more.app.util.annotations.UIActionUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = ChargeDefinationView.pageUrlString, layout = LeftAlignedLayout.class)
public class ChargeDefinationView extends BaseView<ChargeDefination>
{
	private static final long serialVersionUID = 7665920589462231912L;
	public static final String pageUrlString = "chargedef";
	public static final Long resourceAdd = Long.valueOf(52L);
	public static final Long resourceEdit = Long.valueOf(53L);
	public static final Long resourceView = Long.valueOf(54L);
	
	private TextField chargeCodeTF;
	private TextField chargeNameTF;
	@Autowired
	private ChargeDefinationRepository repository;

	public ChargeDefinationView()
	{
		super();
		view = this;
	}

	public ChargeDefinationView(DialogSelectEntity dialog, Dialog dg)
	{
		super(dialog,dg);
	}
	
	public <T> ChargeDefinationView(DialogSelectEntity dialog, Dialog dg, JpaRepository repository)
	{
		super(dialog, dg, repository);
		this.repository = (ChargeDefinationRepository) repository;
	}

	@Override
	public void loadComponents()
	{
		chargeCodeTF = new TextField("Code");
		chargeCodeTF.setMaxLength(3);

		chargeNameTF = new TextField("Charge");
		chargeNameTF.setMaxLength(35);
		
		grid.addColumn(ChargeDefination::getChargeCode).setHeader(UIActionUtil.getFieldLabel(ChargeDefination.class, "chargeCode"));
		grid.addColumn(ChargeDefination::getChargeName).setHeader(UIActionUtil.getFieldLabel(ChargeDefination.class, "chargeName"));
		grid.addColumn(ChargeDefination::getAccountTypeCode).setHeader(UIActionUtil.getFieldLabel(ChargeDefination.class, "accountTypeCode"));
		grid.addColumn(ChargeDefination::getChargeType).setHeader(UIActionUtil.getFieldLabel(ChargeDefination.class, "chargeType"));
		grid.addColumn(ChargeDefination::getChargeCcy).setHeader(UIActionUtil.getFieldLabel(ChargeDefination.class, "chargeCcy"));
		grid.addColumn(ChargeDefination::getChargeFrequency).setHeader(UIActionUtil.getFieldLabel(ChargeDefination.class, "chargeFrequency"));
		
		hlsearch.add(chargeCodeTF, chargeNameTF, searchb);
		hlsearch.setVerticalComponentAlignment(Alignment.BASELINE, chargeCodeTF, chargeNameTF, searchb);

		addBaseComponentsandStyle();

		searchb.addClickListener(evebt ->
		{
			grid.setItems(new ArrayList<ChargeDefination>());
			String code = chargeCodeTF.getValue();
			String name = chargeNameTF.getValue();
			grid.setItems(repository.findByChargeCodeStartsWithAndChargeNameStartsWith(code, name));
		});
	}

	@Override
	public void reloadView()
	{
		grid.setItems(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
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
		getUI().ifPresent(ui -> ui.navigate(ChargeDefinationCrudView.class, param));
	}

	@Override
	public String getDialogTitle()
	{
		return "Search Charge Defination";
	}
}

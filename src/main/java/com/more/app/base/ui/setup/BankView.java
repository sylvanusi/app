package com.more.app.base.ui.setup;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.base.ui.BaseView;
import com.more.app.base.ui.DialogSelectEntity;
import com.more.app.base.ui.LeftAlignedLayout;
import com.more.app.entity.Bank;
import com.more.app.repository.BankRepository;
import com.more.app.repository.CurrencyRepository;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "bankView", layout = LeftAlignedLayout.class)
public class BankView extends BaseView<Bank>
{
	private static final long serialVersionUID = -4351157630319048831L;
	public static final String pageUrlString = "bank-view";
	public static final Long resourceAdd = Long.valueOf(19L);
	public static final Long resourceEdit = Long.valueOf(20L);
	public static final Long resourceView = Long.valueOf(21L);

	private TextField codeTF;
	private TextField bankNameTF;
	
	@Autowired
	private BankRepository bankRepository;

	public BankView()
	{
		super();
		view = this;
	}

	public BankView(DialogSelectEntity dialogSelectEntity, Dialog dg)
	{
		super(dialogSelectEntity, dg);
	}
	

	public <T> BankView(DialogSelectEntity dialog, Dialog dg, JpaRepository repository)
	{
		super(dialog, dg, repository);
		this.bankRepository = (BankRepository) repository;
	}


	public void loadComponents()
	{
		codeTF = new TextField("Code");
		codeTF.setMaxLength(2);

		bankNameTF = new TextField("Bank Name");
		bankNameTF.setMaxLength(35);

		grid.addColumn(Bank::getCode).setHeader("Code");
		grid.addColumn(Bank::getName).setHeader("Name");
		grid.addColumn(Bank::getCountryName).setHeader("Country");
		grid.addColumn(Bank::getLocalCurrencyName).setHeader("Local Currency");

		hlsearch.add(codeTF, bankNameTF, searchb);
		hlsearch.setVerticalComponentAlignment(Alignment.BASELINE, codeTF, bankNameTF, searchb);

		addBaseComponentsandStyle();

		searchb.addClickListener(event ->
		{
			grid.setItems(new ArrayList<Bank>());
			String code = codeTF.getValue();
			String value = bankNameTF.getValue();
			grid.setItems(bankRepository.findByCodeStartsWithAndNameStartsWith(code, value));
		});
	}

	public void reloadView()
	{
		grid.setItems(bankRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
		grid.setItems(bankRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
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
		getUI().ifPresent(ui -> ui.navigate(BankCrudView.class, param));
	}

	@Override
	public String getDialogTitle()
	{
		return "Search Bank";
	}
}

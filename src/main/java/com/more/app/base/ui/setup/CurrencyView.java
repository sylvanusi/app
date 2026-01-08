package com.more.app.base.ui.setup;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.base.ui.BaseView;
import com.more.app.base.ui.DialogSelectEntity;
import com.more.app.base.ui.LeftAlignedLayout;
import com.more.app.entity.Currency;
import com.more.app.repository.CurrencyRepository;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "currencyView", layout = LeftAlignedLayout.class)
public class CurrencyView extends BaseView<Currency>
{
	private static final long serialVersionUID = -4232596535128360059L;
	public static final String pageUrlString = "currency-view";
	public static final Long resourceAdd = Long.valueOf(16L);
	public static final Long resourceEdit = Long.valueOf(17L);
	public static final Long resourceView = Long.valueOf(18L);

	private TextField codeTF;
	private TextField currencyTF;
	
	@Autowired
	private CurrencyRepository currencyRepository;

	public CurrencyView()
	{
		super();
		view = this;
	}

	public CurrencyView(DialogSelectEntity dialog, Dialog dg)
	{
		super(dialog, dg);
	}
	
	public <T> CurrencyView(DialogSelectEntity dialog, Dialog dg, JpaRepository repository)
	{
		super(dialog, dg, repository);
		this.currencyRepository = (CurrencyRepository) repository;
	}

	public void loadComponents()
	{
		codeTF = new TextField("Code");
		codeTF.setMaxLength(3);

		currencyTF = new TextField("Currency");
		currencyTF.setMaxLength(35);

		grid.addColumn(Currency::getCode).setHeader("Code");
		grid.addColumn(Currency::getCurrency).setHeader("Currency");
		grid.addColumn(Currency::getCountryName).setHeader("Country");

		hlsearch.add(codeTF, currencyTF, searchb);
		hlsearch.setVerticalComponentAlignment(Alignment.BASELINE, codeTF, currencyTF, searchb);

		addBaseComponentsandStyle();

		searchb.addClickListener(evebt ->
		{
			grid.setItems(new ArrayList<Currency>());
			String code = codeTF.getValue();
			String currency = currencyTF.getValue();
			grid.setItems(currencyRepository.findByCodeStartsWithAndCurrencyStartsWith(code, currency));
		});
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
		grid.setItems(currencyRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
	}

	@Override
	public void reloadView()
	{
		grid.setItems(currencyRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
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
		getUI().ifPresent(ui -> ui.navigate(CurrencyCrudView.class, param));
	}

	@Override
	public String getDialogTitle()
	{
		return "Search Currency";
	}
}

package com.more.app.base.ui.setup;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.more.app.base.ui.BaseView;
import com.more.app.base.ui.DialogSelectEntity;
import com.more.app.base.ui.LeftAlignedLayout;
import com.more.app.entity.Country;
import com.more.app.repository.CountryRepository;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Component
@Route(value = "countryView", layout = LeftAlignedLayout.class)
public class CountryView extends BaseView<Country>
{
	private static final long serialVersionUID = 4156014215920148324L;
	public static final String pageUrlString = "country-view";
	public static final Long resourceAdd = Long.valueOf(13L);
	public static final Long resourceEdit = Long.valueOf(14L);
	public static final Long resourceView = Long.valueOf(15L);

	private TextField codeTF;
	private TextField countryNameTF;
	
	private CountryRepository countryRepository;

	@Autowired
	public CountryView(CountryRepository countryRepository)
	{
		super();
		this.countryRepository = countryRepository;
		view = this;
	}

	public CountryView(DialogSelectEntity dialog, Dialog dg)
	{
		super(dialog, dg);
	}
	

	
	public <T> CountryView(DialogSelectEntity dialog, Dialog dg, JpaRepository repository)
	{
		super(dialog, dg, repository);
		this.countryRepository = (CountryRepository) repository;
	}

	public void loadComponents()
	{
		codeTF = new TextField("Code");
		codeTF.setMaxLength(2);

		countryNameTF = new TextField("Country Name");
		countryNameTF.setMaxLength(35);

		grid.addColumn(Country::getCode).setHeader("Code");
		grid.addColumn(Country::getCountryName).setHeader("Country Name");

		hlsearch.add(codeTF, countryNameTF, searchb);
		hlsearch.setVerticalComponentAlignment(Alignment.BASELINE, codeTF, countryNameTF, searchb);

		addBaseComponentsandStyle();

		searchb.addClickListener(event ->
		{
			grid.setItems(new ArrayList<Country>());
			String code = codeTF.getValue();
			String countryName = countryNameTF.getValue();
			grid.setItems(countryRepository.findByCodeStartsWithAndCountryNameStartsWith(code, countryName));

		});
	}

	@Override
	public void onAttach(AttachEvent attachEvent)
	{
		grid.setItems(countryRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
	}

	@Override
	public void reloadView()
	{
		grid.setItems(countryRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
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
		getUI().ifPresent(ui -> ui.navigate(CountryCrudView.class, param));
	}

	@Override
	public String getDialogTitle()
	{
		return "Search Country";
	}
}

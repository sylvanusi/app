package com.more.app.base.ui.setup;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.more.app.base.ui.BaseView;
import com.more.app.base.ui.DialogSelectEntity;
import com.more.app.base.ui.LeftAlignedLayout;
import com.more.app.entity.SwiftBic;
import com.more.app.repository.SwiftBicRepository;
import com.more.app.util.annotations.UIActionUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = SwiftBicView.pageUrlString, layout = LeftAlignedLayout.class)
public class SwiftBicView extends BaseView<SwiftBic>
{
	private static final long serialVersionUID = 4566008868498338184L;
	public static final String pageUrlString = "swiftbic-view";
	public static final Long resourceAdd = Long.valueOf(28L);
	public static final Long resourceEdit = Long.valueOf(29L);
	public static final Long resourceView = Long.valueOf(30L);

	private TextField swiftBicTF;
	private TextField bankNameTF;
	private TextField bankCodeTF;
	private TextField countryNameTF;
	private SwiftBicView view;
	private ParametersCrudView cdg;
	
	@Autowired
	private SwiftBicRepository repository;

	public SwiftBicView()
	{
		super();
		view = this;
	}

	public SwiftBicView(DialogSelectEntity dialog, Dialog dg)
	{
		super(dialog, dg);
	}

	public void loadComponents()
	{
		swiftBicTF = new TextField("Swift BIC");
		swiftBicTF.setMaxLength(11);

		bankNameTF = new TextField("Bank Name");
		bankNameTF.setMaxLength(50);

		bankCodeTF = new TextField("Swift BIC");
		bankCodeTF.setMaxLength(4);

		countryNameTF = new TextField("Country Name");
		countryNameTF.setMaxLength(35);

		grid.addColumn(SwiftBic::getSwiftBic).setHeader(UIActionUtil.getFieldLabel(SwiftBic.class, "swiftBic"));
		grid.addColumn(SwiftBic::getBankName).setHeader(UIActionUtil.getFieldLabel(SwiftBic.class, "bankName"));
		grid.addColumn(SwiftBic::getBankCode).setHeader(UIActionUtil.getFieldLabel(SwiftBic.class, "bankCode"));
		grid.addColumn(SwiftBic::getCountryName).setHeader(UIActionUtil.getFieldLabel(SwiftBic.class, "countryName"));

		hlsearch.add(swiftBicTF, bankNameTF, bankCodeTF, countryNameTF, searchb);
		hlsearch.setVerticalComponentAlignment(Alignment.BASELINE, swiftBicTF, bankNameTF, bankCodeTF, countryNameTF,
				searchb);

		addBaseComponentsandStyle();

		searchb.addClickListener(event ->
		{
			grid.setItems(new ArrayList<SwiftBic>());
			String swiftBic = swiftBicTF.getValue();
			String bankName = bankNameTF.getValue();
			String bankCode = bankCodeTF.getValue();
			String countryName = countryNameTF.getValue();
			grid.setItems(repository.findBySwiftBicStartsWithAndBankNameStartsWithAndBankCodeStartsWithAndCountryNameStartsWith(swiftBic, bankName, bankCode, countryName));
		});
	}

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
		getUI().ifPresent(ui -> ui.navigate(SwiftBicCrudView.class, param));
	}

	@Override
	public String getDialogTitle()
	{
		return "Search Swift Bic";
	}
}

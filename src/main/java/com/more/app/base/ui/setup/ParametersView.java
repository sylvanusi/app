package com.more.app.base.ui.setup;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.base.ui.BaseView;
import com.more.app.base.ui.DialogSelectEntity;
import com.more.app.base.ui.LeftAlignedLayout;
import com.more.app.entity.Parameters;
import com.more.app.repository.ParametersRepository;
import com.more.app.util.annotations.UIActionUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = ParametersView.pageUrlString, layout = LeftAlignedLayout.class)
public class ParametersView extends BaseView<Parameters> 
{
	private static final long serialVersionUID = 4566008868498338184L;
	
	public static final String pageUrlString = "parameters-view";
	public static final Long resourceAdd = Long.valueOf(7L);
	public static final Long resourceEdit = Long.valueOf(8L);
	public static final Long resourceView = Long.valueOf(9L);
	
	private TextField codeTF;
	private TextField valueTF;
	ParametersView view;
	ParametersCrudView cdg;
	
	@Autowired
	private ParametersRepository parametersRepository;

	public ParametersView()
	{
		super();
		view = this;
	}

	public ParametersView(DialogSelectEntity dialog, Dialog dg)
	{
		super(dialog,dg);
	}
	
	public <T> ParametersView(DialogSelectEntity dialog, Dialog dg, JpaRepository repository)
	{
		super(dialog, dg, repository);
		this.parametersRepository = (ParametersRepository) repository;
	}

	public void loadComponents()
	{		
		codeTF = new TextField(UIActionUtil.getFieldLabel(Parameters.class, "paramCode"));
		codeTF.setMaxLength(5);

		valueTF = new TextField(UIActionUtil.getFieldLabel(Parameters.class, "paramValue"));
		valueTF.setMaxLength(35);

		grid.addColumn(Parameters::getParamCode).setHeader(UIActionUtil.getFieldLabel(Parameters.class, "paramCode"));
		grid.addColumn(Parameters::getParamValue).setHeader(UIActionUtil.getFieldLabel(Parameters.class, "paramValue"));
		
		hlsearch.add(codeTF, valueTF, searchb);
		hlsearch.setVerticalComponentAlignment(Alignment.BASELINE, codeTF, valueTF, searchb);

		addBaseComponentsandStyle();
		
		searchb.addClickListener(event ->
		{			
				grid.setItems(new ArrayList<Parameters>());
				String code = codeTF.getValue();
				String value = valueTF.getValue();
				grid.setItems(parametersRepository.findByParamCodeStartsWithAndParamValueStartsWith(code, value));
		});
	}
	
	public void reloadView()
	{
		grid.setItems(parametersRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
		grid.setItems(parametersRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
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
		getUI().ifPresent(ui -> ui.navigate(ParametersCrudView.class, param));
	}

	@Override
	public String getDialogTitle()
	{
		return "Search Parameter";
	}
}

package com.more.app.base.ui.configuration;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.more.app.base.ui.BaseView;
import com.more.app.base.ui.DialogSelectEntity;
import com.more.app.base.ui.LeftAlignedLayout;
import com.more.app.entity.ProductModule;
import com.more.app.repository.productsetup.ProductModuleRepository;
import com.more.app.util.annotations.UIActionUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = ProductModuleView.pageUrlString, layout = LeftAlignedLayout.class)
public class ProductModuleView extends BaseView<ProductModule> 
{
	private static final long serialVersionUID = 4566008868498338184L;
	public static final String pageUrlString = "module-view";
	public static final Long resourceAdd = Long.valueOf(43L);
	public static final Long resourceEdit = Long.valueOf(44L);
	public static final Long resourceView = Long.valueOf(45L);
	
	private TextField codeTF;
	private TextField nameTF;
	
	@Autowired
	private ProductModuleRepository repository;

	public ProductModuleView()
	{
		super();
		view = this;
	}

	public ProductModuleView(DialogSelectEntity dialog, Dialog dg)
	{
		super(dialog,dg);
	}

	public void loadComponents()
	{		
		codeTF = new TextField(UIActionUtil.getFieldLabel(ProductModule.class, "code"));
		codeTF.setMaxLength(2);

		nameTF = new TextField(UIActionUtil.getFieldLabel(ProductModule.class, "name"));
		nameTF.setMaxLength(35);

		grid.addColumn(ProductModule::getCode).setHeader(UIActionUtil.getFieldLabel(ProductModule.class, "code"));
		grid.addColumn(ProductModule::getName).setHeader(UIActionUtil.getFieldLabel(ProductModule.class, "name"));
		
		hlsearch.add(codeTF, nameTF, searchb);
		hlsearch.setVerticalComponentAlignment(Alignment.BASELINE, codeTF, nameTF, searchb);

		addBaseComponentsandStyle();
		
		searchb.addClickListener(event ->
			{
				grid.setItems(new ArrayList<ProductModule>());
				String code = codeTF.getValue();
				String value = nameTF.getValue();
				grid.setItems(repository.findByCodeStartsWithAndNameStartsWith(code, value));
			}

		);
		
		hl.remove(addb, editb);
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
	     getUI().ifPresent(ui -> ui.navigate(ProductModuleCrudView.class, param)); 	
	}

	@Override
	public String getDialogTitle()
	{
		return "Search Module";
	}
}

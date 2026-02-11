package com.more.app.base.ui.configuration;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.base.ui.BaseView;
import com.more.app.base.ui.DialogSelectEntity;
import com.more.app.base.ui.LeftAlignedLayout;
import com.more.app.entity.Product;
import com.more.app.repository.CurrencyRepository;
import com.more.app.repository.productsetup.ProductRepository;
import com.more.app.util.annotations.UIActionUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = ProductConfigurationView.pageUrlString, layout = LeftAlignedLayout.class)
public class ProductConfigurationView extends BaseView<Product>
{
	private static final long serialVersionUID = 4566008868498338184L;
	public static final String pageUrlString = "productcfg-view";
	public static final Long resourceAdd = Long.valueOf(55L);
	public static final Long resourceEdit = Long.valueOf(56L);
	public static final Long resourceView = Long.valueOf(57L);
	
	@Autowired
	private ProductRepository repository;

	public ProductConfigurationView()
	{
		super();
		this.view = this;
	}

	public ProductConfigurationView(DialogSelectEntity dialog, Dialog dg)
	{
		super(dialog, dg);
	}
	
	public <T> ProductConfigurationView(DialogSelectEntity dialog, Dialog dg, JpaRepository repository)
	{
		super(dialog, dg, repository);
		this.repository = (ProductRepository) repository;
	}

	private TextField codeTF;
	private TextField nameTF;

	@Override
	public void loadComponents()
	{
		codeTF = new TextField(UIActionUtil.getFieldLabel(Product.class, "productCode"));
		codeTF.setMaxLength(2);

		nameTF = new TextField(UIActionUtil.getFieldLabel(Product.class, "productName"));
		nameTF.setMaxLength(35);

		grid.addColumn(Product::getModuleCode)
				.setHeader(UIActionUtil.getFieldLabel(Product.class, "module"));
		grid.addColumn(Product::getTypeCode)
				.setHeader(UIActionUtil.getFieldLabel(Product.class, "type"));
		grid.addColumn(Product::getProductCode).setHeader(UIActionUtil.getFieldLabel(Product.class, "productCode"));
		grid.addColumn(Product::getProductName).setHeader(UIActionUtil.getFieldLabel(Product.class, "productName"));
		grid.addColumn(Product::getStatus).setHeader(UIActionUtil.getFieldLabel(Product.class, "status"));

		hlsearch.add(codeTF, nameTF, searchb);
		hlsearch.setVerticalComponentAlignment(Alignment.BASELINE, codeTF, nameTF, searchb);

		addBaseComponentsandStyle();

		searchb.addClickListener(event ->
		{
			grid.setItems(new ArrayList<Product>());
			String code = codeTF.getValue();
			grid.setItems(repository.findByProductCodeStartsWith(code));
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
		

		if(dg != null)
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
		getUI().ifPresent(ui -> ui.navigate(ProductConfigurationCrudView.class, param));
	}

	@Override
	public String getDialogTitle()
	{
		return "Search Product Configuration";
	}
}

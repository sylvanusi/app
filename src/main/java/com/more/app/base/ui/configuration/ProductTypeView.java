package com.more.app.base.ui.configuration;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.base.ui.BaseView;
import com.more.app.base.ui.DialogSelectEntity;
import com.more.app.base.ui.LeftAlignedLayout;
import com.more.app.entity.ProductModule;
import com.more.app.entity.ProductType;
import com.more.app.repository.productsetup.ProductModuleRepository;
import com.more.app.repository.productsetup.ProductTypeRepository;
import com.more.app.util.annotations.UIActionUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = ProductTypeView.pageUrlString, layout = LeftAlignedLayout.class)
public class ProductTypeView extends BaseView<ProductType>
{
	private static final long serialVersionUID = 4566008868498338184L;
	public static final String pageUrlString = "producttype-view";
	public static final Long resourceAdd = Long.valueOf(46L);
	public static final Long resourceEdit = Long.valueOf(47L);
	public static final Long resourceView = Long.valueOf(48L);

	private TextField codeTF;
	private TextField productTypeTF;
	private TextField moduleTF;
	private Button prodModuleBtn;

	private ProductModule productModule;
	
	@Autowired
	private ProductTypeRepository repository;
	
	@Autowired
	private ProductModuleRepository moduleRepo;

	public ProductTypeView()
	{
		super();
		view = this;
	}

	public ProductTypeView(DialogSelectEntity dialog, Dialog dg)
	{
		super(dialog, dg);
	}
	
	public <T> ProductTypeView(DialogSelectEntity dialog, Dialog dg, JpaRepository repository)
	{
		super(dialog, dg, repository);
		this.repository = (ProductTypeRepository) repository;
	}
	
	public <T> ProductTypeView( Dialog dg, JpaRepository repository)
	{
		super(dg, repository);
		this.repository = (ProductTypeRepository) repository;
	}
	
	public <T> ProductTypeView( Dialog dg, JpaRepository repository, ProductModuleRepository moduleRepo)
	{
		super(dg, repository);
		this.repository = (ProductTypeRepository) repository;
		this.moduleRepo = moduleRepo;
	}

	public void loadComponents()
	{
		prodModuleBtn = new Button("Get Module",new Icon(VaadinIcon.SEARCH));
		codeTF = new TextField(UIActionUtil.getFieldLabel(ProductType.class, "code"));
		codeTF.setMaxLength(12);

		productTypeTF = new TextField(UIActionUtil.getFieldLabel(ProductType.class, "productType"));
		productTypeTF.setMaxLength(35);

		moduleTF = new TextField(UIActionUtil.getFieldLabel(ProductType.class, "module"));
		moduleTF.setMaxLength(35);

		grid.addColumn(ProductType::getCode).setHeader(UIActionUtil.getFieldLabel(ProductType.class, "code"));
		grid.addColumn(ProductType::getProductType)
				.setHeader(UIActionUtil.getFieldLabel(ProductType.class, "productType"));
		grid.addColumn(ProductType::getModuleName)
				.setHeader(UIActionUtil.getFieldLabel(ProductType.class, "moduleName"));

		hlsearch.add(codeTF, productTypeTF, moduleTF, prodModuleBtn, searchb);
		hlsearch.setVerticalComponentAlignment(Alignment.BASELINE, codeTF, productTypeTF, moduleTF, prodModuleBtn,
				searchb);

		addBaseComponentsandStyle();

		searchb.addClickListener(event ->
		{
			if(null == productModule)
			{
				Notification n = new Notification();
				n.addThemeVariants(NotificationVariant.LUMO_ERROR);
				Div content = new Div();
				// content.getClassNames().add("savenofication");
				content.setText("Select Module");
				n.add(content);
				n.setDuration(5000);
				n.setPosition(Position.TOP_CENTER);
				n.open();
				return;
			}
			grid.setItems(new ArrayList<ProductType>());
			String code = codeTF.getValue();
			String productType = productTypeTF.getValue();
			grid.setItems(repository.findByModuleIdAndCodeStartsWithAndProductTypeStartsWith(productModule.getId(), code, productType));
		});

		prodModuleBtn.addClickListener(event ->
		{

			Dialog dg = new Dialog();
			productModule = null;
			ProductModuleView cview = new ProductModuleView(dg,moduleRepo);
					
			cview.select.setVisible(false);
			Button selectItem_n = new Button("Select Item");
			selectItem_n.setSizeFull();
			selectItem_n.addClickListener(evt ->
			{
				if (!cview.grid.getSelectedItems().isEmpty())
				{
					Object[] arr = cview.grid.getSelectedItems().toArray();
					ProductModule productM = (ProductModule) arr[0];
					productModule = productM;
					moduleTF.setValue(productModule.getName());
					dg.close();
				}

			});
			cview.add(selectItem_n);
			dg.add(cview);
			dg.open();

		});

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
		getUI().ifPresent(ui -> ui.navigate(ProductTypeCrudView.class, param));
	}

	@Override
	public String getDialogTitle()
	{
		return "Search Product Type";
	}
}

package com.more.app.base.ui.setup;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.base.ui.BaseView;
import com.more.app.base.ui.DialogSelectEntity;
import com.more.app.base.ui.LeftAlignedLayout;
import com.more.app.entity.Customer;
import com.more.app.repository.CustomerRepository;
import com.more.app.util.annotations.UIActionUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = CustomerView.pageUrlString, layout = LeftAlignedLayout.class)
public class CustomerView extends BaseView<Customer>
{
	public static final String pageUrlString = "customer-view";
	public static final Long resourceAdd = Long.valueOf(34L);
	public static final Long resourceEdit = Long.valueOf(35L);
	public static final Long resourceView = Long.valueOf(36L);
	private TextField fullNameTF;
	private Select<Boolean> correspondentBox;
	private Select<String> customerTypeTF;
	private TextField customerNoTF;
	
	@Autowired
	private CustomerRepository repository;

	public CustomerView()
	{
		super();
		view = this;
	}

	public CustomerView(DialogSelectEntity dialog, Dialog dg)
	{
		super(dialog, dg);
	}

	public <T> CustomerView(DialogSelectEntity dialog, Dialog dg, JpaRepository repository)
	{
		super(dialog, dg, repository);
		this.repository = (CustomerRepository) repository;
	}

	@Override
	public void loadComponents()
	{
		fullNameTF = new TextField();
		fullNameTF.setMaxLength(50);
		fullNameTF.setLabel(UIActionUtil.getFieldLabel(Customer.class, "fullName"));

		customerNoTF = new TextField();
		customerNoTF.setMaxLength(35);
		customerNoTF.setLabel(UIActionUtil.getFieldLabel(Customer.class, "customerNo"));

		correspondentBox = new Select<Boolean>();
		correspondentBox.setItems(true, false);
		correspondentBox.setLabel(UIActionUtil.getFieldLabel(Customer.class, "correspondent"));

		customerTypeTF = new Select<String>();
		customerTypeTF.setItems("", "Customer", "Bank");
		customerTypeTF.setLabel(UIActionUtil.getFieldLabel(Customer.class, "customerType"));

		grid.addColumn(Customer::getFullName).setHeader(UIActionUtil.getFieldLabel(Customer.class, "fullName"));
		grid.addColumn(Customer::getCustomerNo).setHeader(UIActionUtil.getFieldLabel(Customer.class, "customerNo"));
		grid.addColumn(Customer::getEmail).setHeader(UIActionUtil.getFieldLabel(Customer.class, "email"));
		grid.addColumn(Customer::getPhoneNo).setHeader(UIActionUtil.getFieldLabel(Customer.class, "phoneNo"));
		grid.addColumn(Customer::getCustomerType).setHeader(UIActionUtil.getFieldLabel(Customer.class, "customerType"));
		grid.addColumn(Customer::isCorrespondent)
				.setHeader(UIActionUtil.getFieldLabel(Customer.class, "correspondent"));

		hlsearch.add(fullNameTF, customerNoTF, customerTypeTF, searchb);
		hlsearch.setVerticalComponentAlignment(Alignment.BASELINE, fullNameTF, customerNoTF, customerTypeTF,
				 searchb);

		addBaseComponentsandStyle();

		searchb.addClickListener(new ComponentEventListener<ClickEvent<Button>>()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event)
			{
				grid.setItems(new ArrayList<Customer>());

				String customerType = customerTypeTF.getValue();
				if (null != customerType)
				{
					if (customerType.trim().isEmpty())
						customerType = null;
					if (customerType.trim().equals("Customer"))
						customerType = "C";
					if (customerType.trim().equals("Bank"))
						customerType = "B";
				}

				String fullName = fullNameTF.getValue();
				String customerNo = customerNoTF.getValue();
				Boolean correspondent = correspondentBox.getValue();

				grid.setItems(repository.findByFullNameStartsWithAndCustomerNoStartsWithAndCustomerTypeStartsWith(fullName, customerNo, customerType));//, correspondent
			}

		});
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
		grid.setItems(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
	}

	@Override
	public void reloadView()
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
		getUI().ifPresent(ui -> ui.navigate(CustomerCrudView.class, param));
	}

	@Override
	public String getDialogTitle()
	{
		return "Search Customer";
	}
}

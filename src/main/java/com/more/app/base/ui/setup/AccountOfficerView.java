package com.more.app.base.ui.setup;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.base.ui.BaseView;
import com.more.app.base.ui.DialogSelectEntity;
import com.more.app.base.ui.LeftAlignedLayout;
import com.more.app.entity.AccountOfficer;
import com.more.app.repository.AccountOfficerRepository;
import com.more.app.repository.CustomerRepository;
import com.more.app.util.annotations.UIActionUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = AccountOfficerView.pageUrlString, layout = LeftAlignedLayout.class)
public class AccountOfficerView extends BaseView<AccountOfficer>
{
	private static final long serialVersionUID = 4156014215920148324L;
	public static final String pageUrlString = "accountofficer-view";
	public static final Long resourceAdd = Long.valueOf(31L);
	public static final Long resourceEdit = Long.valueOf(32L);
	public static final Long resourceView = Long.valueOf(33L);

	private TextField fullNameTF;
	private TextField departmentTF;
	

	@Autowired
	private AccountOfficerRepository repository;

	public AccountOfficerView()
	{
		super();
		view = this;
	}

	public AccountOfficerView(DialogSelectEntity dialog, Dialog dg)
	{
		super(dialog, dg);
	}

	public <T> AccountOfficerView(DialogSelectEntity dialog, Dialog dg, JpaRepository repository)
	{
		super(dialog, dg, repository);
		this.repository = (AccountOfficerRepository) repository;
	}

	public void loadComponents()
	{
		fullNameTF = new TextField(UIActionUtil.getFieldLabel(AccountOfficer.class, "fullName"));
		fullNameTF.setMaxLength(50);

		departmentTF = new TextField(UIActionUtil.getFieldLabel(AccountOfficer.class, "department"));
		departmentTF.setMaxLength(35);

		grid.addColumn(AccountOfficer::getFullName)
				.setHeader(UIActionUtil.getFieldLabel(AccountOfficer.class, "fullName"));
		grid.addColumn(AccountOfficer::getEmail).setHeader(UIActionUtil.getFieldLabel(AccountOfficer.class, "email"));
		grid.addColumn(AccountOfficer::getPhoneNo)
				.setHeader(UIActionUtil.getFieldLabel(AccountOfficer.class, "phoneNo"));
		grid.addColumn(AccountOfficer::getDepartment)
				.setHeader(UIActionUtil.getFieldLabel(AccountOfficer.class, "department"));

		hlsearch.add(fullNameTF, departmentTF, searchb);
		hlsearch.setVerticalComponentAlignment(Alignment.BASELINE, fullNameTF, departmentTF, searchb);

		addBaseComponentsandStyle();

		searchb.addClickListener(event ->
		{
			grid.setItems(new ArrayList<AccountOfficer>());
			String fullName = fullNameTF.getValue();
			String department = departmentTF.getValue();
			grid.setItems(repository.findByFullNameStartsWithAndDepartmentStartsWith(fullName, department));
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
		getUI().ifPresent(ui -> ui.navigate(AccountOfficerCrudView.class, param));
	}

	@Override
	public String getDialogTitle()
	{
		return "Search Account Officer";
	}
}

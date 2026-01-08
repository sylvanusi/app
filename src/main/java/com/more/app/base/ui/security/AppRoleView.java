package com.more.app.base.ui.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.more.app.base.ui.BaseView;
import com.more.app.base.ui.DialogSelectEntity;
import com.more.app.base.ui.LeftAlignedLayout;
import com.more.app.entity.AppRole;
import com.more.app.repository.AppRoleRepository;
import com.more.app.util.annotations.UIActionUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = AppRoleView.pageUrlString, layout = LeftAlignedLayout.class)
public class AppRoleView extends BaseView<AppRole>
{
	private static final long serialVersionUID = 4156014215920148324L;
	public static final String pageUrlString = "role-view";

	public static final Long resourceAdd = Long.valueOf(4L);
	public static final Long resourceEdit = Long.valueOf(5L);
	public static final Long resourceView = Long.valueOf(6L);

	private TextField nameTF;
	private TextField statusTF;
	
	@Autowired
	private AppRoleRepository appRoleRepository;

	public AppRoleView()
	{
		super();
		view = this;
	}

	public AppRoleView(DialogSelectEntity dialog, Dialog dg)
	{
		super(dialog, dg);
	}

	public void loadComponents()
	{
		nameTF = new TextField(UIActionUtil.getFieldLabel(AppRole.class, "name"));
		nameTF.setMaxLength(35);

		statusTF = new TextField(UIActionUtil.getFieldLabel(AppRole.class, "status"));
		statusTF.setMaxLength(12);

		grid.addColumn(AppRole::getName).setHeader(UIActionUtil.getFieldLabel(AppRole.class, "name"));
		grid.addColumn(AppRole::getStatus).setHeader(UIActionUtil.getFieldLabel(AppRole.class, "status"));

		hlsearch.add(nameTF, statusTF, searchb);
		hlsearch.setVerticalComponentAlignment(Alignment.BASELINE, nameTF, statusTF, searchb);

		addBaseComponentsandStyle();

		searchb.addClickListener(event ->
		{
			grid.setItems(new ArrayList<AppRole>());
			String name = nameTF.getValue();
			String status = statusTF.getValue();
			grid.setItems(appRoleRepository.findByNameStartsWithAndStatusStartsWith(name, status));
		});
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
		grid.setItems(appRoleRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
	}

	@Override
	public void reloadView()
	{
		grid.setItems(appRoleRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
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
		getUI().ifPresent(ui -> ui.navigate(AppRoleCrudView.class, param));
	}

	@Override
	public String getDialogTitle()
	{
		return "Search Role";
	}

}

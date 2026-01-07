package com.more.app.entity.ui;

import java.util.ArrayList;
import java.util.Arrays;mport java.util

impor

import com.more.app.annotations.UIActionUtil;
import com.more.app.base.ui.BaseView;
import com.more.app.base.ui.DialogSelectEntity;
import com.more.app.entity.AppResource;
import com.more.app.entity.AppRole;
import com.more.app.entity.enums.ModuleName;ew;
import com.more.app.base.ui.DialogSelectEntity;
import com.more.app.entity.AppResource;
import com.more.app.entity.AppRole;
import com.more.app.entity.enums.ModuleName;.app.annotations.UIActionUtil;
import com.princeps.app.authentication.CurrentUser;
import com.princeps.app.authorization.PermissionController;
import comort com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;Component.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
importvaadin.flow.router.RouteParam;

@Route(value = PermissionEntityView.pageUrlString, layout = LeftAlignedLayout.class)
public class PermissionEntityView extends BaseView<PermissionEntity>
{
	private static final long serialVersionUID = 4566008868498338184L;
	public static ew";

	public static final Long resourceAdd = Long.valueOf(10L);
	public static final Long resourceEdit = Long.valueOf(11L);
	public static final Long resourceView = Long.valueOf(12L);

	private TextField roleTF;
	private TextField descriptionTA;
	private Button roleSearch;
	private Button createPermissionBtn;
	private Select moduleBox;
	private Button filterByModule;
	private AppRole role;
	private String username;
	private Button updatePerm;

	public PermissionEntityView()
	{
		super();
		view = this;
		username = CurrentUser.get();
	}

	public PermissionEntityView(DialogSelectEntity dialog, Dialog dg)
	{
		super(dialog, dg);
		username = CurrentUser.get();
	}

	public void loadComponents()
	{
		VerticalLayout vl = new VerticalLayout();
		roleTF = new TextField("Role");
		roleTF.setMaxLength(15);

		descriptionTA = new TextField("Role Description");
		descriptionTA.setMinWidth("350px");

		roleSearch = new Button(new Icon(VaadinIcon.SEARCH));

		createPermissionBtn = new Button("Create Role Permissions");
		createPermissionBtn.setWidth("300px");
		createPermissionBtn.getClassNames().add("searchbuttonadd");

		moduleBox = new Select();
		moduleBox.setEmptySelectionAllowed(true);
		moduleBox.setItems("SECURITY", "SETUP");

		filterByModule = new Button("Filter By Module", new Icon(VaadinIcon.SEARCH));

		updatePerm = new Button("Update Permissions");

		HorizontalLayout hlRoleS = new HorizontalLayout();

		hlRoleS.add(roleTF, roleSearch, descriptionTA, searchb);
		hlRoleS.setVerticalComponentAlignment(Alignment.BASELINE, roleTF, roleSearch, descriptionTA, searchb);

		vl.add(hlRoleS);

		if (PermissionController.hasAccess("ADD", resourceAdd))
		{
			vl.add(createPermissionBtn);
			createPermissionBtn.setVisible(false);
		}

		HorizontalLayout hlModuleS = new HorizontalLayout();

		hlModuleS.add(moduleBox, filterByModule);
		hlModuleS.setVerticalComponentAlignment(Alignment.BASELINE, moduleBox, filterByModule);

		vl.add(hlModuleS);

		hlsearch.add(vl);

		grid.addColumn(PermissionEntity::getModuleName)
				.setHeader(UIActionUtil.getFieldLabel(PermissionEntity.class, "moduleName"));
		grid.addColumn(PermissionEntity::getResourceName)
				.setHeader(UIActionUtil.getFieldLabel(PermissionEntity.class, "resourceName"));
		grid.addColumn(PermissionEntity::getResourceDescription)
				.setHeader(UIActionUtil.getFieldLabel(PermissionEntity.class, "resourceDescription"));

		if (PermissionController.hasAccess("ADD", resourceAdd) || PermissionController.hasAccess("EDIT", resourceEdit))
		{
			grid.addColumn(new ComponentRenderer<>(entity ->
			{
				PermissionEntity pe = (PermissionEntity) entity;
				Select sf = new Select();
				sf.setItems(Arrays.asList(PermissionType.values()));
				sf.setValue(pe.getType());
				sf.addValueChangeListener(event ->
				{
					if (event.getValue() != null)
					{
						pe.setType((PermissionType) event.getValue());
						grid.getDataProvider().refreshItem(pe);
						FacadeFactory.getFacade().store(pe);
					}
				});

				return sf;
			})).setHeader("Permission Type");
		} else
			grid.addColumn(PermissionEntity::getType)
					.setHeader(UIActionUtil.getFieldLabel(PermissionEntity.class, "type"));
		addBaseComponentsandStyle();

		roleSearch.addClickListener(event ->
		{
			grid.setItems(new ArrayList<PermissionEntity>());
			role = null;
			AppRoleView cview = new AppRoleView();
			cview.remove(cview.getHl());
			Button selectItem = new Button("Select Item");
			selectItem.setSizeFull();
			selectItem.addClickListener(evt ->
			{
				if (!cview.grid.getSelectedItems().isEmpty())
				{
					Object[] arr = cview.grid.getSelectedItems().toArray();
					AppRole apprg = (AppRole) arr[0];
					role = apprg;
					roleTF.setValue(role.getName());
					descriptionTA.setValue(role.getDescription());
					if (PermissionEntityDAO.search(role, null).size() == 0)
						createPermissionBtn.setVisible(true);
					else
					{
						createPermissionBtn.setVisible(false);
						grid.setItems(PermissionEntityDAO.search(role, null));
					}
					setRole(role);
				}
			});
			cview.add(selectItem);
			Dialog dg = new Dialog();
			dg.add(cview);
			dg.open();
		});

		createPermissionBtn.addClickListener(event ->
		{
			try
			{
				List<AppResource> resList = FacadeFactory.getFacade().list(AppResource.class);
				List<PermissionEntity> pmelist = new ArrayList<PermissionEntity>();

				for (AppResource ar : resList)
				{
					PermissionEntity permission = new PermissionEntity(PermissionType.DENY);
					permission.setRole(getRole().getId().toString());
					permission.setAction(ar.getAction());
					permission.setResource(ar.getIdentifier());
					permission.setAppresource(ar);
					pmelist.add(permission);
				}

				if (pmelist.size() > 0)
				{
					FacadeFactory.getFacade().storeAll(pmelist);
				}

				grid.setItems(PermissionEntityDAO.search(getRole(), null));

				createPermissionBtn.setVisible(false);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		});

		filterByModule.addClickListener(event ->
		{
			if (null == getRole() || null == moduleBox.getValue())
				return;
			String values = (String) moduleBox.getValue();
			ModuleName value = null;
			if (values.equals("SETUP"))
			{
				value = ModuleName.SETUP;
			}
			if (values.equals("SECURITY"))
			{
				value = ModuleName.SECURITY;
			}
			grid.setItems(PermissionEntityDAO.search(getRole(), value));
		});
	}

	public void reloadView()
	{
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
	}

	@Override
	public boolean getAddPermission()
	{
		return false;
	}

	@Override
	public boolean getEditPermission()
	{
		return false;
	}

	@Override
	public boolean getViewPermission()
	{
		return false;
	}

	public AppRole getRole()
	{
		return role;
	}

	public void setRole(AppRole role)
	{
		this.role = role;
	}

	@Override
	public void navigationAction(String param)
	{
	}

	@Override
	public String getDialogTitle()
	{
		return "Search Permissions";
	}
}

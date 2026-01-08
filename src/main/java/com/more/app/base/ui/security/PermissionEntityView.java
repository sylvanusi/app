package com.more.app.base.ui.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.more.app.authorization.PermissionController;
import com.more.app.authorization.PermissionEntity;
import com.more.app.authorization.PermissionType;
import com.more.app.base.ui.BaseView;
import com.more.app.base.ui.DialogSelectEntity;
import com.more.app.base.ui.LeftAlignedLayout;
import com.more.app.entity.AppResource;
import com.more.app.entity.AppRole;
import com.more.app.repository.AppResourceRepository;
import com.more.app.repository.PermissionEntityRepository;
import com.more.app.util.annotations.UIActionUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

@Route(value = PermissionEntityView.pageUrlString, layout = LeftAlignedLayout.class)
public class PermissionEntityView extends BaseView<PermissionEntity>
{
	private static final long serialVersionUID = 4566008868498338184L;
	public static final String pageUrlString = "permission-view";

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
	
	@Autowired
	private PermissionController permissionController;
	
	@Autowired
	private PermissionEntityRepository permissionEntityRepository;
	
	@Autowired
	private AppResourceRepository appResourceRepository;

	public PermissionEntityView()
	{
		super();
		view = this;
		username = "Sylvanus";
	}

	public PermissionEntityView(DialogSelectEntity dialog, Dialog dg)
	{
		super(dialog, dg);
		username = "Sylvanus";
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

		if (permissionController.hasAccess("ADD", resourceAdd))
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

		if (permissionController.hasAccess("ADD", resourceAdd) || permissionController.hasAccess("EDIT", resourceEdit))
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
						//FacadeFactory.getFacade().store(pe);
						permissionEntityRepository.save(pe);
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
					if (permissionEntityRepository.findByRole(role.getIdentifier()).size() == 0)
						createPermissionBtn.setVisible(true);
					else
					{
						createPermissionBtn.setVisible(false);
						grid.setItems(permissionEntityRepository.findByRole(role.getIdentifier()));
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
				List<AppResource> resList = appResourceRepository.findAll();
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
					permissionEntityRepository.saveAll(pmelist);
				}

				grid.setItems(permissionEntityRepository.findByRole(role.getIdentifier()));

				createPermissionBtn.setVisible(false);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
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

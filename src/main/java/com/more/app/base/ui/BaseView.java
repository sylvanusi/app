package com.more.app.base.ui;


import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.entity.AbstractPojo;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.RouterLayout;

import jakarta.annotation.PostConstruct;

public abstract class BaseView<T extends AbstractPojo> extends VerticalLayout
		implements BeforeEnterListener, RouterLayout
{
	private static final long serialVersionUID = -4606100481847088963L;
	private static final String PAGE_MODE_ADD = "ADD";
	private static final String PAGE_MODE_EDIT = "EDIT";
	private static final String PAGE_MODE_VIEW = "VIEW";
	public Grid<T> grid = new Grid<T>();
	protected HorizontalLayout hl = new HorizontalLayout();
	protected HorizontalLayout hlsearch = new HorizontalLayout();
	protected Button addb = new Button("Add", new Icon(VaadinIcon.PLUS_SQUARE_O));
	protected Button editb = new Button("Edit", new Icon(VaadinIcon.EDIT));
	protected Button viewb = new Button("View", new Icon(VaadinIcon.EYE));
	protected Button searchb = new Button("Search", new Icon(VaadinIcon.SEARCH));
	public Button select = new Button("Select Item", new Icon(VaadinIcon.SELECT));
	protected DialogSelectEntity dialogSelectEntity;
	protected Dialog dg;
	protected H4 title = new H4();
	protected boolean hasDialogue = false;
	protected BaseView<T> view;
	private JpaRepository<T, Long> repository;
	
	//protected JpaRepository<T> repository;

	public BaseView()
	{
		this.view = this;
		loadComponents();

		setHeight("100%");
		setWidthFull();
		

		getStyle().set("overflow-y", "auto");
		setSpacing(true);
		setMargin(true);
	}

	public BaseView(DialogSelectEntity dialogSelectEntity, Dialog dg)
	{
		this.dialogSelectEntity = dialogSelectEntity;
		this.dg = dg;
		hasDialogue = true;
		loadComponents();
		setSpacing(true);
		setMargin(true);
	}
	
	public BaseView(DialogSelectEntity dialogSelectEntity, Dialog dg, JpaRepository<T, Long> repository)
	{
		this.dialogSelectEntity = dialogSelectEntity;
		this.dg = dg;
		hasDialogue = true;
		this.repository = repository;
		loadComponents();
	}
	
	public BaseView(Dialog dg,JpaRepository<T, Long> repository)
	{
		this.dg = dg;
		this.repository = repository;
		hasDialogue = true;
		loadComponents();
		setSpacing(true);
		setMargin(true);
	}

	public void addBaseComponentsandStyle()
	{
		Icon close = new Icon(VaadinIcon.CLOSE_CIRCLE_O);
		close.addClickListener(event -> {
			if (hasDialogue)
				dg.close();
			else
				getUI().get().navigate("nav");
		});

		title.setText(getDialogTitle());
		title.getElement().getStyle().set("font-weight", "normal");
		title.getElement().getStyle().set("font-weight", "bold");

		HorizontalLayout hlheader = new HorizontalLayout();
		hlheader.setPadding(false);
		hlheader.setMargin(false);
		hlheader.setSpacing(true);
		hlheader.add(title,close);
		hlheader.setSizeFull();
		hlheader.setFlexGrow(4, title);
		Hr hr = new Hr();
		hr.setSizeFull();
		add(hlheader, hr);
		
		VerticalLayout innerVl = new VerticalLayout();
		innerVl.getElement().getStyle().set("background-color", "white");
		
		  
		  hr.getElement().getStyle().set("border-top", "1.0px solid #000000");


		addb.getClassNames().add("searchbuttonadd");
		editb.getClassNames().add("searchbuttonedit");
		viewb.getClassNames().add("searchbuttonview");
		searchb.getClassNames().add("searchbuttonsearch");
		select.getClassNames().add("searchbuttonselect");

		grid.addThemeVariants(GridVariant.AURA_COLUMN_BORDERS, GridVariant.LUMO_COMPACT);
		grid.addThemeVariants(GridVariant.AURA_ROW_STRIPES);
		//grid.getElement().getStyle().set("border", "0.5px bold #e6f5ff");
		grid.addClassName(PAGE_MODE_ADD);

		//grid.getClassNames().add("appgrid");
		

		hlsearch.getElement().getStyle().set("border", "0.5px solid #e0b5b1");
		hl.getElement().getStyle().set("border", "0.5px solid #e0b5b1");

		hlsearch.getElement().getStyle().set("margin-top", "1em");
		hlsearch.getElement().getStyle().set("padding-top", "0em");
		hlsearch.getElement().getStyle().set("padding-right", "0.4em");
		hlsearch.getElement().getStyle().set("padding-bottom", "0.6em");
		hlsearch.getElement().getStyle().set("padding-left", "0.4em");

		hl.getElement().getStyle().set("padding-top", "0.6em");
		hl.getElement().getStyle().set("padding-right", "0.4em");
		hl.getElement().getStyle().set("padding-bottom", "0.6em");
		hl.getElement().getStyle().set("padding-left", "0.4em");

		grid.setSelectionMode(SelectionMode.SINGLE);
		grid.setPageSize(10);
		grid.setMaxHeight("530px");

		if (getAddPermission())
			hl.add(addb);
		if (getEditPermission())
			hl.add(editb);
		if (getViewPermission())
			hl.add(viewb);

		innerVl.add(hlsearch);

		if (!hasDialogue)
			innerVl.add(hl);

		innerVl.add(grid);

		
		select.setWidth("300px");
		select.setHeight("40px");
		
		HorizontalLayout dgFooter = new HorizontalLayout(select);
		if (hasDialogue)
			innerVl.add(dgFooter);

		select.addClickListener(evt ->
		{
			if (!grid.getSelectedItems().isEmpty())
			{
				Object[] arr = grid.getSelectedItems().toArray();
				dialogSelectEntity.setSelectedEntity((T) arr[0]);
				dg.close();
			}
		});

		

		if (hl.getComponentCount() == 0)
			remove(hl);
		
		add(innerVl);
	}
	
	@PostConstruct
	private void init() {
		addb.addClickListener(evt -> navigationAction(PAGE_MODE_ADD));
		editb.addClickListener(event -> navigationAction(PAGE_MODE_EDIT + "," + getSelectedItemId()));
		viewb.addClickListener(event -> navigationAction(PAGE_MODE_VIEW + "," + getSelectedItemId()));
		
	}
	
	

	public abstract void loadComponents();

	public abstract void reloadView();

	public abstract boolean getAddPermission();

	public abstract boolean getEditPermission();

	public abstract boolean getViewPermission();

	public abstract void navigationAction(String param);

	public abstract String getDialogTitle();

	public HorizontalLayout getHl()
	{
		return hl;
	}

	public void setHl(HorizontalLayout hl)
	{
		this.hl = hl;
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event)
	{
		reloadView();
	}

	private Long getSelectedItemId()
	{
		if (grid.getSelectedItems().isEmpty())
			return -1L;
		Object[] arr = grid.getSelectedItems().toArray();
		return ((AbstractPojo) arr[0]).getId();
	}
}

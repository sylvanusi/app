package com.more.app.base.ui;

import java.util.ArrayList;
import java.util.List;

import com.more.app.entity.AbstractPojo;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayoutVariant;
import com.vaadin.flow.data.binder.Binder;

import jakarta.persistence.OptimisticLockException;

public abstract class BaseCrudComponent<T extends AbstractPojo> extends VerticalLayout implements DialogSelectEntity {
	private static final long serialVersionUID = 8293319876445303508L;
	protected T entity;
	protected String pageMode = "";
	protected Binder<T> binder;
	protected VerticalLayout errorVl = new VerticalLayout();
	protected VerticalLayout vl = new VerticalLayout();
	protected BaseCrudComponent<T> ui;
	protected H4 title = new H4();
	private Div body = new Div();
	public Button confirmButton, addewButton, closeButton;

	@SuppressWarnings("unchecked")
	public BaseCrudComponent() {
		this.ui = this;
		this.entity = getEntity();
		this.pageMode = getPageMode();
		this.binder = getBinder();

		initializeComponents();

		Notification n = new Notification();
		n.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

		confirmButton = new Button("Save Record", new Icon(VaadinIcon.CHECK_SQUARE_O), event -> {
			try {
				errorVl.removeAll();
				ui.vl.remove(errorVl);

				T entity = getBinder().getBean();
				getBinder().validate();
				List<String> erString = new ArrayList();
				if (erString.isEmpty() && getBinder().validate().isOk()) {
					if (ui.getPageMode().equals("ADDNEW"))
						entity.setId(null);
					setEventEntityFields(entity);
					if (!checkConditions(entity)) {
						n.show("Record Not Saved", 7000, Position.BOTTOM_STRETCH);
						return;
					}

					System.out.println(entity.toString());

					// entity.setAuditUser(CurrentUser.get());
					// FacadeFactory.getFacade().store(entity);
					saveRecord(entity);
					n.removeAll();
					Div content = new Div();
					// content.getClassNames().add("savenofication");
					content.setText("Record Saved");
					n.add(content);
					n.setDuration(5000);
					n.setPosition(Position.TOP_CENTER);
					n.open();
					updateFieldsOnSave();
					ui.setPageMode("EDIT");
					getBinder().setBean(entity);
				} else {
					String caption = "";
					for (int i = 0; i < erString.size(); i++) {
						H1 errorLabel = new H1("");
						errorLabel.setText((String) erString.get(i));
						errorLabel.getElement().getStyle().set("color", "red");
						errorVl.add(errorLabel);
						ui.vl.addComponentAtIndex(0, errorVl);
					}
				}

			} catch (OptimisticLockException e) {
				// FacadeFactory.getFacade().refresh(entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		addewButton = new Button("Add New Record", new Icon(VaadinIcon.PLUS_SQUARE_O), event -> {
			try {
				errorVl.removeAll();
				ui.vl.remove(errorVl);
				clearSelectedEntity(entity);
				getBinder().setBean((T) getEntity().getClass().newInstance());
				ui.setPageMode("ADDNEW");
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});

		closeButton = new Button("Close", new Icon(VaadinIcon.CLOSE_CIRCLE_O));
		closeButton.addClickListener(event -> {
			//removeAll();
			getUI().get().navigate((Class<? extends Component>) getCloseNavigationClass());
		});

		HorizontalLayout hl = new HorizontalLayout();
		hl.setPadding(true);
		// hl.setSizeFull();
		hl.setSpacing(true);
		hl.getElement().getStyle().set("border", "1px solid #e6f5ff");
		hl.add(confirmButton, addewButton, closeButton);
		confirmButton.setVisible(false);
		addewButton.setVisible(false);

		title.setText(getPageTitle());

		title.getElement().getStyle().set("font-weight", "bold");

		HorizontalLayout hlheader = new HorizontalLayout();
		hlheader.setPadding(false);
		hlheader.setMargin(false);
		hlheader.setSpacing(true);
		hlheader.add(title);
		hlheader.setVerticalComponentAlignment(Alignment.CENTER, title, closeButton);
		hlheader.setFlexGrow(4, title);
		Hr hr = new Hr();
		hr.setWidthFull();
		add(hlheader, hr);

		body.getElement().getStyle().set("overflow", "auto");
		body.getElement().getStyle().set("padding-right", "var(--lumo-space-m)");

		vl.setPadding(true);
		vl.setMargin(false);
		vl.setSpacing(true);
		vl.setWidthFull();

		vl.addThemeVariants(VerticalLayoutVariant.LUMO_SPACING_XL);
		vl.getElement().getStyle().set("background-color", "white");

		body.setWidthFull();
		body.add(vl);

		add(body);

		Hr hr1 = new Hr();
		hr1.setWidthFull();
		hr1.getElement().getStyle().set("border-top", "1.0px solid #000000");
		add(hr1);

		add(hl);

		confirmButton.getElement().getStyle().set("color", "#004e06");
		confirmButton.getElement().getStyle().set("border", "0.2px solid #004e06");
		confirmButton.getElement().getStyle().set("background-color", "white");

		addewButton.getElement().getStyle().set("color", "#0aa8f7");
		addewButton.getElement().getStyle().set("border", "0.2px solid #0aa8f7");
		addewButton.getElement().getStyle().set("background-color", "white");

		errorVl.getElement().getStyle().set("background-color", "#f2f2f2");

		closeButton.getElement().getStyle().set("color", "black");
		closeButton.getElement().getStyle().set("border", "0.2px solid #000000");
		closeButton.getElement().getStyle().set("background-color", "white");

		setHeight("100%");
		setWidthFull();
		getStyle().set("overflow-y", "auto");

		setPadding(false);
		setMargin(false);
		setSpacing(false);
	}

	public abstract Class<?> getCloseNavigationClass();

	public abstract void initializeComponents();

	public abstract void setSelectedEntity(AbstractPojo pojo);

	public abstract void clearSelectedEntity(T pojo);

	public abstract boolean fullsize();

	public abstract String getPageTitle();

	public abstract String getPageMode();

	public void setEventEntityFields(T pojo) {
	}

	public abstract Binder<T> getBinder();

	public abstract T getEntity();

	protected boolean checkConditions(T pojo) {
		return true;
	}

	public void setPageMode(String pageMode) {
		this.pageMode = pageMode;
	}

	public void setBinder(Binder<T> binder) {
		this.binder = binder;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public T setOtherFields(T entity) {
		return entity;
	}

	public void updateFieldsOnSave() {

	}

	public abstract void saveRecord(T entity);
}

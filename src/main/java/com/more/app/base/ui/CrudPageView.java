package com.more.app.base.ui;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLayout;

public class CrudPageView extends VerticalLayout implements RouterLayout, BeforeEnterObserver
{
	private static final long serialVersionUID = 7344322144679571933L;

	public CrudPageView()
	{
		setHeight("100%");
		setWidth(null);
		getStyle().set("overflow-y", "auto");
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event)
	{
		//if (null == CurrentUser.get())
		{
		//	SessionHandler.setUser(null);
		//	event.rerouteTo(LoginScreen.class);
		}
	}
}

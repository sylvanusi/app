package com.more.app.base.ui;

import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;

public class BaseLayout extends VerticalLayout implements RouterLayout{
	
	public BaseLayout()
	{
		setAlignItems(FlexComponent.Alignment.CENTER);
		setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
		setAlignSelf(Alignment.CENTER);
	}

}

package com.more.app.base.ui;

import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;

public class LeftAlignedLayout extends VerticalLayout implements RouterLayout {

	public LeftAlignedLayout() {
		setAlignItems(FlexComponent.Alignment.START);
		setJustifyContentMode(FlexComponent.JustifyContentMode.START);
		setAlignSelf(Alignment.START);
		//setHeight("100%");
		//setWidthFull();
		//getStyle().set("overflow-y", "auto");
	}

}

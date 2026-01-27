package com.more.app.base.ui.product;

import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.details.DetailsVariant;

public class CustomAccordionPanel extends AccordionPanel
{
	private static final long serialVersionUID = -6011469327980021174L;

	public CustomAccordionPanel(String label)
	{
		super();
		setSummaryText(label);
		addThemeVariants(DetailsVariant.FILLED);
		setSummaryText(label);
		setOpened(true);
		getElement().getStyle().set("width", "1000px");
	}
}

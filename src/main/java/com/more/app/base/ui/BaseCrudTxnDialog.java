package com.more.app.base.ui;

import com.more.app.entity.AbstractPojo;

public abstract class BaseCrudTxnDialog<T extends AbstractPojo> extends BaseCrudComponent
{

	public BaseCrudTxnDialog()
	{
		super();
		// lumo-space-m is the margin of the dialog to the viewport
		setHeight("calc(100vh - (2*var(--lumo-space-m)))");
		setWidth("calc(100vw - (4*var(--lumo-space-m)))");
	}

}

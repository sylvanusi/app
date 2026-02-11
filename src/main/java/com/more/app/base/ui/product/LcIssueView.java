package com.more.app.base.ui.product;

import com.more.app.base.ui.BaseLayout;
import com.more.app.entity.product.ImportLetterOfCreditIssue;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "lc-issue", layout = BaseLayout.class)
public class LcIssueView extends VerticalLayout
{

	Grid<ImportLetterOfCreditIssue> grid = new Grid<ImportLetterOfCreditIssue>();
	HorizontalLayout hl = new HorizontalLayout();
	Button add = new Button("Add");
	Button edit = new Button("Edit");
	Button view = new Button("View");

	public LcIssueView()
	{
		grid.setSelectionMode(SelectionMode.SINGLE);

		grid.addColumn(ImportLetterOfCreditIssue::getId).setHeader("Id");
		grid.addColumn(ImportLetterOfCreditIssue::getReceiverBic).setHeader("Receiver Bic");
		grid.addColumn(ImportLetterOfCreditIssue::getDocumentaryCrNo).setHeader("LC Reference");
		grid.addColumn(ImportLetterOfCreditIssue::getCurrencyCode).setHeader("CCY");
		grid.addColumn(ImportLetterOfCreditIssue::getAmountOfDocumentaryCredit).setHeader("Amount");
		grid.addColumn(ImportLetterOfCreditIssue::getDateOfIssue).setHeader("Issue Date");

		hl.add(add, edit, view);

		add(hl, grid);

		setSizeFull();
		//grid.setItems(FacadeFactory.getFacade().list(ImportLetterOfCreditIssue.class));

		add.addClickListener(new ComponentEventListener<ClickEvent<Button>>()
		{

			@Override
			public void onComponentEvent(ClickEvent<Button> event)
			{
				LcIssueCrudView dg = new LcIssueCrudView(new ImportLetterOfCreditIssue());
				dg.open();

			}

		});

		edit.addClickListener(new ComponentEventListener<ClickEvent<Button>>()
		{

			@Override
			public void onComponentEvent(ClickEvent<Button> event)
			{
				if (grid.getSelectedItems().isEmpty())
					return;
				Object[] arr = grid.getSelectedItems().toArray();
				LcIssueCrudView dg = new LcIssueCrudView((ImportLetterOfCreditIssue) arr[0]);
				dg.open();

			}

		});

	}

}

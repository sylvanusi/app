package com.more.app.base.ui.product;


import com.more.app.base.ui.BaseView;
import com.more.app.base.ui.LeftAlignedLayout;
import com.more.app.entity.product.ImportLetterOfCreditPreAdvise;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.router.Route;

@Route(value = "pre-advise", layout = LeftAlignedLayout.class)
public class LcPreAdviceView  extends BaseView<ImportLetterOfCreditPreAdvise>
{


	public static final String pageUrlString = "LC PreAdvise";
	// change this resource value
	public static final Long resourceAdd = Long.valueOf(55L);
	public static final Long resourceEdit = Long.valueOf(56L);
	public static final Long resourceView = Long.valueOf(57L);


	public LcPreAdviceView()
	{
		


	}
	
	@Override
	public void loadComponents()
	{
		grid.setSelectionMode(SelectionMode.SINGLE);

		grid.addColumn(ImportLetterOfCreditPreAdvise::getId).setHeader("Id");
		grid.addColumn(ImportLetterOfCreditPreAdvise::getReceiverBic).setHeader("Receiver Bic");
		grid.addColumn(ImportLetterOfCreditPreAdvise::getDocumentaryCrNo).setHeader("LC Reference");
		grid.addColumn(ImportLetterOfCreditPreAdvise::getCurrencyCode).setHeader("CCY");
		grid.addColumn(ImportLetterOfCreditPreAdvise::getAmountOfDocumentaryCredit).setHeader("Amount");

		addBaseComponentsandStyle();
	}

	public void reloadView()
	{
		//grid.setItems(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
		//grid.setItems(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
	}

	@Override
	public boolean getAddPermission()
	{
		return true;
	}

	@Override
	public boolean getEditPermission()
	{
		return true;
	}

	@Override
	public boolean getViewPermission()
	{
		return true;
	}

	@Override
	public void navigationAction(String param)
	{
		getUI().ifPresent(ui -> ui.navigate(LcPreAdviceCrudView_N.class, param));
	}

	@Override
	public String getDialogTitle()
	{
		return "LC PreAdvise";
	}
}

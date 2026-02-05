package com.more.app.base.ui.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.base.ui.BaseLayout;
import com.more.app.base.ui.BaseView;
import com.more.app.base.ui.DialogSelectEntity;
import com.more.app.entity.product.LcMaster;
import com.more.app.repository.product.LcMasterRepository;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.router.Route;

@Route(value = "lcView", layout = BaseLayout.class)
public class LcMasterView extends BaseView<LcMaster>
{
	@Autowired
	private LcMasterRepository repository;
	public LcMasterView()
	{
		super();
		this.view = this;
	}

	public LcMasterView(DialogSelectEntity dialogSelectEntity, Dialog dg)
	{
		super(dialogSelectEntity, dg);
	}
	public <T> LcMasterView(DialogSelectEntity dialog, Dialog dg, JpaRepository repository)
	{
		super(dialog, dg, repository);
		this.repository = (LcMasterRepository) repository;
	}

	@Override
	public void loadComponents() {
		grid.addColumn(LcMaster::getInternalReferenceNo).setHeader("Internal Reference");
		grid.addColumn(LcMaster::getLcReferenceNo).setHeader("LC Reference");		
		grid.addColumn(LcMaster::getApplicantName).setHeader("Applicant");
		grid.addColumn(LcMaster::getBeneficiaryName).setHeader("Beneficiary");
		grid.addColumn(LcMaster::getLcCcy).setHeader("Currency");
		grid.addColumn(LcMaster::getLcAmount).setHeader("Amount");
		grid.addColumn(LcMaster::getIssueDate).setHeader("Issue Date");
		grid.addColumn(LcMaster::getLcEvent).setHeader("Current Event");
		grid.addColumn(LcMaster::getEventStatus).setHeader("Current Event Status");
		grid.addColumn(LcMaster::getLcStatus).setHeader("LC Status");
		
		addBaseComponentsandStyle();
	}

	@Override
	public void reloadView() {
		grid.setItems(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
		
	}
	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
		grid.setItems(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
	}

	@Override
	public boolean getAddPermission() {
		return true;
	}

	@Override
	public boolean getEditPermission() {
		return true;
	}

	@Override
	public boolean getViewPermission() {
		return true;
	}

	@Override
	public void navigationAction(String param) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDialogTitle() {
		return "LC Master";
	}
}

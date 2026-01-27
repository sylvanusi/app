package com.more.app.base.ui.product;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.base.ui.BaseView;
import com.more.app.base.ui.DialogSelectEntity;
import com.more.app.base.ui.LeftAlignedLayout;
import com.more.app.entity.product.Register;
import com.more.app.repository.product.RegisterRepository;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.router.Route;

@Route(value = RegisterView.pageUrlString, layout = LeftAlignedLayout.class)
public class RegisterView extends BaseView<Register>
{
	private static final long serialVersionUID = 4096148626512431660L;
	public static final String pageUrlString = "Register";
	// change this resource value
	public static final Long resourceAdd = Long.valueOf(55L);
	public static final Long resourceEdit = Long.valueOf(56L);
	public static final Long resourceView = Long.valueOf(57L);
	private NumberFormat numberFormat = new DecimalFormat("#,###.00");

	@Autowired
	private RegisterRepository repository;

	public RegisterView()
	{
		super();
		view = this;
	}

	public RegisterView(DialogSelectEntity entity, Dialog dg)
	{
		super(entity, dg);
	}

	public <T> RegisterView(DialogSelectEntity dialog, Dialog dg, JpaRepository repository)
	{
		super(dialog, dg, repository);
		this.repository = (RegisterRepository) repository;
	}
	
	@Override
	public void loadComponents()
	{
		grid.addColumn(Register :: getRegistrationDate).setHeader("Registration Date");
		grid.addColumn(y -> (y.getTransactionReference() == null) ? y.getInternalReference() : y.getTransactionReference()).setHeader("Reference");
		grid.addColumn(Register :: getProductType).setHeader("Product Type");
		grid.addColumn(Register :: getProductName).setHeader("Product");
		grid.addColumn(k -> (k.getTransactionCcy() == null ? "" : k.getTransactionCcy()) + " "
				+ (k.getTransactionAmount() == null ? "" : numberFormat.format(k.getTransactionAmount())))
				.setHeader("Amount");
		grid.addColumn(Register :: getApplicantName).setHeader("Applicant");
		grid.addColumn(Register :: getCurrentQueueName).setHeader("Current Queue");
		grid.addColumn(Register::getWorkflowStatus).setHeader("WorkFlow Status");
		//grid.addColumn(Register :: getNextQueueName).setHeader("Next Queue");
		grid.addColumn(k -> k.getTransactionStatus().getStatus()).setHeader("Transaction Status");

		addBaseComponentsandStyle();
	}

	public void reloadView()
	{
		grid.setItems(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
		grid.setItems(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
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
		getUI().ifPresent(ui -> ui.navigate(RegisterCrudView.class, param));
	}

	@Override
	public String getDialogTitle()
	{
		return "Search Product Register";
	}
}

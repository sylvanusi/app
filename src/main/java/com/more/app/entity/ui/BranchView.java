package com.more.app.entity.ui;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.base.ui.BaseView;
import com.more.app.base.ui.DialogSelectEntity;
import com.more.app.base.ui.LeftAlignedLayout;
import com.more.app.entity.Branch;
import com.more.app.repository.BranchRepository;
import com.more.app.repository.CurrencyRepository;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = BranchView.pageUrlString, layout = LeftAlignedLayout.class)
public class BranchView extends BaseView<Branch>
{
	private static final long serialVersionUID = -4351157630319048831L;
	public static final String pageUrlString = "branch-view";
	public static final Long resourceAdd = Long.valueOf(22L);
	public static final Long resourceEdit = Long.valueOf(23L);
	public static final Long resourceView = Long.valueOf(24L);

	private TextField codeTF;
	private TextField branchNoTF;
	private TextField branchNameTF;
	private TextField cityTF;
	
	@Autowired
	private BranchRepository branchRepo;

	public BranchView()
	{
		super();
		this.view = this;
	}

	public BranchView(DialogSelectEntity dialogSelectEntity, Dialog dg)
	{
		super(dialogSelectEntity, dg);
	}
	public <T> BranchView(DialogSelectEntity dialog, Dialog dg, JpaRepository repository)
	{
		super(dialog, dg, repository);
		this.branchRepo = (BranchRepository) repository;
	}
	
	public void loadComponents()
	{
		codeTF = new TextField("Code");
		codeTF.setMaxLength(10);

		branchNoTF = new TextField("Branch No");
		branchNoTF.setMaxLength(3);

		branchNameTF = new TextField("Branch Name");
		branchNameTF.setMaxLength(35);

		cityTF = new TextField("Branch Name");
		cityTF.setMaxLength(35);

		grid.addColumn(Branch::getCode).setHeader("Code");
		grid.addColumn(Branch::getBranchNo).setHeader("Branch No");
		grid.addColumn(Branch::getName).setHeader("Branch Name");
		grid.addColumn(Branch::getBankName).setHeader("Bank Name");
		grid.addColumn(Branch::getCity).setHeader("City");

		hlsearch.add(codeTF, branchNoTF, branchNameTF, searchb);
		hlsearch.setVerticalComponentAlignment(Alignment.BASELINE, codeTF, branchNoTF, branchNameTF, searchb);

		addBaseComponentsandStyle();

		searchb.addClickListener(event ->
		{
			grid.setItems(new ArrayList<Branch>());
			String code = codeTF.getValue();
			String branchNo = branchNoTF.getValue();
			String branchName = branchNameTF.getValue();
			String city = cityTF.getValue();
			grid.setItems(branchRepo.findByCodeStartsWithAndNameStartsWithAndBranchNoStartsWithAndCityStartsWith(code, branchName, branchNo, city));
		});
	}

	public void reloadView()
	{
		grid.setItems(branchRepo.findAll(Sort.by(Sort.Direction.DESC, "id")));
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
		grid.setItems(branchRepo.findAll(Sort.by(Sort.Direction.DESC, "id")));
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
		getUI().ifPresent(ui -> ui.navigate(BranchCrudView.class, param));
	}

	@Override
	public String getDialogTitle()
	{
		return "Search Branch";
	}
}

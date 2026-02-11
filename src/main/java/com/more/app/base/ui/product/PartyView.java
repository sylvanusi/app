package com.more.app.base.ui.product;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.base.ui.BaseView;
import com.more.app.base.ui.DialogSelectEntity;
import com.more.app.base.ui.LeftAlignedLayout;
import com.more.app.base.ui.setup.BankCrudView;
import com.more.app.entity.Bank;
import com.more.app.entity.product.Party;
import com.more.app.repository.product.PartyRepository;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "partyvw", layout = LeftAlignedLayout.class)
public class PartyView  extends BaseView<Party> {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private PartyRepository repository;
	
	private TextField bicCodeTF;
	private TextField nameTF;
	private TextField accountTF;
	
	public PartyView()
	{
		super();
		view = this;
	}
	
	public PartyView(DialogSelectEntity dialogSelectEntity, Dialog dg)
	{
		super(dialogSelectEntity, dg);
	}
	
	public <T> PartyView(Dialog dg, JpaRepository repository)
	{
		super(dg, repository);
		this.repository = (PartyRepository) repository;
	}
	public <T> PartyView(DialogSelectEntity dialog, Dialog dg, JpaRepository repository)
	{
		super(dialog, dg, repository);
		this.repository = (PartyRepository) repository;
	}

	@Override
	public void loadComponents() {
		
		bicCodeTF = new TextField("Bic Code");
		nameTF = new TextField("Name");
		accountTF = new TextField("Account");

		grid.addColumn(Party::getSwiftPartyType).setHeader("Type");
		grid.addColumn(Party::getName).setHeader("Name");
		grid.addColumn(Party::getBicCode).setHeader("BIC");
		grid.addColumn(Party::getAccount).setHeader("Account");
		grid.addColumn(Party::getLocation).setHeader("Location");
		
		hlsearch.add(bicCodeTF, nameTF, accountTF, searchb);
		hlsearch.setVerticalComponentAlignment(Alignment.BASELINE, bicCodeTF, nameTF, accountTF, searchb);

		//grid.asSingleSelect().addValueChangeListener(event -> openForm(event.getValue()));
		addBaseComponentsandStyle();
		
		searchb.addClickListener(e -> {
			grid.setItems(new ArrayList<Party>());			
		    grid.setItems(repository.findByBicCodeStartsWithAndNameStartsWithAndAccountStartsWith(bicCodeTF.getValue(), nameTF.getValue(), accountTF.getValue()));
		});
	}

	@Override
	public void reloadView() {
		System.out.println("Reloading Party View");
		grid.setItems(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
	}
	
	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
		System.out.println("Attaching Party View");
		
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
		getUI().ifPresent(ui -> ui.navigate(PartyCrudView.class, param));
	}

	@Override
	public String getDialogTitle() {
		return "Search Party";
	}

	
}

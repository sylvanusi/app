package com.more.app.base.ui.product;

import org.tinylog.Logger;

import com.more.app.base.ui.BaseCrudComponent;
import com.more.app.base.ui.CrudPageView;
import com.more.app.entity.AbstractPojo;
import com.more.app.entity.product.ImportLetterOfCreditPreAdvise;
import com.more.app.repository.product.ImportLetterOfCreditPreAdviseRepository;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = "preadvise_crud", layout = CrudPageView.class)
public class LcPreAdviceCrudView extends BaseCrudComponent<ImportLetterOfCreditPreAdvise>
		implements HasUrlParameter<String> {
	private ImportLetterOfCreditPreAdviseRepository repository;

	private ImportLetterOfCreditPreAdvise entity;
	private Binder<ImportLetterOfCreditPreAdvise> binder = new Binder<>(ImportLetterOfCreditPreAdvise.class);

	public LcPreAdviceCrudView() {
		super();
		
	}

	private TextField documentaryCrNoTF, placeOfExpiryTF;
	private ComboBox<String> formOfDocumentaryCrTF;
	private DatePicker dateOfExpiryDF;



	@Override
	public void setParameter(BeforeEvent beforeEvent, String parameter) {
		Logger.info("Setting parameter for LcPreAdviceCrudView_N: " + parameter);
		if (parameter != null && !parameter.isEmpty()) {
			String params[] = parameter.split(",");
			pageMode = params[0];

			if ("ADD".equals(pageMode))
				entity = new ImportLetterOfCreditPreAdvise();
			else {
				Long id = Long.valueOf(params[1]);
				if (id == -1L)
					beforeEvent.rerouteTo(getCloseNavigationClass());
				else {
					entity = new ImportLetterOfCreditPreAdvise();
				}
			}
		} else
			getUI().get().navigate(getCloseNavigationClass());
	}

	@Override
	public Class<LcMasterView> getCloseNavigationClass() {
		Logger.info("Getting close navigation class for LcPreAdviceCrudView_N");
		return LcMasterView.class;
	}

	
	public void initializeComponents() {
		formOfDocumentaryCrTF = new ComboBox<String>();
		formOfDocumentaryCrTF.setItems("IRREVOCABLE", "IRREVOCABLE TRANSFERABLE");
		formOfDocumentaryCrTF.setClearButtonVisible(true);

		documentaryCrNoTF = new TextField();
		documentaryCrNoTF.setMaxLength(16);
		
		dateOfExpiryDF = new DatePicker();
		
		placeOfExpiryTF = new TextField();
		placeOfExpiryTF.setMaxLength(29);
		
		
		
		vl.add(formOfDocumentaryCrTF, documentaryCrNoTF, dateOfExpiryDF, placeOfExpiryTF);

		
	}

	@Override
	public void setSelectedEntity(AbstractPojo pojo) {
		// TODO Auto-generated method stub
	}


	@Override
	public boolean fullsize() {
		return true;
	}

	@Override
	public String getPageTitle() {
		return "LC Pre Advise";
	}

	@Override
	public String getPageMode() {
		return pageMode;
	}

	@Override
	public Binder<ImportLetterOfCreditPreAdvise> getBinder() {
		return binder;
	}

	@Override
	public ImportLetterOfCreditPreAdvise getEntity() {
		return entity;
	}

	@Override
	public void saveRecord(ImportLetterOfCreditPreAdvise entity) {
		repository.save(entity);
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {

		Logger.info("Attaching LcPreAdviceCrudView_N");
		super.onAttach(attachEvent);
		binder.setBean(entity);
	}

	@Override
	public void clearSelectedEntity(ImportLetterOfCreditPreAdvise pojo) {
		// TODO Auto-generated method stub
		
	}
}

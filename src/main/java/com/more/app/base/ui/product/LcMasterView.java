package com.more.app.base.ui.product;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.tinylog.Logger;

import com.more.app.base.ui.BaseLayout;
import com.more.app.base.ui.BaseView;
import com.more.app.base.ui.DialogSelectEntity;
import com.more.app.entity.product.ImportLetterOfCreditPreAdvise;
import com.more.app.entity.product.LcMaster;
import com.more.app.entity.product.Party;
import com.more.app.entity.product.dto.LcMasterSearchDTO;
import com.more.app.repository.product.ImportLetterOfCreditPreAdviseRepository;
import com.more.app.repository.product.LcMasterRepository;
import com.more.app.repository.product.PartyRepository;
import com.more.app.specification.LcMasterSpecification;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import ch.carnet.kasparscherrer.EmptyFormLayoutItem;

@Route(value = "lcView", layout = BaseLayout.class)
public class LcMasterView extends BaseView<LcMaster> {

	@Autowired
	private LcMasterRepository repository;
	@Autowired
	private PartyRepository partyRepository;
	@Autowired
	private ImportLetterOfCreditPreAdviseRepository preAdviseRepository;

	public LcMasterView() {
		super();
		this.view = this;
	}

	public LcMasterView(DialogSelectEntity dialogSelectEntity, Dialog dg) {
		super(dialogSelectEntity, dg);
	}

	public <T> LcMasterView(DialogSelectEntity dialog, Dialog dg, JpaRepository repository) {
		super(dialog, dg, repository);
		this.repository = (LcMasterRepository) repository;
	}

	private TextField applicantTF;
	private TextField beneficiaryTF;

	private TextField lcReferenceNoTF;
	private ComboBox<String> lcEventTF;

	private Button searchApplicant;
	private Button searchBeneficiary;

	private Long applicantId, beneficiaryId;

	private DatePicker fromCreateDatePicker;
	private DatePicker toCreateDatePicker;

	private HorizontalLayout applicantHl, beneficiaryHl, eventButtonHl;

	private Button preAdviseBtn, issueBtn, amendmentBtn, adjustmentBtn, paymentBtn;

	private NumberFormat numberFormat = new DecimalFormat("#,###.00");

	public void loadComponents() {
		Logger.info("Loading LC master search components");
		fromCreateDatePicker = new DatePicker("From Registration Date");
		fromCreateDatePicker.setLocale(Locale.US);
		fromCreateDatePicker.setMax(LocalDate.now());
		// fromRegDatePicker.setWidth("25%");

		toCreateDatePicker = new DatePicker("To Registration Date");
		toCreateDatePicker.setLocale(Locale.US);
		toCreateDatePicker.setMax(LocalDate.now());

		applicantHl = new HorizontalLayout();
		// applicantHl.setSpacing(true);
		beneficiaryHl = new HorizontalLayout();
		// beneficiaryHl.setSpacing(true);

		searchApplicant = new Button(new Icon(VaadinIcon.SEARCH));
		searchBeneficiary = new Button(new Icon(VaadinIcon.SEARCH));

		applicantTF = new TextField("Applicant");
		applicantTF.setMaxLength(35);
		applicantTF.setClearButtonVisible(true);
		applicantTF.setReadOnly(true);
		applicantTF.setWidth("250px");
		applicantHl.add(applicantTF, searchApplicant);

		beneficiaryTF = new TextField("Beneficiary");
		beneficiaryTF.setMaxLength(35);
		beneficiaryTF.setClearButtonVisible(true);
		beneficiaryTF.setReadOnly(true);
		beneficiaryTF.setWidth("250px");
		beneficiaryHl.add(beneficiaryTF, searchBeneficiary);

		lcReferenceNoTF = new TextField("Reference No");
		lcReferenceNoTF.setMaxLength(16);

		lcEventTF = new ComboBox<String>();
		lcEventTF.setLabel("Current Event");
		lcEventTF.setClearButtonVisible(true);
		// Registered, PreAdvise, Issue, Amendment, Adjustment, Payment
		lcEventTF.setItems("REGISTERED", "PREADVISED", "ISSUE", "AMENDMENT", "ADJUSTMENT", "PAYMENT");

		preAdviseBtn = new Button("Pre Advise LC");
		issueBtn = new Button("Issue LC");
		amendmentBtn = new Button("Amend LC");
		adjustmentBtn = new Button("Adjust LC");
		paymentBtn = new Button("LC Payment");

		// preAdviseBtn.setEnabled(false);
		preAdviseBtn.setEnabled(false);
		issueBtn.setEnabled(false);
		amendmentBtn.setEnabled(false);
		adjustmentBtn.setEnabled(false);
		paymentBtn.setEnabled(false);

		hlevent.add(preAdviseBtn, issueBtn, amendmentBtn, adjustmentBtn, paymentBtn);

		grid.addColumn(LcMaster::getInternalReferenceNo).setHeader("Internal Reference");
		grid.addColumn(LcMaster::getLcReferenceNo).setHeader("LC Reference");
		grid.addColumn(LcMaster::getApplicantName).setHeader("Applicant");
		grid.addColumn(LcMaster::getBeneficiaryName).setHeader("Beneficiary");
		// grid.addColumn(LcMaster::getLcCcy).setHeader("Currency");
		grid.addColumn(k -> (k.getLcCcy() == null ? "" : k.getLcCcy()) + " "
				+ (k.getLcAmount() == null ? "" : numberFormat.format(k.getLcAmount()))).setHeader("Amount");
		grid.addColumn(LcMaster::getIssueDate).setHeader("Issue Date");
		grid.addColumn(LcMaster::getLcEvent).setHeader("Current Event");
		grid.addColumn(LcMaster::getEventStatus).setHeader("Current Event Status");
		grid.addColumn(LcMaster::getLcStatus).setHeader("LC Status");
		grid.addColumn(LcMaster::getSystemCreateDate).setHeader("Create Date");

		grid.setHeight(100, Unit.PIXELS);

		addBaseComponentsandStyle();
		hlevent.setVisible(true);

		// FormLayout fl = new FormLayout();
		// fl.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 6));
		HorizontalLayout fl = new HorizontalLayout();
		// fl.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 6));
		fl.add(fromCreateDatePicker, toCreateDatePicker, applicantHl, beneficiaryHl, lcReferenceNoTF, lcEventTF);

		VerticalLayout content = new VerticalLayout();
		content.setSpacing(true);
		content.setMargin(true);

		content.add(fl, new EmptyFormLayoutItem(), searchb);

		searchb.getElement().getStyle().set("background-color", "#d7d8d6");

		Details details = new Details("Search Criteria", content);
		details.setOpened(true);

		hlsearch.add(details);

		searchApplicant.addClickListener(event -> {
			applicantId = null;

			Dialog dg = new Dialog();
			PartyView cview = new PartyView(view, dg, partyRepository);
			cview.getHl().setVisible(false);
			cview.select.setVisible(false);

			Button selectItem = new Button("Select Item");
			selectItem.setSizeFull();
			selectItem.addClickListener(evt -> {
				if (!cview.grid.getSelectedItems().isEmpty()) {
					Object[] arr = cview.grid.getSelectedItems().toArray();
					Party party = (Party) arr[0];
					applicantId = party.getId();
					applicantTF.setValue(party.getName());
				} else {
					applicantId = null;
					applicantTF.setValue("");
				}
			});
			cview.add(selectItem);
			dg.add(cview);
			dg.open();
		});

		searchBeneficiary.addClickListener(event -> {
			Logger.info("Search Beneficiary button clicked");
			beneficiaryId = null;

			Dialog dg = new Dialog();
			PartyView cview = new PartyView(view, dg, partyRepository);
			cview.getHl().setVisible(false);
			cview.select.setVisible(false);
			Button selectItem = new Button("Select Item");
			selectItem.setSizeFull();
			selectItem.addClickListener(evt -> {
				Logger.info("Select Item button clicked in Beneficiary search dialog");
				if (!cview.grid.getSelectedItems().isEmpty()) {
					Logger.info("Beneficiary selected from grid");
					Object[] arr = cview.grid.getSelectedItems().toArray();
					Party party = (Party) arr[0];
					Logger.info("Selected Beneficiary - ID: {}, Name: {}", party.getId(), party.getName());
					beneficiaryId = party.getId();
					beneficiaryTF.setValue(party.getName());
					Logger.info("Beneficiary search completed successfully, Beneficiary ID: {}, Beneficiary Name: {}",
							beneficiaryId, beneficiaryTF.getValue());
				} else {
					Logger.info("No Beneficiary selected, clearing Beneficiary ID and TextField");
					beneficiaryId = null;
					beneficiaryTF.setValue("");
				}
			});
			cview.add(selectItem);
			dg.add(cview);
			dg.open();
		});

		searchb.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				Logger.info(
						"Search button clicked, executing search with criteria - From Date: {}, To Date: {}, Applicant ID: {}, Beneficiary ID: {}, LC Reference No: {}, LC Event: {}",
						fromCreateDatePicker.getValue(), toCreateDatePicker.getValue(), applicantId, beneficiaryId,
						lcReferenceNoTF.getValue(), lcEventTF.getValue());
				grid.setItems(new ArrayList<LcMaster>());
				LocalDate fromDate = fromCreateDatePicker.getValue();
				LocalDate toDate = toCreateDatePicker.getValue();

				if (fromDate == null || toDate == null || fromDate.isAfter(toDate)
						|| fromDate.plusYears(1).isBefore(toDate)) {
					Logger.error("Invalid date input, From Date: {}, To Date: {}", fromDate, toDate);
					Notification.show(
							"From /To Registration Date cannot be empty, From Registration Date must be before To Registration Date, Date Difference cannot be greater than 1 year",
							2000, Position.MIDDLE);
					return;
				}

				LcMasterSearchDTO dto = new LcMasterSearchDTO();

				dto.setFromCreateDate(fromDate);
				dto.setToCreateDate(toDate);

				if (beneficiaryId != null)
					dto.setBeneficiaryPartyId(beneficiaryId);

				if (applicantId != null)
					dto.setApplicantPartyId(applicantId);

				String lcReferenceNo = lcReferenceNoTF.getValue();
				if (lcReferenceNo != null && !lcReferenceNo.isEmpty())
					dto.setLcReferenceNo(lcReferenceNo);
				String lcEvent = lcEventTF.getValue();
				if (lcEvent != null && !lcEvent.isEmpty())
					dto.setLcEvent(lcEvent);

				Logger.info(
						"Executing search with criteria - From Date: {}, To Date: {}, Applicant ID: {}, Beneficiary ID: {}, LC Reference No: {}, LC Event: {}",
						dto.getFromCreateDate(), dto.getToCreateDate(), dto.getApplicantPartyId(),
						dto.getBeneficiaryPartyId(), dto.getLcReferenceNo(), dto.getLcEvent());

				repository.findAll(LcMasterSpecification.build(dto), Sort.by(Sort.Direction.DESC, "id"));
				grid.setItems(repository.findAll(LcMasterSpecification.build(dto), Sort.by(Sort.Direction.DESC, "id")));

				Logger.info("Search completed, number of records found: {}",
						grid.getDataProvider().size(new com.vaadin.flow.data.provider.Query<>()));
			}

		});

		grid.addSelectionListener(event -> {
			if (!grid.getSelectedItems().isEmpty()) {
				Object[] arr = grid.getSelectedItems().toArray();
				LcMaster lc = (LcMaster) arr[0];
				boolean eventStatusCompleted = !lc.getEventStatus().equals("COMPLETED");
				Logger.info("Event Status is Completed " + eventStatusCompleted);
				Logger.info("Lc Event " + lc.getLcEvent());
				if (lc.getLcEvent().equals("REGISTERED")) {
					preAdviseBtn.setEnabled(true);
					issueBtn.setEnabled(true);
					amendmentBtn.setEnabled(false);
					adjustmentBtn.setEnabled(false);
					paymentBtn.setEnabled(false);
				} else if (lc.getLcEvent().equals("PREADVISED")) {	
					preAdviseBtn.setEnabled(eventStatusCompleted);
					issueBtn.setEnabled(true);
					amendmentBtn.setEnabled(false);
					adjustmentBtn.setEnabled(false);
					paymentBtn.setEnabled(false);
				} else if (lc.getLcEvent().equals("ISSUE")) {
					preAdviseBtn.setEnabled(false);
					issueBtn.setEnabled(eventStatusCompleted);
					amendmentBtn.setEnabled(true);
					adjustmentBtn.setEnabled(true);
					paymentBtn.setEnabled(true);
				} else if (lc.getLcEvent().equals("AMENDMENT")) {
					preAdviseBtn.setEnabled(false);
					issueBtn.setEnabled(false);
					amendmentBtn.setEnabled(eventStatusCompleted);
					adjustmentBtn.setEnabled(true);
					paymentBtn.setEnabled(true);
				} else if (lc.getLcEvent().equals("ADJUSTMENT")) {
					preAdviseBtn.setEnabled(false);
					issueBtn.setEnabled(false);
					amendmentBtn.setEnabled(true);
					adjustmentBtn.setEnabled(!eventStatusCompleted);
					paymentBtn.setEnabled(true);
				} else if (lc.getLcEvent().equals("PAYMENT")) {
					preAdviseBtn.setEnabled(false);
					issueBtn.setEnabled(false);
					amendmentBtn.setEnabled(false);
					adjustmentBtn.setEnabled(false);
					paymentBtn.setEnabled(eventStatusCompleted);
				} else {
					preAdviseBtn.setEnabled(false);
					issueBtn.setEnabled(false);
					amendmentBtn.setEnabled(false);
					adjustmentBtn.setEnabled(false);
					paymentBtn.setEnabled(false);
				}
			}
		});

		beneficiaryHl.setVerticalComponentAlignment(Alignment.BASELINE, beneficiaryTF, searchBeneficiary);
		applicantHl.setVerticalComponentAlignment(Alignment.BASELINE, applicantTF, searchApplicant);

		preAdviseBtn.addClickListener(event -> getUI().ifPresent(ui -> {
			Logger.info("Pre Advise button clicked for LC Master ID: {}", getSelectedItemId());
			
			ImportLetterOfCreditPreAdvise preAdviseEntity = preAdviseRepository.findByLcMasterId(getSelectedItemId());
			
			if (preAdviseEntity != null) {
				Logger.info(
						"Existing Pre Advise found for LC Master ID: {}, navigating to LcPreAdviseForm with Pre Advise ID: {}",
						getSelectedItemId(), preAdviseEntity.getId());
				ui.navigate(LcPreAdviseForm.class, "PRE_ADVISE"+ "," + getSelectedItemId() + "," + preAdviseEntity.getId());
			} else {
				Logger.info(
						"No existing Pre Advise found for LC Master ID: {}, navigating to LcPreAdviseForm for new Pre Advise",
						getSelectedItemId());
				ui.navigate(LcPreAdviseForm.class, "PRE_ADVISE" + "," + getSelectedItemId());
			}
		}));
		
		issueBtn.addClickListener(evt -> {
			getUI().ifPresent(ui -> ui.navigate(ImportLetterOfCreditIssueForm.class, "ISSUE" + "," + getSelectedItemId()));
		});

		Logger.info("LC Master search components loaded successfully");
	}

	@Override
	public void reloadView() {
		// grid.setItems(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));

	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		// grid.setItems(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
	}

	@Override
	public boolean getAddPermission() {
		return false;
	}

	@Override
	public boolean getEditPermission() {
		return false;
	}

	@Override
	public boolean getViewPermission() {
		return false;
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

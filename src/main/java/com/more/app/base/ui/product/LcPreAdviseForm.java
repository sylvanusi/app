package com.more.app.base.ui.product;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.tinylog.Logger;

import com.more.app.base.ui.CrudPageView;
import com.more.app.base.ui.setup.CountryView;
import com.more.app.base.ui.setup.CustomerView;
import com.more.app.entity.Country;
import com.more.app.entity.Customer;
import com.more.app.entity.Product;
import com.more.app.entity.product.ImportLetterOfCreditPreAdvise;
import com.more.app.entity.product.LcMaster;
import com.more.app.entity.product.Party;
import com.more.app.entity.product.Register;
import com.more.app.repository.CountryRepository;
import com.more.app.repository.CustomerRepository;
import com.more.app.repository.product.ImportLetterOfCreditPreAdviseRepository;
import com.more.app.repository.product.LcMasterRepository;
import com.more.app.repository.product.PartyRepository;
import com.more.app.repository.product.RegisterRepository;
import com.more.app.repository.productsetup.ProductRepository;
import com.more.app.service.product.ImportLetterOfCreditPreAdviseService;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

import ch.carnet.kasparscherrer.EmptyFormLayoutItem;

@Route(value = "preadvise_form", layout = CrudPageView.class)
public class LcPreAdviseForm extends VerticalLayout implements HasUrlParameter<String> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private ImportLetterOfCreditPreAdviseRepository repository;
	@Autowired
	private LcMasterRepository lcMasterRepository;
	@Autowired
	private RegisterRepository registerRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ImportLetterOfCreditPreAdviseService preAdviseService;
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private PartyRepository partyRepository;
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private PartyCrudView partCrudView;

	private ImportLetterOfCreditPreAdvise entity;

	private Binder<ImportLetterOfCreditPreAdvise> binder = new Binder<>(ImportLetterOfCreditPreAdvise.class);

	private H5 title = new H5("Letter of Credit Pre-Advise Form");
	private Hr hrHeader = new Hr();
	private Hr hrFooter = new Hr();

	private HorizontalLayout hlheader = new HorizontalLayout();
	private HorizontalLayout hlFooter = new HorizontalLayout();
	public Button closeButton, validateButton, saveButton, submitButton, declineButton, cancelButton;

	private VerticalLayout bodyVl = new VerticalLayout();

	private TextField documentaryCrNoTF, placeOfExpiryTF;
	private ComboBox<String> formOfDocumentaryCrTF;
	private DatePicker dateOfExpiryDF, latestDateOfShipmentDF;
	private FormLayout formLayout = new FormLayout();
	private FormLayout formLayout1 = new FormLayout();
	private FormLayout formLayout2 = new FormLayout();
	private FormLayout formLayout3 = new FormLayout();

	private TextField applicantTF, beneficiaryTF, advisingBankTF, beneficiaryCountryTF, advThrBankNameTF,
			advThrBankAccNoTF, advthrBankBicTF;
	private TextField availableWithByBicTF, availableWithByNameTF, availableWithByAddress1TF, availableWithByAddress2TF,
			availableWithByAddress3TF;

	private NumberField amountNF, tolerancePlusNF, toleranceMinusNF;

	private ComboBox<String> currencyCB, availableWithByCodeCB;

	private Button searchApplicantPartyBtn, searchBeneficiaryPartyBtn, searchAvisingBankPartyBtn,
			searchBeneficiaryCountryBtn, advThrBankSearchBtn, availableWithByBicSearchBtn;

	private HorizontalLayout applicantHl, beneficiaryHl, advisingBankHl, beneficiaryCountryHl, amountHl, toleranceHl,
			advThrBankHl, availableWithByBICHl, availableWithByNameAddressHl, availableWithByNameAddressHl1;

	private TextArea additionalAmountsCoveredTA, dispatchFromTA, portOfLoadingTA, portOfDischargeTA,
			placeOfFinalDestinationTA, shipmentPeriodTA, goodDesriptionTA, narrativeTA, senderToReceiverInfoTA;

	private VerticalLayout availableWithByNameAddressVl;

	public LcPreAdviseForm() {
		Logger.info("Creating LcPreAdviseForm instance: constructor called");
		Logger.info("Initializing LcPreAdviseForm UI component");

		closeButton = new Button("Close", new Icon(VaadinIcon.CLOSE_CIRCLE_O));
		closeButton.addClickListener(event -> {
			removeAll();
			getUI().get().navigate(LcMasterView.class);
		});

		saveButton = new Button("Save", new Icon(VaadinIcon.CHECK_CIRCLE_O));
		submitButton = new Button("Submit", new Icon(VaadinIcon.CHECK));
		declineButton = new Button("Decline", new Icon(VaadinIcon.ARROW_CIRCLE_DOWN_O));
		cancelButton = new Button("Cancel", new Icon(VaadinIcon.MINUS_CIRCLE_O));
		validateButton = new Button("Validate", new Icon(VaadinIcon.CHECK_SQUARE_O));

		title.getElement().getStyle().set("font-weight", "bold");

		hlheader.setPadding(false);
		hlheader.setMargin(false);
		hlheader.setSpacing(true);
		hlheader.add(title);

		hrHeader.setWidthFull();
		hrFooter.setWidthFull();

		hlFooter.setSizeFull();
		hlFooter.add(validateButton, saveButton, submitButton, declineButton, cancelButton, closeButton);

		bodyVl.setSizeFull();
		bodyVl.setSpacing(true);

		formOfDocumentaryCrTF = new ComboBox<String>();
		formOfDocumentaryCrTF.setItems("IRREVOCABLE", "IRREVOCABLE TRANSFERABLE");
		formOfDocumentaryCrTF.setClearButtonVisible(true);
		formOfDocumentaryCrTF.setLabel("Form of Documentary Credit");
		formOfDocumentaryCrTF.setRequired(true);
		formOfDocumentaryCrTF.setRequiredIndicatorVisible(true);
		binder.forField(formOfDocumentaryCrTF).bind(ImportLetterOfCreditPreAdvise::getFormOfDocumentaryCr,
				ImportLetterOfCreditPreAdvise::setFormOfDocumentaryCr);

		documentaryCrNoTF = new TextField();
		documentaryCrNoTF.setMaxLength(16);
		documentaryCrNoTF.setLabel("Documentary Credit No.");
		documentaryCrNoTF.setRequired(true);
		documentaryCrNoTF.setRequiredIndicatorVisible(true);
		documentaryCrNoTF.setReadOnly(true);
		binder.forField(documentaryCrNoTF).bind(ImportLetterOfCreditPreAdvise::getDocumentaryCrNo,
				ImportLetterOfCreditPreAdvise::setDocumentaryCrNo);

		dateOfExpiryDF = new DatePicker();
		dateOfExpiryDF.setLabel("Date of Expiry");
		dateOfExpiryDF.setRequired(true);
		dateOfExpiryDF.setRequiredIndicatorVisible(true);
		binder.forField(dateOfExpiryDF).bind(ImportLetterOfCreditPreAdvise::getDateOfExpiry,
				ImportLetterOfCreditPreAdvise::setDateOfExpiry);

		placeOfExpiryTF = new TextField();
		placeOfExpiryTF.setMaxLength(29);
		placeOfExpiryTF.setLabel("Place of Expiry");
		placeOfExpiryTF.setRequired(true);
		placeOfExpiryTF.setRequiredIndicatorVisible(true);
		binder.forField(placeOfExpiryTF).bind(ImportLetterOfCreditPreAdvise::getPlaceOfExpiry,
				ImportLetterOfCreditPreAdvise::setPlaceOfExpiry);

		applicantHl = new HorizontalLayout();
		applicantTF = new TextField();
		applicantTF.setLabel("Applicant");
		applicantTF.setWidthFull();
		applicantTF.setRequired(true);
		applicantTF.setRequiredIndicatorVisible(true);
		applicantTF.setReadOnly(true);
		binder.forField(applicantTF).bind(ImportLetterOfCreditPreAdvise::getApplicantName,
				ImportLetterOfCreditPreAdvise::setApplicantName);
		searchApplicantPartyBtn = new Button(new Icon(VaadinIcon.SEARCH));
		applicantHl.add(applicantTF, searchApplicantPartyBtn);
		applicantHl.setSpacing(true);
		applicantHl.setVerticalComponentAlignment(Alignment.BASELINE, applicantTF, searchApplicantPartyBtn);
		applicantHl.setWidthFull();

		beneficiaryHl = new HorizontalLayout();
		beneficiaryTF = new TextField();
		beneficiaryTF.setLabel("Beneficiary");
		beneficiaryTF.setWidthFull();
		beneficiaryTF.setReadOnly(true);
		binder.forField(beneficiaryTF).bind(ImportLetterOfCreditPreAdvise::getBeneficiaryName,
				ImportLetterOfCreditPreAdvise::setBeneficiaryName);
		searchBeneficiaryPartyBtn = new Button(new Icon(VaadinIcon.SEARCH));
		beneficiaryHl.add(beneficiaryTF, searchBeneficiaryPartyBtn);
		beneficiaryHl.setSpacing(true);
		beneficiaryHl.setVerticalComponentAlignment(Alignment.BASELINE, beneficiaryTF, searchBeneficiaryPartyBtn);
		beneficiaryHl.setWidthFull();

		advisingBankHl = new HorizontalLayout();
		advisingBankTF = new TextField();
		advisingBankTF.setLabel("Advising Bank");
		advisingBankTF.setWidthFull();
		binder.forField(advisingBankTF).withValidator(SwiftValidator::bic, "Invalid BIC").bind(
				ImportLetterOfCreditPreAdvise::getAdvisingBankBicCode,
				ImportLetterOfCreditPreAdvise::setAdvisingBankBicCode);
		searchAvisingBankPartyBtn = new Button(new Icon(VaadinIcon.SEARCH));
		searchAvisingBankPartyBtn.addClickListener(event -> {
			Dialog dg = new Dialog();
			CustomerView view = new CustomerView(dg, customerRepository);
			view.customerTypeTF.setValue("Bank");
			view.customerTypeTF.setReadOnly(true);
			// .grid.setItems(custRepository.findByCustomerType("Beneficiary"));
			view.select.setVisible(false);
			Button selectItem = new Button("Select Item");
			selectItem.setSizeFull();
			selectItem.addClickListener(evt -> {
				if (!view.grid.getSelectedItems().isEmpty()) {
					Object[] arr = view.grid.getSelectedItems().toArray();
					Customer entity = (Customer) arr[0];
					advisingBankTF.setValue(entity.getSwiftAddress());
				} else
					advisingBankTF.setValue("");
			});
			view.add(selectItem);
			dg.add(view);
			dg.open();
			dg.addAttachListener(evt -> {
				view.updateGrid(view);
			});
		});
		advisingBankHl.add(advisingBankTF, searchAvisingBankPartyBtn);
		advisingBankHl.setSpacing(true);
		advisingBankHl.setVerticalComponentAlignment(Alignment.BASELINE, advisingBankTF, searchAvisingBankPartyBtn);
		advisingBankHl.setWidthFull();

		beneficiaryCountryHl = new HorizontalLayout();
		beneficiaryCountryTF = new TextField();
		beneficiaryCountryTF.setLabel("Beneficiary Country");

		binder.forField(beneficiaryCountryTF).bind(ImportLetterOfCreditPreAdvise::getBeneficiaryCountry,
				ImportLetterOfCreditPreAdvise::setBeneficiaryCountry);
		searchBeneficiaryCountryBtn = new Button(new Icon(VaadinIcon.SEARCH));
		searchBeneficiaryCountryBtn.addClickListener(event -> {
			Dialog dg = new Dialog();
			CountryView v = new CountryView(dg, countryRepository);
			v.select.setVisible(false);
			Button selectItem = new Button("Select Country");
			selectItem.setSizeFull();
			selectItem.addClickListener(evt -> {
				Logger.info("Select Item button clicked in Beneficiary search dialog");
				if (!v.grid.getSelectedItems().isEmpty()) {

					Object[] arr = v.grid.getSelectedItems().toArray();
					Country party = (Country) arr[0];

					beneficiaryCountryTF.setValue(party.getCode());

				} else {
					beneficiaryCountryTF.setValue("");
				}
			});
			v.add(selectItem);
			dg.add(v);
			dg.open();
		});

		beneficiaryCountryHl.add(beneficiaryCountryTF, searchBeneficiaryCountryBtn);
		beneficiaryCountryHl.setSpacing(true);
		beneficiaryCountryHl.setVerticalComponentAlignment(Alignment.BASELINE, beneficiaryCountryTF,
				searchBeneficiaryCountryBtn);
		beneficiaryCountryHl.setWidthFull();

		currencyCB = new ComboBox<String>();
		currencyCB.setLabel("Ccy");
		currencyCB.setRequired(true);
		currencyCB.setRequiredIndicatorVisible(true);

		amountNF = new NumberField();
		amountNF.setLabel("Amount");
		amountNF.setRequired(true);
		amountNF.setRequiredIndicatorVisible(true);
		binder.forField(amountNF).withNullRepresentation(0.0)
				.withConverter(doubleValue -> BigDecimal.valueOf(doubleValue),
						bigDecimal -> bigDecimal == null ? 0.0 : bigDecimal.doubleValue(), "Invalid amount")
				.withValidator(bd -> bd.scale() <= 3, "Max 3 decimal places allowed")
				.bind(ImportLetterOfCreditPreAdvise::getAmountOfDocumentaryCredit,
						ImportLetterOfCreditPreAdvise::setAmountOfDocumentaryCredit);

		amountHl = new HorizontalLayout();
		amountHl.add(currencyCB, amountNF);

		tolerancePlusNF = new NumberField();
		tolerancePlusNF.setLabel("Tolerance Plus (+)");
		binder.forField(tolerancePlusNF).bind(ImportLetterOfCreditPreAdvise::getTolerancePlus,
				ImportLetterOfCreditPreAdvise::setTolerancePlus);

		toleranceMinusNF = new NumberField();
		toleranceMinusNF.setLabel("Tolerance Minus (-)");
		binder.forField(toleranceMinusNF).withNullRepresentation(0.0).bind(
				ImportLetterOfCreditPreAdvise::getToleranceMinus, ImportLetterOfCreditPreAdvise::setToleranceMinus);
		toleranceHl = new HorizontalLayout();
		toleranceHl.add(toleranceMinusNF, tolerancePlusNF);

		additionalAmountsCoveredTA = new TextArea();
		additionalAmountsCoveredTA.setLabel("Additional Amounts Covered");
		additionalAmountsCoveredTA.setMaxRows(4);
		additionalAmountsCoveredTA.setMaxLength(140);
		binder.forField(additionalAmountsCoveredTA).bind(ImportLetterOfCreditPreAdvise::getAdditionalAmountsCovered,
				ImportLetterOfCreditPreAdvise::setAdditionalAmountsCovered);

		dispatchFromTA = new TextArea();
		dispatchFromTA.setLabel("Dispatch From");
		additionalAmountsCoveredTA.setMaxLength(140);
		binder.forField(dispatchFromTA).bind(ImportLetterOfCreditPreAdvise::getDispatchFrom,
				ImportLetterOfCreditPreAdvise::setDispatchFrom);

		portOfLoadingTA = new TextArea();
		portOfLoadingTA.setLabel("Port of Loading");
		portOfLoadingTA.setMaxLength(140);
		binder.forField(portOfLoadingTA).bind(ImportLetterOfCreditPreAdvise::getPortOfLoading,
				ImportLetterOfCreditPreAdvise::setPortOfLoading);

		portOfDischargeTA = new TextArea();
		portOfDischargeTA.setLabel("Port of Discharge");
		portOfDischargeTA.setMaxLength(140);
		binder.forField(portOfDischargeTA).bind(ImportLetterOfCreditPreAdvise::getPortOfDischarge,
				ImportLetterOfCreditPreAdvise::setPortOfDischarge);

		placeOfFinalDestinationTA = new TextArea();
		placeOfFinalDestinationTA.setLabel("Place of Final Destination");
		placeOfFinalDestinationTA.setMaxLength(140);
		binder.forField(placeOfFinalDestinationTA).bind(ImportLetterOfCreditPreAdvise::getPlaceOfFinalDestination,
				ImportLetterOfCreditPreAdvise::setPlaceOfFinalDestination);

		latestDateOfShipmentDF = new DatePicker();
		latestDateOfShipmentDF.setLabel("Latest Date of Shipment");
		binder.forField(latestDateOfShipmentDF).bind(ImportLetterOfCreditPreAdvise::getLatestDateOfShipment,
				ImportLetterOfCreditPreAdvise::setLatestDateOfShipment);

		shipmentPeriodTA = new TextArea();
		shipmentPeriodTA.setLabel("Shipment Period");
		shipmentPeriodTA.setMaxLength(390);
		shipmentPeriodTA.setMaxRows(6);
		binder.forField(shipmentPeriodTA).bind(ImportLetterOfCreditPreAdvise::getShipmentPeriod,
				ImportLetterOfCreditPreAdvise::setShipmentPeriod);

		goodDesriptionTA = new TextArea();
		goodDesriptionTA.setLabel("Goods Description");
		goodDesriptionTA.setMaxLength(390);
		goodDesriptionTA.setMaxRows(6);
		binder.forField(goodDesriptionTA).bind(ImportLetterOfCreditPreAdvise::getGoodsDescription,
				ImportLetterOfCreditPreAdvise::setGoodsDescription);

		narrativeTA = new TextArea();
		narrativeTA.setLabel("Narrative");
		narrativeTA.setMaxLength(390);
		narrativeTA.setMaxRows(6);
		binder.forField(narrativeTA).bind(ImportLetterOfCreditPreAdvise::getNarrative,
				ImportLetterOfCreditPreAdvise::setNarrative);

		senderToReceiverInfoTA = new TextArea();
		senderToReceiverInfoTA.setLabel("Sender to Receiver Information");
		senderToReceiverInfoTA.setMaxLength(390);
		senderToReceiverInfoTA.setMaxRows(6);
		binder.forField(senderToReceiverInfoTA).bind(ImportLetterOfCreditPreAdvise::getSenderToReceiverInfo,
				ImportLetterOfCreditPreAdvise::setSenderToReceiverInfo);

		advThrBankNameTF = new TextField();
		advThrBankNameTF.setLabel("Adv/Thr Bank Name");
		binder.forField(advThrBankNameTF).bind(ImportLetterOfCreditPreAdvise::getAdvThrBankName,
				ImportLetterOfCreditPreAdvise::setAdvThrBankName);

		advThrBankAccNoTF = new TextField();
		advThrBankAccNoTF.setLabel("Adv/Thr Bank Account No.");
		binder.forField(advThrBankAccNoTF).bind(ImportLetterOfCreditPreAdvise::getAdvThrBankAccountNo,
				ImportLetterOfCreditPreAdvise::setAdvThrBankAccountNo);

		advthrBankBicTF = new TextField();
		advthrBankBicTF.setLabel("Adv/Thr Bank BIC");
		binder.forField(advthrBankBicTF).bind(ImportLetterOfCreditPreAdvise::getAdvThrBankBic,
				ImportLetterOfCreditPreAdvise::setAdvThrBankBic);

		advThrBankSearchBtn = new Button(new Icon(VaadinIcon.SEARCH));
		advThrBankSearchBtn.addClickListener(ev -> {
			Dialog dg = new Dialog();
			partCrudView.closeButton.setVisible(false);
			partCrudView.setFromDialogue(true);

			Button closeBtn = new Button("Close", new Icon(VaadinIcon.CLOSE_CIRCLE_O));
			closeBtn.addClickListener(e -> {
				dg.close();
			});

			Button confirmBtn = partCrudView.confirmButton;
			confirmBtn.setVisible(true);
			confirmBtn.addClickListener(e -> {
				
				advthrBankBicTF.setValue(partCrudView.getEntity().getBicCode());
				advThrBankNameTF.setValue(partCrudView.getEntity().getName());
				advThrBankAccNoTF.setValue(partCrudView.getEntity().getAccount());
				binder.getBean().setAdvThrBankPartyId(partCrudView.getEntity().getId())	;	
			});

			dg.addDetachListener(e -> {
				partCrudView.hl.remove(confirmBtn, closeBtn);
			});
			
			dg.addAttachListener(e ->{
				if(entity.getAdvThrBankPartyId() != null)
				{
					Party party = partyRepository.findById(entity.getAdvThrBankPartyId()).orElse(null);
					partCrudView.getBinder().setBean(party);
				}
			});

			partCrudView.hl.add(confirmBtn, closeBtn);
			dg.add(partCrudView);

			dg.open();
		});

		advThrBankHl = new HorizontalLayout();
		advThrBankHl.add(advThrBankNameTF, advThrBankAccNoTF, advthrBankBicTF, advThrBankSearchBtn);

		advThrBankHl.setSpacing(true);
		advThrBankHl.setVerticalComponentAlignment(Alignment.BASELINE, advThrBankNameTF, advThrBankAccNoTF,
				advthrBankBicTF, advThrBankSearchBtn);
		advThrBankHl.setWidthFull();

		availableWithByCodeCB = new ComboBox<String>();
		availableWithByCodeCB.setLabel("Available With By Code");
		availableWithByCodeCB.setItems("BY ACCEPTANCE", "BY DEF PAYMENT", "BY MIXED PYMT", "BY NEGOTIATION",
				"BY PAYMENT");
		binder.forField(availableWithByCodeCB).bind(ImportLetterOfCreditPreAdvise::getAvailableWithByCode,
				ImportLetterOfCreditPreAdvise::setAvailableWithByCode);

		availableWithByBicTF = new TextField();
		availableWithByBicTF.setLabel("Available With By BIC");
		binder.forField(availableWithByBicTF).withValidator(SwiftValidator::bic, "Invalid BIC").bind(
				ImportLetterOfCreditPreAdvise::getAvailableWithByBic,
				ImportLetterOfCreditPreAdvise::setAvailableWithByBic);

		availableWithByBicSearchBtn = new Button(new Icon(VaadinIcon.SEARCH));
		availableWithByBicSearchBtn.addClickListener(event -> {
			Dialog dg = new Dialog();
			CustomerView view = new CustomerView(dg, customerRepository);
			view.customerTypeTF.setValue("Bank");
			view.customerTypeTF.setReadOnly(true);
			// .grid.setItems(custRepository.findByCustomerType("Beneficiary"));
			view.select.setVisible(false);
			Button selectItem = new Button("Select Item");
			selectItem.setSizeFull();
			selectItem.addClickListener(evt -> {
				if (!view.grid.getSelectedItems().isEmpty()) {
					Object[] arr = view.grid.getSelectedItems().toArray();
					Customer entity = (Customer) arr[0];
					availableWithByBicTF.setValue(entity.getSwiftAddress());
				} else
					availableWithByBicTF.setValue("");
			});
			view.add(selectItem);
			dg.add(view);
			dg.open();
			dg.addAttachListener(evt -> {
				view.updateGrid(view);
			});
		});

		availableWithByBICHl = new HorizontalLayout();
		availableWithByBICHl.add(availableWithByBicTF, availableWithByBicSearchBtn);
		availableWithByBICHl.setSpacing(true);
		availableWithByBICHl.setVerticalComponentAlignment(Alignment.BASELINE, availableWithByBicTF,
				availableWithByBicSearchBtn);

		availableWithByNameTF = new TextField();
		availableWithByNameTF.setLabel("Available With By Name");
		binder.forField(availableWithByNameTF).bind(ImportLetterOfCreditPreAdvise::getAvailableWithByName,
				ImportLetterOfCreditPreAdvise::setAvailableWithByName);
		availableWithByAddress1TF = new TextField();
		availableWithByAddress1TF.setLabel("Address 1");
		binder.forField(availableWithByAddress1TF).bind(ImportLetterOfCreditPreAdvise::getAvailableWithByAddress1,
				ImportLetterOfCreditPreAdvise::setAvailableWithByAddress1);
		availableWithByAddress2TF = new TextField();
		availableWithByAddress2TF.setLabel("Address 2");
		binder.forField(availableWithByAddress2TF).bind(ImportLetterOfCreditPreAdvise::getAvailableWithByAddress2,
				ImportLetterOfCreditPreAdvise::setAvailableWithByAddress2);
		availableWithByAddress3TF = new TextField();
		availableWithByAddress3TF.setLabel("Address 3");
		binder.forField(availableWithByAddress3TF).bind(ImportLetterOfCreditPreAdvise::getAvailableWithByAddress3,
				ImportLetterOfCreditPreAdvise::setAvailableWithByAddress3);

		availableWithByNameAddressHl = new HorizontalLayout();
		availableWithByNameAddressHl.setSizeFull();

		availableWithByNameAddressHl1 = new HorizontalLayout();
		availableWithByNameAddressHl1.setSizeFull();

		availableWithByNameAddressHl.add(availableWithByNameTF, availableWithByAddress1TF);
		availableWithByNameAddressHl1.add(availableWithByAddress2TF, availableWithByAddress3TF);

		availableWithByNameAddressVl = new VerticalLayout();
		availableWithByNameAddressVl.setSpacing(true);
		availableWithByNameAddressVl.setSizeFull();
		availableWithByNameAddressVl.add(availableWithByNameAddressHl, availableWithByNameAddressHl1);

		// formLayout.getElement().getStyle().set("overflow", "auto");
		formLayout.getElement().getStyle().set("padding-right", "var(--lumo-space-m)");
		formLayout1.getElement().getStyle().set("padding-right", "var(--lumo-space-m)");
		formLayout2.getElement().getStyle().set("padding-right", "var(--lumo-space-m)");
		formLayout3.getElement().getStyle().set("padding-right", "var(--lumo-space-m)");

		formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 4));
		formLayout1.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 4));
		formLayout2.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 4));
		formLayout3.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 4));

		H6 section1 = new H6("Documentary Credit Details");
		Hr section1Hr = new Hr();
		section1Hr.setWidthFull();
		H6 section2 = new H6("Parties to Documentary Credit");
		Hr section2Hr = new Hr();
		section2Hr.setWidthFull();
		H6 section3 = new H6("Amount Details");
		Hr section3Hr = new Hr();
		section3Hr.setWidthFull();
		H6 section4 = new H6("Available With By Details");
		Hr section4Hr = new Hr();
		section4Hr.setWidthFull();
		H6 section5 = new H6("Shipment Details");
		Hr section5Hr = new Hr();
		section5Hr.setWidthFull();

		bodyVl.add(section1);
		bodyVl.add(section1Hr);
		formLayout.add(formOfDocumentaryCrTF, documentaryCrNoTF, dateOfExpiryDF, placeOfExpiryTF);
		bodyVl.add(formLayout);
		bodyVl.add(section2);
		bodyVl.add(section2Hr);
		formLayout1.add(applicantHl, beneficiaryHl, advisingBankHl, beneficiaryCountryHl);
		formLayout1.add(advThrBankHl, new EmptyFormLayoutItem(), new EmptyFormLayoutItem());
		formLayout1.setColspan(advThrBankHl, 2);
		bodyVl.add(formLayout1);

		bodyVl.add(section3);
		bodyVl.add(section3Hr);

		formLayout2.add(amountHl, toleranceHl, additionalAmountsCoveredTA, new EmptyFormLayoutItem());
		formLayout2.add(availableWithByCodeCB, availableWithByBICHl, availableWithByNameTF, availableWithByAddress1TF);
		formLayout2.add(new EmptyFormLayoutItem(), new EmptyFormLayoutItem(), availableWithByAddress2TF,
				availableWithByAddress3TF);
		bodyVl.add(formLayout2);

		bodyVl.add(section5);
		bodyVl.add(section5Hr);
		formLayout3.add(dispatchFromTA, portOfLoadingTA, portOfDischargeTA, placeOfFinalDestinationTA);
		formLayout3.add(latestDateOfShipmentDF, shipmentPeriodTA, goodDesriptionTA, narrativeTA);
		formLayout3.add(senderToReceiverInfoTA);
		bodyVl.add(formLayout3);

		add(hlheader, hrHeader, bodyVl, hrFooter, hlFooter);

		saveButton.addClickListener(event -> {
			if (entity.getAdvisingBankBicCode() != null)
				entity.setReceiverBic(entity.getAdvisingBankBicCode());
			preAdviseService.savePreAdvise(entity);
			Notification.show("Readvise record saved for update", 2000, Position.TOP_CENTER);
		});
	}

	private LcMaster lcMaster = null;
	private Register register = null;
	private Product product = null;

	@Override
	public void setParameter(BeforeEvent event, String parameter) {
		Logger.info("Setting parameter for LcPreAdviseForm: " + parameter);
		if (parameter != null && !parameter.isEmpty()) {
			String params[] = parameter.split(",");
			int paramSize = params.length;

			if (paramSize > 1) {
				Logger.info("Fetching LcMaster with ID: " + params[1]);
				long lcMasterId = Integer.valueOf(params[1]);
				lcMaster = lcMasterRepository.findById(lcMasterId).orElse(null);
				register = registerRepository.findById(lcMaster.getRegisterId()).orElse(null);
				if (lcMaster != null) {
					Logger.info("LcMaster found: " + lcMaster.getId() + ", Register ID: " + lcMaster.getRegisterId());
					register = registerRepository.findById(lcMaster.getRegisterId()).orElse(null);
					if (register != null) {
						Logger.info("Register found: " + register.getId() + ", Transaction Reference: "
								+ register.getTransactionReference());
						product = productRepository.findById(register.getProductId()).orElse(null);
						if (product != null) {
							Logger.info("Product found: " + product.getId() + ", Name: " + product.getProductName());
						} else {
							Logger.warn("Product not found for ID: " + register.getProductId());
						}
					} else {
						Logger.warn("Register not found for ID: " + lcMaster.getRegisterId());
					}
				}
			}

			Logger.info("Fetching LcMaster with ID: " + params[1]);
			long lcMasterId = Integer.valueOf(params[1]);
			// long preAdviseId = Integer.valueOf(params[2]);
			entity = repository.findByLcMasterId(lcMasterId);

			if (entity == null) {
				entity = new ImportLetterOfCreditPreAdvise();
				entity.setLcMasterId(lcMaster.getId());
				entity.setDocumentaryCrNo(register.getTransactionReference());
				entity.setApplicantPartyId(lcMaster.getApplicantPartyId());
				entity.setApplicantName(lcMaster.getApplicantName());
				entity.setBeneficiaryPartyId(lcMaster.getBeneficiaryPartyId());
				entity.setBeneficiaryName(lcMaster.getBeneficiaryName());
				entity.setCurrencyCode(lcMaster.getLcCcy());
				entity.setAmountOfDocumentaryCredit(lcMaster.getLcAmount());
				entity = repository.save(entity);

				entity.setLcMaster(lcMaster);
				entity.setRegister(register);
			} else {
				entity.setLcMaster(lcMaster);
				entity.setRegister(register);
			}

		} else {
			event.rerouteTo(LcMasterView.class);
		}
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		Logger.info("LcPreAdviseForm attached to UI");
		if (product != null) {
			currencyCB.setItems(product.getAllowedCurrencies());
		}
		binder.forField(currencyCB).bind(ImportLetterOfCreditPreAdvise::getCurrencyCode,
				ImportLetterOfCreditPreAdvise::setCurrencyCode);
		binder.setBean(entity);
		Logger.info("onAttach completed");
	}

}

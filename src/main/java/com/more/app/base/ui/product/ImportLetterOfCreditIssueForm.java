package com.more.app.base.ui.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.tinylog.Logger;

import com.more.app.base.ui.CrudPageView;
import com.more.app.entity.Product;
import com.more.app.entity.product.ImportLetterOfCreditIssue;
import com.more.app.entity.product.ImportLetterOfCreditPreAdvise;
import com.more.app.entity.product.LcMaster;
import com.more.app.entity.product.Party;
import com.more.app.entity.product.Register;
import com.more.app.repository.CountryRepository;
import com.more.app.repository.CustomerRepository;
import com.more.app.repository.product.ImportLetterOfCreditIssueRepository;
import com.more.app.repository.product.ImportLetterOfCreditPreAdviseRepository;
import com.more.app.repository.product.LcMasterRepository;
import com.more.app.repository.product.PartyRepository;
import com.more.app.repository.product.RegisterRepository;
import com.more.app.repository.productsetup.ProductRepository;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = "lcIssue-form", layout = CrudPageView.class)
public class ImportLetterOfCreditIssueForm extends VerticalLayout implements HasUrlParameter<String> {

	private final Binder<ImportLetterOfCreditIssue> binder = new Binder<>(ImportLetterOfCreditIssue.class);

	private H5 title = new H5("Letter of Credit Issue");
	private Hr hrHeader = new Hr();
	private Hr hrFooter = new Hr();



	// Basic Fields
	private ComboBox<String> formOfDocumentaryCrField;
	private TextField documentaryCrNoField;
	private TextField referenceToPreAdviceField;

	// Dates & Expiry
	private DatePicker dateOfIssueField;
	private DatePicker dateOfExpiryField;
	private TextField placeOfExpiryField;
	private DatePicker latestDateOfShipmentField;

	// Rules & Applicable
	private ComboBox<String> applicableRulesField;
	private TextArea applicableRulesNarrativeField;

	// Parties
	private TextArea applicantBankField;
	private TextArea applicantField;
	private TextArea beneficiaryField;
	private TextArea advisingBankField;

	private HorizontalLayout applicantBankHl;
	private Button searchApplicantBankPartyBtn;

	private HorizontalLayout applicantHl;
	private Button searchApplicantPartyBtn;

	private HorizontalLayout beneficiaryHl;
	private Button searchBeneficiaryBtn;

	private HorizontalLayout advisingBankHl;
	private Button searchAdvisingBankPartyBtn;

	// Amount & Currency
	private HorizontalLayout currencyAmountHl;
	private TextField currencyCodeField;
	private BigDecimalField amountOfDocumentaryCreditField;
	private IntegerField tolerance1Field;
	private IntegerField tolerance2Field;
	private TextArea additionalAmountsCoveredField;

	// Payment & Draft Details

	private HorizontalLayout availableWithByHl;
	private Button searchAvailableWithByPartyBtn;
	private TextArea availableWithByField;

	private TextArea draftsAtField;

	private HorizontalLayout draweeHl;
	private Button searchDraweePartyBtn;
	private TextArea draweeField;
	private TextArea mixedPaymentDetailsField;
	private TextArea negotiationDeferredPaymentDetailsField;

	// Shipment Details
	private ComboBox<String> partialShipmentsField;
	private ComboBox<String> transhipmentsField;
	private TextArea dispatchFromField;
	private TextArea portOfLoadingField;
	private TextArea portOfDischargeField;
	private TextArea placeOfFinalDestinationField;
	private TextArea shipmentPeriodField;

	// Documents & Conditions
	private TextArea goodsDescriptionField;
	private TextArea documentsRequiredField;
	private TextArea additionalConditionsField;
	private TextArea specialPaymentConditionBeneficiaryField;
	private TextArea specialPaymentConditionReceivingBankField;
	private TextArea chargesField;

	// Presentation & Confirmation
	private HorizontalLayout periodOfPresentationHl;
	private IntegerField periodOfPresentationDaysField;
	private TextField periodOfPresentationNarrativeField;
	private ComboBox<String> confirmationInstructionsField;

	private HorizontalLayout requestedConfirmationHl;
	private Button searchRequestedConfirmationPartyBtn;
	private TextArea requestedConfirmationPartyField;

	private HorizontalLayout reimbursingBankHl;
	private Button searchReimbursingBankPartyBtn;
	private TextArea reimbursingBankPartyField;

	// Additional Info
	private TextArea instructionsToPayBankField;

	private HorizontalLayout advThrBankHl;
	private Button searchAdvThrBankPartyBtn;
	private TextArea advThrBankPartyField;

	private TextArea senderToReceiverInfoField;

	private Button saveButton;
	public Button closeButton, validateButton, submitButton, declineButton, cancelButton;
	private HorizontalLayout hlFooter = new HorizontalLayout();
	private HorizontalLayout hlheader = new HorizontalLayout();
	@Autowired
	private PartyCrudView partCrudView;

	public ImportLetterOfCreditIssueForm() {
		initializeFields();
		createLayout();
		// add(hlFooter);
	}

	private void initializeFields() {
		
		title.getElement().getStyle().set("font-weight", "bold");

		hlheader.setPadding(false);
		hlheader.setMargin(false);
		hlheader.setSpacing(true);
		hlheader.add(title);

		hrHeader.setWidthFull();
		hrFooter.setWidthFull();

		formOfDocumentaryCrField = new ComboBox<String>();
		formOfDocumentaryCrField.setItems("IRREVOCABLE", "IRREVOCABLE TRANSFERABLE");
		formOfDocumentaryCrField.setClearButtonVisible(true);
		formOfDocumentaryCrField.setLabel("Form of Documentary Credit");
		formOfDocumentaryCrField.setRequired(true);
		formOfDocumentaryCrField.setRequiredIndicatorVisible(true);
		binder.forField(formOfDocumentaryCrField).bind(ImportLetterOfCreditIssue::getFormOfDocumentaryCr,
				ImportLetterOfCreditIssue::setFormOfDocumentaryCr);

		documentaryCrNoField = new TextField();
		documentaryCrNoField.setMaxLength(16);
		documentaryCrNoField.setLabel("Documentary Credit No.");
		documentaryCrNoField.setRequired(true);
		documentaryCrNoField.setRequiredIndicatorVisible(true);
		binder.forField(documentaryCrNoField).bind(ImportLetterOfCreditIssue::getDocumentaryCrNo,
				ImportLetterOfCreditIssue::setDocumentaryCrNo);

		referenceToPreAdviceField = new TextField("Pre-Advice Reference");
		referenceToPreAdviceField.setMaxLength(16);
		referenceToPreAdviceField.setEnabled(false);
		binder.forField(referenceToPreAdviceField).bind(ImportLetterOfCreditIssue::getReferenceToPreAdvice,
				ImportLetterOfCreditIssue::setReferenceToPreAdvice);

		dateOfIssueField = new DatePicker("Issue Date");
		dateOfIssueField.setRequired(true);
		dateOfIssueField.setRequiredIndicatorVisible(true);
		binder.forField(dateOfIssueField).bind(ImportLetterOfCreditIssue::getDateOfIssue,
				ImportLetterOfCreditIssue::setDateOfIssue);

		dateOfExpiryField = new DatePicker();
		dateOfExpiryField.setLabel("Date of Expiry");
		dateOfExpiryField.setRequired(true);
		dateOfExpiryField.setRequiredIndicatorVisible(true);
		binder.forField(dateOfExpiryField).bind(ImportLetterOfCreditIssue::getDateOfExpiry,
				ImportLetterOfCreditIssue::setDateOfExpiry);

		placeOfExpiryField = new TextField();
		placeOfExpiryField.setMaxLength(29);
		placeOfExpiryField.setLabel("Place of Expiry");
		placeOfExpiryField.setRequired(true);
		placeOfExpiryField.setRequiredIndicatorVisible(true);
		binder.forField(placeOfExpiryField).bind(ImportLetterOfCreditIssue::getPlaceOfExpiry,
				ImportLetterOfCreditIssue::setPlaceOfExpiry);

		latestDateOfShipmentField = new DatePicker("Latest Shipment Date");
		latestDateOfShipmentField.setRequired(true);
		latestDateOfShipmentField.setRequiredIndicatorVisible(true);
		binder.forField(latestDateOfShipmentField).bind(ImportLetterOfCreditIssue::getLatestDateOfShipment,
				ImportLetterOfCreditIssue::setLatestDateOfShipment);

		applicableRulesField = new ComboBox<String>("Applicable Rules");
		applicableRulesField.setItems("EUCP LATEST VERSION", "EUCPURR LATEST VERSION", "UCP LATEST VERSION",
				"UCPURR LATEST VERSION", "OTHR");
		applicableRulesField.setRequired(true);
		applicableRulesField.setRequiredIndicatorVisible(true);
		binder.forField(applicableRulesField).bind(ImportLetterOfCreditIssue::getApplicableRules,
				ImportLetterOfCreditIssue::setApplicableRules);

		applicableRulesNarrativeField = new TextArea("Applicable Rules Narrative");
		applicableRulesNarrativeField.setMaxLength(35);
		applicableRulesNarrativeField.setRequired(true);
		applicableRulesNarrativeField.setRequiredIndicatorVisible(true);
		binder.forField(applicableRulesNarrativeField).bind(ImportLetterOfCreditIssue::getApplicableRulesNarrative,
				ImportLetterOfCreditIssue::setApplicableRulesNarrative);

		applicantBankHl = new HorizontalLayout();
		applicantBankField = new TextArea();
		applicantBankField.setLabel("Applicant Bank");
		applicantBankField.setWidthFull();
		applicantBankField.setRequired(true);
		applicantBankField.setRequiredIndicatorVisible(true);
		applicantBankField.setReadOnly(true);
		binder.forField(applicantBankField).bind(ImportLetterOfCreditIssue::getApplicantBankDetails,
				ImportLetterOfCreditIssue::setApplicantBankDetails);
		searchApplicantBankPartyBtn = new Button(new Icon(VaadinIcon.SEARCH));
		applicantBankHl.add(applicantBankField, searchApplicantBankPartyBtn);
		applicantBankHl.setSpacing(true);
		applicantBankHl.setVerticalComponentAlignment(Alignment.BASELINE, applicantBankField,
				searchApplicantBankPartyBtn);
		applicantBankHl.setWidthFull();

		applicantHl = new HorizontalLayout();
		applicantField = new TextArea("Applicant");
		applicantField.setWidthFull();
		applicantField.setRequired(true);
		applicantField.setRequiredIndicatorVisible(true);
		applicantField.setReadOnly(true);
		binder.forField(applicantBankField).bind(ImportLetterOfCreditIssue::getApplicantDetails,
				ImportLetterOfCreditIssue::setApplicantDetails);
		searchApplicantPartyBtn = new Button(new Icon(VaadinIcon.SEARCH));

		applicantHl.add(applicantField, searchApplicantPartyBtn);
		applicantHl.setSpacing(true);
		applicantHl.setVerticalComponentAlignment(Alignment.BASELINE, applicantField, searchApplicantPartyBtn);
		applicantHl.setWidthFull();

		beneficiaryHl = new HorizontalLayout();
		beneficiaryField = new TextArea();
		beneficiaryField.setLabel("Beneficiary");
		beneficiaryField.setWidthFull();
		beneficiaryField.setRequired(true);
		beneficiaryField.setRequiredIndicatorVisible(true);
		beneficiaryField.setReadOnly(true);
		binder.forField(beneficiaryField).bind(ImportLetterOfCreditIssue::getBeneficiaryDetails,
				ImportLetterOfCreditIssue::setBeneficiaryDetails);
		searchBeneficiaryBtn = new Button(new Icon(VaadinIcon.SEARCH));
		beneficiaryHl.add(beneficiaryField, searchBeneficiaryBtn);
		beneficiaryHl.setSpacing(true);
		beneficiaryHl.setVerticalComponentAlignment(Alignment.BASELINE, beneficiaryField, searchBeneficiaryBtn);
		beneficiaryHl.setWidthFull();

		advisingBankHl = new HorizontalLayout();
		advisingBankField = new TextArea("Advising Bank");
		advisingBankField.setWidthFull();
		advisingBankField.setRequired(true);
		advisingBankField.setRequiredIndicatorVisible(true);
		advisingBankField.setReadOnly(true);
		binder.forField(advisingBankField).bind(ImportLetterOfCreditIssue::getApplicantBankDetails,
				ImportLetterOfCreditIssue::setApplicantBankDetails);
		searchAdvisingBankPartyBtn = new Button(new Icon(VaadinIcon.SEARCH));
		advisingBankHl.add(advisingBankField, searchAdvisingBankPartyBtn);
		advisingBankHl.setSpacing(true);
		advisingBankHl.setVerticalComponentAlignment(Alignment.BASELINE, advisingBankField, searchAdvisingBankPartyBtn);
		advisingBankHl.setWidthFull();

		currencyAmountHl = new HorizontalLayout();
		currencyCodeField = new TextField("Currency");
		currencyCodeField.setMaxLength(3);
		currencyCodeField.setRequired(true);
		currencyCodeField.setRequiredIndicatorVisible(true);
		currencyCodeField.setWidth(100, Unit.PIXELS);
		binder.forField(currencyCodeField).bind(ImportLetterOfCreditIssue::getCurrencyCode,
				ImportLetterOfCreditIssue::setCurrencyCode);

		amountOfDocumentaryCreditField = new BigDecimalField("LC Amount");
		amountOfDocumentaryCreditField.setRequired(true);
		amountOfDocumentaryCreditField.setRequiredIndicatorVisible(true);
		binder.forField(amountOfDocumentaryCreditField).bind(ImportLetterOfCreditIssue::getAmountOfDocumentaryCredit,
				ImportLetterOfCreditIssue::setAmountOfDocumentaryCredit);
		currencyAmountHl.add(currencyCodeField, amountOfDocumentaryCreditField);

		tolerance1Field = new IntegerField("Tolerance (+)");
		binder.forField(tolerance1Field).bind(ImportLetterOfCreditIssue::getTolerance1,
				ImportLetterOfCreditIssue::setTolerance1);
		tolerance2Field = new IntegerField("Tolerance (-)");
		binder.forField(tolerance2Field).bind(ImportLetterOfCreditIssue::getTolerance2,
				ImportLetterOfCreditIssue::setTolerance2);

		additionalAmountsCoveredField = new TextArea("Additional Amounts Covered");
		additionalAmountsCoveredField.setMaxLength(140);
		binder.forField(additionalAmountsCoveredField).bind(ImportLetterOfCreditIssue::getAdditionalAmountsCovered,
				ImportLetterOfCreditIssue::setAdditionalAmountsCovered);

		availableWithByHl = new HorizontalLayout();
		availableWithByField = new TextArea("Available With ... By ...");
		availableWithByField.setWidthFull();
		availableWithByField.setReadOnly(true);
		availableWithByField.setRequired(true);
		availableWithByField.setRequiredIndicatorVisible(true);
		binder.forField(availableWithByField).bind(ImportLetterOfCreditIssue::getApplicantBankDetails,
				ImportLetterOfCreditIssue::setApplicantBankDetails);
		searchAvailableWithByPartyBtn = new Button(new Icon(VaadinIcon.SEARCH));
		availableWithByHl.add(availableWithByField, searchAvailableWithByPartyBtn);
		availableWithByHl.setSpacing(true);
		availableWithByHl.setVerticalComponentAlignment(Alignment.BASELINE, availableWithByField,
				searchAvailableWithByPartyBtn);
		availableWithByHl.setWidthFull();

		draftsAtField = new TextArea("Drafts At");
		draftsAtField.setMaxLength(140);

		binder.forField(draftsAtField).bind(ImportLetterOfCreditIssue::getDraftsAt,
				ImportLetterOfCreditIssue::setDraftsAt);

		draweeHl = new HorizontalLayout();
		draweeField = new TextArea("Drawee");
		draweeField.setWidthFull();
		draweeField.setReadOnly(true);
		binder.forField(draweeField).bind(ImportLetterOfCreditIssue::getDraweeDetails,
				ImportLetterOfCreditIssue::setDraweeDetails);
		searchDraweePartyBtn = new Button(new Icon(VaadinIcon.SEARCH));
		draweeHl.add(draweeField, searchDraweePartyBtn);
		draweeHl.setSpacing(true);
		draweeHl.setVerticalComponentAlignment(Alignment.BASELINE, draweeField, searchDraweePartyBtn);
		draweeHl.setWidthFull();

		mixedPaymentDetailsField = new TextArea("Mixed Payment");
		mixedPaymentDetailsField.setMaxLength(140);
		binder.forField(mixedPaymentDetailsField).bind(ImportLetterOfCreditIssue::getMixedPaymentDetails,
				ImportLetterOfCreditIssue::setMixedPaymentDetails);

		negotiationDeferredPaymentDetailsField = new TextArea("Negotiation / Deferred Payment");
		negotiationDeferredPaymentDetailsField.setMaxLength(140);
		binder.forField(negotiationDeferredPaymentDetailsField).bind(
				ImportLetterOfCreditIssue::getNegotiationDeferredPaymentDetails,
				ImportLetterOfCreditIssue::setNegotiationDeferredPaymentDetails);

		partialShipmentsField = new ComboBox<String>("Partial Shipments");
		partialShipmentsField.setItems("ALLOWED", "CONDITIONAL", "NOT ALLOWED");
		binder.forField(partialShipmentsField).bind(ImportLetterOfCreditIssue::getPartialShipments,
				ImportLetterOfCreditIssue::setPartialShipments);

		transhipmentsField = new ComboBox<String>("Transhipments");
		transhipmentsField.setItems("ALLOWED", "CONDITIONAL", "NOT ALLOWED");
		binder.forField(transhipmentsField).bind(ImportLetterOfCreditIssue::getTranshipments,
				ImportLetterOfCreditIssue::setTranshipments);

		dispatchFromField = new TextArea("Dispatch From");
		dispatchFromField.setMaxLength(140);
		binder.forField(dispatchFromField).bind(ImportLetterOfCreditIssue::getDispatchFrom,
				ImportLetterOfCreditIssue::setDispatchFrom);

		portOfLoadingField = new TextArea("Port of Loading");
		portOfLoadingField.setMaxLength(140);
		binder.forField(portOfLoadingField).bind(ImportLetterOfCreditIssue::getPortOfLoading,
				ImportLetterOfCreditIssue::setPortOfLoading);

		portOfDischargeField = new TextArea("Port of Discharge");
		portOfDischargeField.setMaxLength(140);
		binder.forField(portOfDischargeField).bind(ImportLetterOfCreditIssue::getPortOfDischarge,
				ImportLetterOfCreditIssue::setPortOfDischarge);

		placeOfFinalDestinationField = new TextArea("Final Destination");
		placeOfFinalDestinationField.setMaxLength(140);
		binder.forField(placeOfFinalDestinationField).bind(ImportLetterOfCreditIssue::getPlaceOfFinalDestination,
				ImportLetterOfCreditIssue::setPlaceOfFinalDestination);

		shipmentPeriodField = new TextArea("Shipment Period");
		shipmentPeriodField.setMaxLength(390);
		binder.forField(shipmentPeriodField).bind(ImportLetterOfCreditIssue::getShipmentPeriod,
				ImportLetterOfCreditIssue::setShipmentPeriod);

		goodsDescriptionField = new TextArea("Goods Description");
		goodsDescriptionField.setMaxLength(6500);
		binder.forField(goodsDescriptionField).bind(ImportLetterOfCreditIssue::getGoodDesription,
				ImportLetterOfCreditIssue::setGoodDesription);

		documentsRequiredField = new TextArea("Documents Required");
		documentsRequiredField.setMaxLength(6500);
		binder.forField(documentsRequiredField).bind(ImportLetterOfCreditIssue::getDocumentsRequired,
				ImportLetterOfCreditIssue::setDocumentsRequired);

		additionalConditionsField = new TextArea("Additional Conditions");
		additionalConditionsField.setMaxLength(6500);
		binder.forField(additionalConditionsField).bind(ImportLetterOfCreditIssue::getAdditionalConditions,
				ImportLetterOfCreditIssue::setAdditionalConditions);

		specialPaymentConditionBeneficiaryField = new TextArea("Payment Conditions (Beneficiary)");
		specialPaymentConditionBeneficiaryField.setMaxLength(6500);
		binder.forField(specialPaymentConditionBeneficiaryField).bind(
				ImportLetterOfCreditIssue::getSpecialPaymentConditionBeneficiary,
				ImportLetterOfCreditIssue::setSpecialPaymentConditionBeneficiary);

		specialPaymentConditionReceivingBankField = new TextArea("Payment Conditions (Receiving Bank)");
		specialPaymentConditionReceivingBankField.setMaxLength(6500);
		binder.forField(specialPaymentConditionReceivingBankField).bind(
				ImportLetterOfCreditIssue::getSpecialPaymentConditionReceivingBank,
				ImportLetterOfCreditIssue::setSpecialPaymentConditionReceivingBank);

		chargesField = new TextArea("Charges");
		chargesField.setMaxLength(210);
		binder.forField(chargesField).bind(ImportLetterOfCreditIssue::getCharges,
				ImportLetterOfCreditIssue::setCharges);

		periodOfPresentationHl = new HorizontalLayout();
		periodOfPresentationDaysField = new IntegerField("Presentation Period");
		periodOfPresentationDaysField.setWidth(150, Unit.PIXELS);
		binder.forField(periodOfPresentationDaysField).bind(ImportLetterOfCreditIssue::getPeriodOfPresentationDays,
				ImportLetterOfCreditIssue::setPeriodOfPresentationDays);

		periodOfPresentationNarrativeField = new TextField("Narrative");
		periodOfPresentationNarrativeField.setMaxLength(35);
		periodOfPresentationNarrativeField.setWidthFull();
		binder.forField(periodOfPresentationNarrativeField).bind(
				ImportLetterOfCreditIssue::getPeriodOfPresentationNarrative,
				ImportLetterOfCreditIssue::setPeriodOfPresentationNarrative);

		periodOfPresentationHl.add(periodOfPresentationDaysField, periodOfPresentationNarrativeField);

		confirmationInstructionsField = new ComboBox<String>("Confirmation Instructions");
		confirmationInstructionsField.setItems("CONFIRM", "MAY ADD", "WITHOUT");
		confirmationInstructionsField.setRequired(true);
		confirmationInstructionsField.setRequiredIndicatorVisible(true);
		binder.forField(confirmationInstructionsField).bind(ImportLetterOfCreditIssue::getConfirmationInstructions,
				ImportLetterOfCreditIssue::setConfirmationInstructions);

		requestedConfirmationHl = new HorizontalLayout();
		requestedConfirmationPartyField = new TextArea("Requested Confirmation Party");
		requestedConfirmationPartyField.setWidthFull();
		requestedConfirmationPartyField.setReadOnly(true);
		binder.forField(applicantBankField).bind(ImportLetterOfCreditIssue::getRequestedConfirmationDetails,
				ImportLetterOfCreditIssue::setRequestedConfirmationDetails);
		searchRequestedConfirmationPartyBtn = new Button(new Icon(VaadinIcon.SEARCH));
		requestedConfirmationHl.add(requestedConfirmationPartyField, searchRequestedConfirmationPartyBtn);
		requestedConfirmationHl.setSpacing(true);
		requestedConfirmationHl.setVerticalComponentAlignment(Alignment.BASELINE, requestedConfirmationPartyField,
				searchRequestedConfirmationPartyBtn);
		requestedConfirmationHl.setWidthFull();

		reimbursingBankHl = new HorizontalLayout();
		reimbursingBankPartyField = new TextArea("Reimbursing Bank");
		reimbursingBankPartyField.setWidthFull();
		reimbursingBankPartyField.setReadOnly(true);
		binder.forField(reimbursingBankPartyField).bind(ImportLetterOfCreditIssue::getReimbursingBankDetails,
				ImportLetterOfCreditIssue::setReimbursingBankDetails);
		searchReimbursingBankPartyBtn = new Button(new Icon(VaadinIcon.SEARCH));
		reimbursingBankHl.add(reimbursingBankPartyField, searchReimbursingBankPartyBtn);
		reimbursingBankHl.setSpacing(true);
		reimbursingBankHl.setVerticalComponentAlignment(Alignment.BASELINE, reimbursingBankPartyField,
				searchReimbursingBankPartyBtn);
		reimbursingBankHl.setWidthFull();

		advThrBankHl = new HorizontalLayout();
		advThrBankPartyField = new TextArea("Advise Through Bank");
		advThrBankPartyField.setWidthFull();
		advThrBankPartyField.setReadOnly(true);
		binder.forField(advThrBankPartyField).bind(ImportLetterOfCreditIssue::getAdvThrBankDetails,
				ImportLetterOfCreditIssue::setAdvThrBankDetails);
		searchAdvThrBankPartyBtn = new Button(new Icon(VaadinIcon.SEARCH));
		advThrBankHl.add(advThrBankPartyField, searchAdvThrBankPartyBtn);
		advThrBankHl.setSpacing(true);
		advThrBankHl.setVerticalComponentAlignment(Alignment.BASELINE, advThrBankPartyField, searchAdvThrBankPartyBtn);
		advThrBankHl.setWidthFull();

		instructionsToPayBankField = new TextArea("Instructions To Paying Bank");
		instructionsToPayBankField.setMaxLength(780);
		binder.forField(instructionsToPayBankField).bind(ImportLetterOfCreditIssue::getInstructionsToPayBank,
				ImportLetterOfCreditIssue::setInstructionsToPayBank);

		senderToReceiverInfoField = new TextArea("Sender To Receiver Info");
		senderToReceiverInfoField.setMaxLength(210);
		binder.forField(senderToReceiverInfoField).bind(ImportLetterOfCreditIssue::getSenderToReceiverInfo,
				ImportLetterOfCreditIssue::setSenderToReceiverInfo);

		addListeners();

		saveButton = new Button("Save", new Icon(VaadinIcon.CHECK_CIRCLE_O));
		saveButton.addClickListener(e -> {
			try {
				binder.writeBean(entity);
				System.out.println(entity.getApplicantBank().getId());
				repository.save(entity);
				Notification.show("Lc Issue record saved for update", 2000, Position.TOP_CENTER);
			} catch (ValidationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		closeButton = new Button("Close", new Icon(VaadinIcon.CLOSE_CIRCLE_O));
		closeButton.addClickListener(event -> {
			removeAll();
			getUI().get().navigate(LcMasterView.class);
		});

		submitButton = new Button("Submit", new Icon(VaadinIcon.CHECK));
		declineButton = new Button("Decline", new Icon(VaadinIcon.ARROW_CIRCLE_DOWN_O));
		cancelButton = new Button("Cancel", new Icon(VaadinIcon.MINUS_CIRCLE_O));
		validateButton = new Button("Validate", new Icon(VaadinIcon.CHECK_SQUARE_O));
		hlFooter.setSizeFull();
		hlFooter.add(validateButton, saveButton, submitButton, declineButton, cancelButton, closeButton);

	}

	private void createLayout() {
		add(hlheader, hrHeader);
		
		Tabs tabs = new Tabs();

		Tab basicTab = new Tab("Basic Information");
		Tab partyTab = new Tab("Party Information");
		Tab amountTab = new Tab("Amount & Currency");
		Tab shipmentTab = new Tab("Shipment Details");
		Tab docsTab = new Tab("Documents & Conditions");
		Tab additionalTab = new Tab("Additional Information");

		tabs.add(basicTab, partyTab, amountTab, shipmentTab, docsTab, additionalTab);
		tabs.setSelectedTab(basicTab);

		VerticalLayout basicLayout = createBasicLayout();
		VerticalLayout partyLayout = createPartyLayout();
		VerticalLayout amountLayout = createAmountLayout();
		VerticalLayout shipmentLayout = createShipmentLayout();
		VerticalLayout docsLayout = createDocsLayout();
		VerticalLayout additionalLayout = createAdditionalLayout();
		
		
		tabs.addSelectedChangeListener(event -> {
			removeAll();
			add(hlheader, hrHeader);
			add(tabs);

			if (event.getSelectedTab() == basicTab) {
				add(basicLayout);
			} else if (event.getSelectedTab() == partyTab) {
				add(partyLayout);
			} else if (event.getSelectedTab() == amountTab) {
				add(amountLayout);
			} else if (event.getSelectedTab() == shipmentTab) {
				add(shipmentLayout);
			} else if (event.getSelectedTab() == docsTab) {
				add(docsLayout);
			} else if (event.getSelectedTab() == additionalTab) {
				add(additionalLayout);
			}
			add(hrFooter);
			add(hlFooter);
		});

		add(tabs, basicLayout,hrFooter, hlFooter);
	}

	private VerticalLayout createBasicLayout() {
		FormLayout form = new FormLayout();
		form.add(formOfDocumentaryCrField, documentaryCrNoField, referenceToPreAdviceField, dateOfIssueField,
				dateOfExpiryField, placeOfExpiryField, applicableRulesField, applicableRulesNarrativeField,
				confirmationInstructionsField);
		form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1), new FormLayout.ResponsiveStep("500px", 2),
				new FormLayout.ResponsiveStep("500px", 3));
		return new VerticalLayout(form);
	}

	private VerticalLayout createPartyLayout() {
		FormLayout form = new FormLayout();
		form.add(applicantHl, applicantBankHl, beneficiaryHl, advisingBankHl, requestedConfirmationHl,
				reimbursingBankHl, advThrBankHl);
		form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1), new FormLayout.ResponsiveStep("500px", 2));
		return new VerticalLayout(form);
	}

	private VerticalLayout createAmountLayout() {
		FormLayout form = new FormLayout();
		form.add(currencyAmountHl, tolerance1Field, tolerance2Field, additionalAmountsCoveredField, availableWithByHl,
				draweeHl);
		form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1), new FormLayout.ResponsiveStep("500px", 2),
				new FormLayout.ResponsiveStep("500px", 3));
		return new VerticalLayout(form);
	}

	private VerticalLayout createShipmentLayout() {
		FormLayout form = new FormLayout();
		form.add(partialShipmentsField, transhipmentsField, dispatchFromField, portOfLoadingField, portOfDischargeField,
				placeOfFinalDestinationField, latestDateOfShipmentField, shipmentPeriodField, goodsDescriptionField);
		form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1), new FormLayout.ResponsiveStep("500px", 2));
		return new VerticalLayout(form);
	}

	private VerticalLayout createDocsLayout() {
		FormLayout form = new FormLayout();
		form.add(documentsRequiredField, additionalConditionsField, specialPaymentConditionBeneficiaryField,
				specialPaymentConditionReceivingBankField, chargesField, draftsAtField, mixedPaymentDetailsField,
				negotiationDeferredPaymentDetailsField);
		form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1), new FormLayout.ResponsiveStep("500px", 2));
		return new VerticalLayout(form);
	}

	private VerticalLayout createAdditionalLayout() {
		FormLayout form = new FormLayout();
		form.add(periodOfPresentationHl, instructionsToPayBankField, senderToReceiverInfoField);
		form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1), new FormLayout.ResponsiveStep("500px", 2));
		return new VerticalLayout(form);
	}

	public void setImportLetterOfCreditIssue(ImportLetterOfCreditIssue ilcIssue) {
		binder.setBean(ilcIssue);
	}

	public ImportLetterOfCreditIssue getImportLetterOfCreditIssue() {
		return binder.getBean();
	}

	public Binder<ImportLetterOfCreditIssue> getBinder() {
		return binder;
	}

	private ImportLetterOfCreditIssue entity;
	@Autowired
	private ImportLetterOfCreditIssueRepository repository;
	@Autowired
	private ImportLetterOfCreditPreAdviseRepository preAdviseRepo;
	@Autowired
	private LcMasterRepository lcMasterRepository;
	@Autowired
	private RegisterRepository registerRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private PartyRepository partyRepository;
	@Autowired
	private CustomerRepository customerRepository;

	private LcMaster lcMaster = null;
	private Register register = null;
	private Product product = null;
	private ImportLetterOfCreditPreAdvise preAdvise = null;

	@Override
	public void setParameter(BeforeEvent event, String parameter) {
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		Logger.info("Setting parameter for ImportLetterOfCreditIssueForm: " + parameter);
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

					preAdvise = preAdviseRepo.findByLcMasterId(lcMasterId);
					if (preAdvise != null) {
						Logger.info("PreAdvise found: " + preAdvise.getId());
					}
				}
			}

			Logger.info("Fetching LcMaster with ID: " + params[1]);
			long lcMasterId = Integer.valueOf(params[1]);
			// long preAdviseId = Integer.valueOf(params[2]);
			entity = repository.findByLcMasterId(lcMasterId);

			if (entity == null) {
				System.out.println(
						"MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
				entity = new ImportLetterOfCreditIssue();
				entity.setLcMasterId(lcMaster.getId());
				entity.setDocumentaryCrNo(register.getTransactionReference());

				entity.setCurrencyCode(lcMaster.getLcCcy());
				entity.setAmountOfDocumentaryCredit(lcMaster.getLcAmount());
				entity = repository.save(entity);

			}
			if (preAdvise != null) {
				// Basic fields
				entity.setLcMasterId(preAdvise.getLcMasterId());
				entity.setRegister(preAdvise.getRegister());
				entity.setLcMaster(preAdvise.getLcMaster());
				entity.setSenderBic(preAdvise.getSenderBic());
				entity.setReceiverBic(preAdvise.getReceiverBic());
				entity.setFormOfDocumentaryCr(preAdvise.getFormOfDocumentaryCr());
				entity.setDocumentaryCrNo(preAdvise.getDocumentaryCrNo());
				entity.setDateOfExpiry(preAdvise.getDateOfExpiry());
				entity.setPlaceOfExpiry(preAdvise.getPlaceOfExpiry());

				// Party mappings - resolve IDs to Party objects
				if (preAdvise.getApplicantPartyId() != null) {
					entity.setApplicant(partyRepository.findById(preAdvise.getApplicantPartyId()).orElse(null));
					entity.setApplicantDetails(preAdvise.getApplicantName());
				}

				System.out.println(
						"UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
				if (preAdvise.getBeneficiaryPartyId() != null) {
					System.out.println(
							"GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
					entity.setBeneficiary(partyRepository.findById(preAdvise.getBeneficiaryPartyId()).orElse(null));
					entity.setBeneficiaryDetails(preAdvise.getBeneficiaryName());
				}

				if (preAdvise.getAdvisingBankPartyId() != null) {
					entity.setAdvisingBank(partyRepository.findById(preAdvise.getAdvisingBankPartyId()).orElse(null));
					entity.setAdvisingBankDetails(preAdvise.getAdvisingBankName());
				}

				if (preAdvise.getAdvThrBankPartyId() != null) {
					entity.setAdvThrBankParty(partyRepository.findById(preAdvise.getAdvThrBankPartyId()).orElse(null));
					entity.setAdvThrBankDetails(preAdvise.getAdvThrBankName());
				}

				// Shipment and location fields
				entity.setCurrencyCode(preAdvise.getCurrencyCode());
				entity.setAmountOfDocumentaryCredit(preAdvise.getAmountOfDocumentaryCredit());
				entity.setTolerance1(
						preAdvise.getTolerancePlus() != null ? preAdvise.getTolerancePlus().intValue() : 0);
				entity.setTolerance2(
						preAdvise.getToleranceMinus() != null ? preAdvise.getToleranceMinus().intValue() : 0);
				entity.setAdditionalAmountsCovered(preAdvise.getAdditionalAmountsCovered());
				entity.setDispatchFrom(preAdvise.getDispatchFrom());
				entity.setPortOfLoading(preAdvise.getPortOfLoading());
				entity.setPortOfDischarge(preAdvise.getPortOfDischarge());
				entity.setPlaceOfFinalDestination(preAdvise.getPlaceOfFinalDestination());
				entity.setLatestDateOfShipment(preAdvise.getLatestDateOfShipment());
				entity.setShipmentPeriod(preAdvise.getShipmentPeriod());
				entity.setGoodDesription(preAdvise.getGoodsDescription());

				// Additional info fields
				entity.setSenderToReceiverInfo(preAdvise.getSenderToReceiverInfo());
			}
			entity.setLcMaster(lcMaster);
			entity.setRegister(register);

		} else {
			event.rerouteTo(LcMasterView.class);
		}

	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		Logger.info("ImportLetterOfCreditIssueForm attached to UI");

		System.out.println(entity.toString());

		binder.forField(currencyCodeField).bind(ImportLetterOfCreditIssue::getCurrencyCode,
				ImportLetterOfCreditIssue::setCurrencyCode);
		binder.setBean(entity);
		documentaryCrNoField.setReadOnly(true);
		applicantBankField.setValue(setPartyDetails(entity.getApplicantBank()));
		applicantField.setValue(setPartyDetails(entity.getApplicant()));

		System.out.println("Beneficiary is null: " + (entity.getBeneficiary() == null));
		beneficiaryField.setValue(setPartyDetails(entity.getBeneficiary()));

		advisingBankField.setValue(setPartyDetails(entity.getAdvisingBank()));
		requestedConfirmationPartyField.setValue(setPartyDetails(entity.getRequestedConfirmationParty()));
		reimbursingBankPartyField.setValue(setPartyDetails(entity.getReimbursingBankParty()));
		advThrBankPartyField.setValue(setPartyDetails(entity.getAdvThrBankParty()));

		draweeField.setValue(setPartyDetails(entity.getDrawee()));
		availableWithByField.setValue(setPartyDetails(entity.getAvailableWithByParty()));

		Logger.info("onAttach completed");
	}

	private String setPartyDetails(Party party) {
		String details = "";
		if (null != party) {
			if (party.getAccount() != null && !party.getAccount().isEmpty())
				details = details + "/" + party.getAccount() + "\r\n";
			if (party.getBicCode() != null && !party.getBicCode().isEmpty())
				details = details + party.getBicCode() + "\r\n";
			if (party.getName() != null && !party.getName().isEmpty())
				details = details + party.getName() + "\r\n";
			if (party.getAddress1() != null && !party.getAddress1().isEmpty())
				details = details + party.getAddress1() + "\r\n";
			if (party.getAddress2() != null && !party.getAddress2().isEmpty())
				details = details + party.getAddress2() + "\r\n";
			if (party.getAddress3() != null && !party.getAddress3().isEmpty())
				details = details + party.getAddress3() + "\r\n";
		}

		return details;
	}

	private void addListeners() {
		searchApplicantBankPartyBtn.addClickListener(ev -> {
			Dialog dg = new Dialog();
			partCrudView.closeButton.setVisible(false);
			partCrudView.setFromDialogue(true);
			partCrudView.swiftPartyType.setItems("A", "D");
			Button closeBtn = new Button("Close", new Icon(VaadinIcon.CLOSE_CIRCLE_O));
			closeBtn.addClickListener(e -> dg.close());
			Button confirmBtn = partCrudView.confirmButton;
			confirmBtn.setVisible(true);
			confirmBtn.addClickListener(e -> {
				applicantBankField.setValue("");
				Party party = partCrudView.getEntity();
				String applicantDetails = setPartyDetails(party);
				applicantBankField.setValue(applicantDetails);
				binder.getBean().setApplicantBank(party);
				entity.setApplicantBank(party);
			});
			dg.addDetachListener(e -> partCrudView.hl.remove(confirmBtn, closeBtn));
			dg.addAttachListener(e -> {
				if (entity.getApplicantBank() != null)
					partCrudView.getBinder().setBean(entity.getApplicantBank());
				else
					partCrudView.getBinder().setBean(new Party());
			});
			partCrudView.hl.add(confirmBtn, closeBtn);
			dg.add(partCrudView);
			dg.open();
		});
		searchApplicantPartyBtn.addClickListener(ev -> {
			Dialog dg = new Dialog();
			partCrudView.closeButton.setVisible(false);
			partCrudView.setFromDialogue(true);
			partCrudView.swiftPartyType.setItems("");
			Button closeBtn = new Button("Close", new Icon(VaadinIcon.CLOSE_CIRCLE_O));
			closeBtn.addClickListener(e -> dg.close());
			Button confirmBtn = partCrudView.confirmButton;
			confirmBtn.setVisible(true);
			confirmBtn.addClickListener(e -> {
				applicantField.setValue("");
				Party party = partCrudView.getEntity();
				String applicantDetails = setPartyDetails(party);
				applicantField.setValue(applicantDetails);
				binder.getBean().setApplicant(party);
				entity.setApplicant(party);
			});
			dg.addDetachListener(e -> partCrudView.hl.remove(confirmBtn, closeBtn));
			dg.addAttachListener(e -> {
				if (entity.getApplicant() != null)
					partCrudView.getBinder().setBean(entity.getApplicant());
				else
					partCrudView.getBinder().setBean(new Party());
			});
			partCrudView.hl.add(confirmBtn, closeBtn);
			dg.add(partCrudView);
			dg.open();
		});
		searchBeneficiaryBtn.addClickListener(ev -> {
			Dialog dg = new Dialog();
			partCrudView.closeButton.setVisible(false);
			partCrudView.setFromDialogue(true);
			partCrudView.swiftPartyType.setItems("");
			Button closeBtn = new Button("Close", new Icon(VaadinIcon.CLOSE_CIRCLE_O));
			closeBtn.addClickListener(e -> dg.close());
			Button confirmBtn = partCrudView.confirmButton;
			confirmBtn.setVisible(true);
			confirmBtn.addClickListener(e -> {
				beneficiaryField.setValue("");
				Party party = partCrudView.getEntity();
				String details = setPartyDetails(party);
				beneficiaryField.setValue(details);
				binder.getBean().setBeneficiary(party);
				entity.setBeneficiary(party);
			});
			dg.addDetachListener(e -> partCrudView.hl.remove(confirmBtn, closeBtn));
			dg.addAttachListener(e -> {
				if (entity.getBeneficiary() != null)
					partCrudView.getBinder().setBean(entity.getBeneficiary());
				else
					partCrudView.getBinder().setBean(new Party());
			});
			partCrudView.hl.add(confirmBtn, closeBtn);
			dg.add(partCrudView);
			dg.open();
		});
		searchAdvisingBankPartyBtn.addClickListener(ev -> {
			Dialog dg = new Dialog();
			partCrudView.closeButton.setVisible(false);
			partCrudView.setFromDialogue(true);
			partCrudView.swiftPartyType.setItems("");
			Button closeBtn = new Button("Close", new Icon(VaadinIcon.CLOSE_CIRCLE_O));
			closeBtn.addClickListener(e -> dg.close());
			Button confirmBtn = partCrudView.confirmButton;
			confirmBtn.setVisible(true);
			confirmBtn.addClickListener(e -> {
				advisingBankField.setValue("");
				Party party = partCrudView.getEntity();
				String details = setPartyDetails(party);
				advisingBankField.setValue(details);
				binder.getBean().setAdvisingBank(party);
				entity.setAdvisingBank(party);
			});
			dg.addDetachListener(e -> partCrudView.hl.remove(confirmBtn, closeBtn));
			dg.addAttachListener(e -> {
				if (entity.getAdvisingBank() != null)
					partCrudView.getBinder().setBean(entity.getAdvisingBank());
				else
					partCrudView.getBinder().setBean(new Party());
			});
			partCrudView.hl.add(confirmBtn, closeBtn);
			dg.add(partCrudView);

			dg.open();
		});
		searchRequestedConfirmationPartyBtn.addClickListener(ev -> {
			Dialog dg = new Dialog();
			partCrudView.closeButton.setVisible(false);
			partCrudView.setFromDialogue(true);
			partCrudView.swiftPartyType.setItems("A", "D");
			Button closeBtn = new Button("Close", new Icon(VaadinIcon.CLOSE_CIRCLE_O));
			closeBtn.addClickListener(e -> dg.close());
			Button confirmBtn = partCrudView.confirmButton;
			confirmBtn.setVisible(true);
			confirmBtn.addClickListener(e -> {
				requestedConfirmationPartyField.setValue("");
				Party party = partCrudView.getEntity();
				String details = setPartyDetails(party);
				requestedConfirmationPartyField.setValue(details);
				binder.getBean().setRequestedConfirmationParty(party);
				entity.setRequestedConfirmationParty(party);
			});
			dg.addDetachListener(e -> partCrudView.hl.remove(confirmBtn, closeBtn));
			dg.addAttachListener(e -> {
				if (entity.getRequestedConfirmationParty() != null)
					partCrudView.getBinder().setBean(entity.getRequestedConfirmationParty());
				else
					partCrudView.getBinder().setBean(new Party());
			});
			partCrudView.hl.add(confirmBtn, closeBtn);
			dg.add(partCrudView);
			dg.open();
		});
		searchReimbursingBankPartyBtn.addClickListener(ev -> {
			Dialog dg = new Dialog();
			partCrudView.closeButton.setVisible(false);
			partCrudView.setFromDialogue(true);
			partCrudView.swiftPartyType.setItems("A", "D");
			Button closeBtn = new Button("Close", new Icon(VaadinIcon.CLOSE_CIRCLE_O));
			closeBtn.addClickListener(e -> dg.close());
			Button confirmBtn = partCrudView.confirmButton;
			confirmBtn.setVisible(true);
			confirmBtn.addClickListener(e -> {
				reimbursingBankPartyField.setValue("");
				Party party = partCrudView.getEntity();
				String details = setPartyDetails(party);
				reimbursingBankPartyField.setValue(details);
				binder.getBean().setReimbursingBankParty(party);
				entity.setReimbursingBankParty(party);
			});
			dg.addDetachListener(e -> partCrudView.hl.remove(confirmBtn, closeBtn));
			dg.addAttachListener(e -> {
				if (entity.getReimbursingBankParty() != null)
					partCrudView.getBinder().setBean(entity.getReimbursingBankParty());
				else
					partCrudView.getBinder().setBean(new Party());
			});
			partCrudView.hl.add(confirmBtn, closeBtn);
			dg.add(partCrudView);
			dg.open();
		});
		searchAdvThrBankPartyBtn.addClickListener(ev -> {
			Dialog dg = new Dialog();
			partCrudView.closeButton.setVisible(false);
			partCrudView.setFromDialogue(true);
			partCrudView.swiftPartyType.setItems("A", "B", "D");
			Button closeBtn = new Button("Close", new Icon(VaadinIcon.CLOSE_CIRCLE_O));
			closeBtn.addClickListener(e -> dg.close());
			Button confirmBtn = partCrudView.confirmButton;
			confirmBtn.setVisible(true);
			confirmBtn.addClickListener(e -> {
				advThrBankPartyField.setValue("");
				Party party = partCrudView.getEntity();
				String details = setPartyDetails(party);
				advThrBankPartyField.setValue(details);
				binder.getBean().setAdvThrBankParty(party);
				entity.setAdvThrBankParty(party);
			});
			dg.addDetachListener(e -> partCrudView.hl.remove(confirmBtn, closeBtn));
			dg.addAttachListener(e -> {
				if (entity.getAdvThrBankParty() != null)
					partCrudView.getBinder().setBean(entity.getAdvThrBankParty());
				else
					partCrudView.getBinder().setBean(new Party());
			});
			partCrudView.hl.add(confirmBtn, closeBtn);
			dg.add(partCrudView);
			dg.open();
		});
		searchDraweePartyBtn.addClickListener(ev -> {
			Dialog dg = new Dialog();
			partCrudView.closeButton.setVisible(false);
			partCrudView.setFromDialogue(true);
			partCrudView.swiftPartyType.setItems("A", "D");
			Button closeBtn = new Button("Close", new Icon(VaadinIcon.CLOSE_CIRCLE_O));
			closeBtn.addClickListener(e -> dg.close());
			Button confirmBtn = partCrudView.confirmButton;
			confirmBtn.setVisible(true);
			confirmBtn.addClickListener(e -> {
				draweeField.setValue("");
				Party party = partCrudView.getEntity();
				String details = setPartyDetails(party);
				draweeField.setValue(details);
				binder.getBean().setDrawee(party);
				entity.setDrawee(party);
			});
			dg.addDetachListener(e -> partCrudView.hl.remove(confirmBtn, closeBtn));
			dg.addAttachListener(e -> {
				if (entity.getDrawee() != null)
					partCrudView.getBinder().setBean(entity.getDrawee());
				else
					partCrudView.getBinder().setBean(new Party());
			});
			partCrudView.hl.add(confirmBtn, closeBtn);
			dg.add(partCrudView);
			dg.open();
		});
		searchAvailableWithByPartyBtn.addClickListener(ev -> {
			Dialog dg = new Dialog();
			partCrudView.closeButton.setVisible(false);
			partCrudView.setFromDialogue(true);
			partCrudView.swiftPartyType.setItems("A", "D");
			Button closeBtn = new Button("Close", new Icon(VaadinIcon.CLOSE_CIRCLE_O));
			closeBtn.addClickListener(e -> dg.close());
			Button confirmBtn = partCrudView.confirmButton;
			confirmBtn.setVisible(true);
			confirmBtn.addClickListener(e -> {
				availableWithByField.setValue("");
				Party party = partCrudView.getEntity();
				String details = setPartyDetails(party);
				availableWithByField.setValue(details);
				binder.getBean().setAvailableWithByParty(party);
				entity.setAvailableWithByParty(party);
			});
			dg.addDetachListener(e -> partCrudView.hl.remove(confirmBtn, closeBtn));
			dg.addAttachListener(e -> {
				if (entity.getAvailableWithByParty() != null)
					partCrudView.getBinder().setBean(entity.getAvailableWithByParty());
				else
					partCrudView.getBinder().setBean(new Party());
			});
			partCrudView.hl.add(confirmBtn, closeBtn);
			dg.add(partCrudView);
			dg.open();
		});
	}
}

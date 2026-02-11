package com.more.app.base.ui.product;

import com.more.app.base.converters.ConfirmationInstructionConverter;
import com.more.app.base.converters.DoubleToBigDecimalConverter;
import com.more.app.base.converters.DoubleToIntegerConverter;
import com.more.app.base.converters.FormOfDocumentaryCreditConverter;
import com.more.app.entity.product.ImportLetterOfCreditIssue;
import com.more.app.entity.product.Party;
import com.more.app.util.annotations.UILabelUtil;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout.Orientation;
import com.vaadin.flow.component.splitlayout.SplitLayoutVariant;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import ch.carnet.kasparscherrer.EmptyFormLayoutItem;

public class LcIssueCrudView extends Dialog
{
	private ImportLetterOfCreditIssue entity;

	private LcIssueCrudView view;
	private Binder<ImportLetterOfCreditIssue> binder = new Binder<>(ImportLetterOfCreditIssue.class);
	private SplitLayout layout = new SplitLayout();

	public LcIssueCrudView(ImportLetterOfCreditIssue entity)
	{
		this.entity = entity;
		this.view = this;
		setCloseOnEsc(true);
		setCloseOnOutsideClick(false);

		setHeight("900px");
		setWidth("1800px");

		initializeComponents(entity);
		binder.readBean(entity);

		VerticalLayout vlp = new VerticalLayout();
		// vl.setSpacing(true);

		Button confirmButton = new Button("Save Record", event ->
		{
			try
			{
				binder.writeBean(entity);
				if (binder.isValid())
				{
					//FacadeFactory.getFacade().store(entity);
					binder.readBean(entity);
				}
				else
				{
					Dialog dg = new Dialog();
					dg.add(new Text("Some Fields are Invalid"));

					dg.open();
				}

			} catch (Exception e)
			{
				e.printStackTrace();
			}
		});
		Button cancelButton = new Button("Close Dialog", event ->
		{
			close();
		});

		confirmButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

		cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

		vlp.add(confirmButton, cancelButton);
		vlp.setAlignItems(Alignment.START);

		VerticalLayout vls = new VerticalLayout();
		// vls.setMargin(true);
		vls.setSpacing(true);

		H4 title = new H4("Letter Of Credit Issue");
		// title.setTitle("OutWard Customer Payment");

		formOfDocumentaryCrSF.setLabel(UILabelUtil.getFieldLabel(entity, "formOfDocumentaryCr"));
		formOfDocumentaryCrSF.setPlaceholder("Select an option");
		formOfDocumentaryCrSF.setEmptySelectionCaption("Select an option");
		formOfDocumentaryCrSF.setEmptySelectionAllowed(true);
		formOfDocumentaryCrSF.setRequiredIndicatorVisible(true);

		documentaryCrNoTF.setLabel(UILabelUtil.getFieldLabel(entity, "documentaryCrNo"));
		documentaryCrNoTF.setMaxLength(16);
		documentaryCrNoTF.setRequired(true);

		referenceToPreAdviceTF.setLabel(UILabelUtil.getFieldLabel(entity, "referenceToPreAdvice"));
		referenceToPreAdviceTF.setMaxLength(16);

		dateOfIssueDP.setLabel(UILabelUtil.getFieldLabel(entity, "dateOfIssue"));
		dateOfIssueDP.setClearButtonVisible(true);
		dateOfIssueDP.setRequired(true);

		applicableRulesSF.setLabel(UILabelUtil.getFieldLabel(entity, "applicableRules"));
		applicableRulesSF.setPlaceholder("Select an option");
		applicableRulesSF.setEmptySelectionCaption("Select an option");
		applicableRulesSF.setEmptySelectionAllowed(true);
		applicableRulesSF.setRequiredIndicatorVisible(true);

		applicableRulesNarrativeTF.setLabel(UILabelUtil.getFieldLabel(entity, "applicableRulesNarrative"));
		applicableRulesNarrativeTF.setMaxLength(35);

		dateOfExpiryDP.setLabel(UILabelUtil.getFieldLabel(entity, "dateOfExpiry"));
		dateOfExpiryDP.setClearButtonVisible(true);
		dateOfExpiryDP.setRequired(true);

		placeOfExpiryTF.setLabel(UILabelUtil.getFieldLabel(entity, "placeOfExpiry"));
		placeOfExpiryTF.setMaxLength(29);
		placeOfExpiryTF.setRequired(true);

		confirmationInstructionSF.setLabel(UILabelUtil.getFieldLabel(entity, "confirmationInstructions"));
		confirmationInstructionSF.setPlaceholder("Select an option");
		confirmationInstructionSF.setEmptySelectionCaption("Select an option");
		confirmationInstructionSF.setEmptySelectionAllowed(true);
		confirmationInstructionSF.setRequiredIndicatorVisible(true);

		currencyCodeSF.setLabel(UILabelUtil.getFieldLabel(entity, "currencyCode"));

		// The empty selection item is the first item that maps to an null item.
		// As the item is not selectable, using it also as placeholder
		currencyCodeSF.setPlaceholder("Select an option");
		currencyCodeSF.setEmptySelectionCaption("Select an option");
		currencyCodeSF.setEmptySelectionAllowed(true);
		currencyCodeSF.setRequiredIndicatorVisible(true);

		amountOfDocumentaryCreditNF.setLabel(UILabelUtil.getFieldLabel(entity, "amountOfDocumentaryCredit"));
		//amountOfDocumentaryCreditNF.setPreventInvalidInput(true);
		amountOfDocumentaryCreditNF.setInvalid(true);
		amountOfDocumentaryCreditNF.setErrorMessage("Invalid Entry, Enter an amount");
		//amountOfDocumentaryCreditNF.setMaxLength(15);
		amountOfDocumentaryCreditNF.setMinWidth("280px");

		HorizontalLayout lcamthl = new HorizontalLayout();
		lcamthl.add(currencyCodeSF, amountOfDocumentaryCreditNF);
		lcamthl.setSizeFull();

		lcamthl.setVerticalComponentAlignment(Alignment.BASELINE, currencyCodeSF, amountOfDocumentaryCreditNF);

		tolerance1NF.setLabel(UILabelUtil.getFieldLabel(entity, "tolerance1"));
		//tolerance1NF.setPreventInvalidInput(true);
		tolerance1NF.setInvalid(true);
		tolerance1NF.setErrorMessage("Invalid Entry, Enter an interger");
		// \\d{6}
		//tolerance1NF.setMaxLength(2);
		tolerance1NF.setMaxWidth("200px");
		//tolerance1NF.setPattern("\\\\d{2}");

		tolerance2NF.setLabel(UILabelUtil.getFieldLabel(entity, "tolerance2"));
		//tolerance2NF.setPreventInvalidInput(true);
		tolerance2NF.setInvalid(true);
		tolerance2NF.setErrorMessage("Invalid Entry, Enter an interger");
		//tolerance2NF.setMaxLength(2);
		tolerance2NF.setMinWidth("200px");

		HorizontalLayout tolerancehl = new HorizontalLayout();
		tolerancehl.add(tolerance1NF, tolerance2NF);
		tolerancehl.setSizeFull();

		tolerancehl.setVerticalComponentAlignment(Alignment.BASELINE, tolerance1NF, tolerance2NF);

		additionalAmountsCoveredTA.setLabel(UILabelUtil.getFieldLabel(entity, "additionalAmountsCovered"));

		draftsatTA.setLabel(UILabelUtil.getFieldLabel(entity, "draftsAt"));

		mixedPaymentTA.setLabel(UILabelUtil.getFieldLabel(entity, "mixedPaymentDetails"));

		negotiationDefferdPayTA.setLabel(UILabelUtil.getFieldLabel(entity, "negotiationDeferredPaymentDetails"));

		partialShipmentSF.setLabel(UILabelUtil.getFieldLabel(entity, "partialShipments"));
		partialShipmentSF.setPlaceholder("Select an option");
		partialShipmentSF.setEmptySelectionCaption("Select an option");
		partialShipmentSF.setEmptySelectionAllowed(true);
		// partialShipmentSF.setRequiredIndicatorVisible(true);

		
		transShipmentSF.setLabel(UILabelUtil.getFieldLabel(entity, "transhipments"));

		// The empty selection item is the first item that maps to an null item.
		// As the item is not selectable, using it also as placeholder
		transShipmentSF.setPlaceholder("Select an option");
		transShipmentSF.setEmptySelectionCaption("Select an option");
		transShipmentSF.setEmptySelectionAllowed(true);
		// transShipmentSF.setRequiredIndicatorVisible(true);

		// dispatchFromTF, porOfLoadingTF,portOfDischargeTF, finalDestinationTF
		
		dispatchFromTF.setLabel(UILabelUtil.getFieldLabel(entity, "dispatchFrom"));
		dispatchFromTF.setMaxLength(65);

		porOfLoadingTF.setLabel(UILabelUtil.getFieldLabel(entity,"portOfLoading"));
		porOfLoadingTF.setMaxLength(65);

		portOfDischargeTF.setLabel(UILabelUtil.getFieldLabel(entity,"portOfDischarge"));
		portOfDischargeTF.setMaxLength(65);

		finalDestinationTF.setLabel(UILabelUtil.getFieldLabel(entity,"placeOfFinalDestination"));
		finalDestinationTF.setMaxLength(65);

		// dateOfIssueDP = new DatePicker();
		latestShipmentDF.setLabel(UILabelUtil.getFieldLabel(entity,"latestDateOfShipment"));
		latestShipmentDF.setClearButtonVisible(true);

		shipmentPeriodTA.setLabel(UILabelUtil.getFieldLabel(entity,"shipmentPeriod"));
		goodsDescriptionTA.setLabel(UILabelUtil.getFieldLabel(entity,"goodDesription"));
		documentsRequiredTA.setLabel(UILabelUtil.getFieldLabel(entity,"documentsRequired"));
		additionalConditionTA.setLabel(UILabelUtil.getFieldLabel(entity,"additionalConditions"));
		specialConditionsForBenTA.setLabel(UILabelUtil.getFieldLabel(entity,"specialPaymentConditionBeneficiary"));
		specialConditionForRBTA.setLabel(UILabelUtil.getFieldLabel(entity,"specialPaymentConditionReceivingBank"));
		chargesTA.setLabel(UILabelUtil.getFieldLabel(entity,"charges"));

		// chargesTA.getElement().getStyle().set("", "");

		// instructionsToPayBankTA, senderToReceiverInfoTA

		// periodOfPresentationDaysNF periodOfPresentationNarrativeTF
		periodOfPresentationDaysNF.setLabel(UILabelUtil.getFieldLabel(entity,"periodOfPresentationDays"));
		//periodOfPresentationDaysNF.setPreventInvalidInput(true);
		//periodOfPresentationDaysNF.setMaxLength(3);
		periodOfPresentationDaysNF.setMaxWidth("150px");

		periodOfPresentationNarrativeTF.setLabel(UILabelUtil.getFieldLabel(entity,"periodOfPresentationNarrative"));
		periodOfPresentationNarrativeTF.setMaxLength(35);
		periodOfPresentationNarrativeTF.setWidth("400px");

		HorizontalLayout periodOfPresentationhl = new HorizontalLayout();
		periodOfPresentationhl.add(periodOfPresentationDaysNF, periodOfPresentationNarrativeTF);
		periodOfPresentationhl.setSizeFull();

		periodOfPresentationhl.setVerticalComponentAlignment(Alignment.BASELINE, periodOfPresentationDaysNF,
				periodOfPresentationNarrativeTF);

		basicdtlsFL.setMaxWidth("1000px");
		// basicdtlsFL.setMaxWidth("100px");
		basicdtlsFL.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));

		basicdtlsFL.add(documentaryCrNoTF, referenceToPreAdviceTF, formOfDocumentaryCrSF, dateOfIssueDP,
				applicableRulesSF, applicableRulesNarrativeTF, dateOfExpiryDP, placeOfExpiryTF,
				confirmationInstructionSF, new EmptyFormLayoutItem(), lcamthl, tolerancehl);

		lcdtlspanel.add(basicdtlsFL);

		//applicantDtlsPanel.add(applicantPDF);

		//beneficiaryDtlsPanel.add(beneficiaryPDF);

		//availableWithDtlsPanel.add(availableWithByPDF);
		//draweeDtls.add(draweePDF);

		shipingDetailsFL.setMaxWidth("1000px");
		shipingDetailsFL.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));

		shipingDetailsFL.add(additionalAmountsCoveredTA, draftsatTA, mixedPaymentTA, negotiationDefferdPayTA,
				partialShipmentSF, transShipmentSF, dispatchFromTF, porOfLoadingTF, portOfDischargeTF,
				finalDestinationTF, latestShipmentDF, shipmentPeriodTA, goodsDescriptionTA, documentsRequiredTA,
				additionalConditionTA, specialConditionsForBenTA, specialConditionForRBTA, chargesTA,
				periodOfPresentationhl);

		shipingDtls.add(shipingDetailsFL);

		// requestedConfirmationPartyPDF, reimbursingBankPartyPDF,advThrBankPartyPDF

		//requestedConfirmationPartyDtls.add(requestedConfirmationPartyPDF);
		//reimbursingBankPartyDtls.add(reimbursingBankPartyPDF);
		//advThrBankPartyDtls.add(advThrBankPartyPDF);

		instructionsToPayBankTA.setLabel(UILabelUtil.getFieldLabel(entity,"instructionsToPayBank"));
		senderToReceiverInfoTA.setLabel(UILabelUtil.getFieldLabel(entity,"senderToReceiverInfo"));

		otherDtlsPanelFL.setMaxWidth("1000px");
		// otherDtlsPanelFL.setSizeFull();
		otherDtlsPanelFL.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));

		otherDtlsPanelFL.add(instructionsToPayBankTA, senderToReceiverInfoTA);
		// setColspan(instructionsToPayBankTA, 1);

		otherDtlsPanel.add(otherDtlsPanelFL);

		vls.add(title, lcdtlspanel, applicantDtlsPanel, beneficiaryDtlsPanel, availableWithDtlsPanel, draweeDtls,
				shipingDtls, requestedConfirmationPartyDtls, reimbursingBankPartyDtls, advThrBankPartyDtls,
				otherDtlsPanel);

		layout.setOrientation(Orientation.HORIZONTAL);
		layout.addThemeVariants(SplitLayoutVariant.LUMO_SMALL);
		layout.setSplitterPosition(10);
		layout.setSizeFull();

		layout.addToPrimary(vlp);

		layout.addToSecondary(vls);

		add(layout);

		applicantDtlsPanel.setOpened(false);
		beneficiaryDtlsPanel.setOpened(false);
		availableWithDtlsPanel.setOpened(false);
		draweeDtls.setOpened(false);
		shipingDtls.setOpened(false);
		requestedConfirmationPartyDtls.setOpened(false);
		reimbursingBankPartyDtls.setOpened(false);
		advThrBankPartyDtls.setOpened(false);
		otherDtlsPanel.setOpened(false);

	}

	private TextField documentaryCrNoTF, referenceToPreAdviceTF, applicableRulesNarrativeTF, placeOfExpiryTF,
			dispatchFromTF, porOfLoadingTF, portOfDischargeTF, finalDestinationTF, periodOfPresentationNarrativeTF;
	private DatePicker dateOfIssueDP, dateOfExpiryDP, latestShipmentDF;
	private Select formOfDocumentaryCrSF, applicableRulesSF, confirmationInstructionSF, currencyCodeSF,
			partialShipmentSF, transShipmentSF;
	private NumberField amountOfDocumentaryCreditNF, tolerance1NF, tolerance2NF, periodOfPresentationDaysNF;
	//private PartyDetailsCrudView applicantPDF, beneficiaryPDF, availableWithByPDF, draweePDF,
	//		requestedConfirmationPartyPDF, reimbursingBankPartyPDF, advThrBankPartyPDF;
	private TextArea additionalAmountsCoveredTA, draftsatTA, mixedPaymentTA, negotiationDefferdPayTA, shipmentPeriodTA,
			goodsDescriptionTA, documentsRequiredTA, additionalConditionTA, specialConditionsForBenTA,
			specialConditionForRBTA, chargesTA, instructionsToPayBankTA, senderToReceiverInfoTA;

	private CustomAccordionPanel lcdtlspanel = new CustomAccordionPanel("LC Details");

	private CustomAccordionPanel otherDtlsPanel = new CustomAccordionPanel("Other Details");
	private CustomAccordionPanel shipingDtls = new CustomAccordionPanel("Shiping Details");
	private CustomAccordionPanel applicantDtlsPanel, beneficiaryDtlsPanel, availableWithDtlsPanel, draweeDtls,
			requestedConfirmationPartyDtls, reimbursingBankPartyDtls, advThrBankPartyDtls;

	private FormLayout shipingDetailsFL = new FormLayout();
	private FormLayout otherDtlsPanelFL = new FormLayout();
	private FormLayout basicdtlsFL = new FormLayout();

	private void initializeComponents(ImportLetterOfCreditIssue entity)
	{

		formOfDocumentaryCrSF = new Select();
		// This should be populated from the Currency Entity code variable.
		formOfDocumentaryCrSF.setItems("Irrevocable", "Irrevocable and Transferable", "Irrevocable Standby LC",
				"Irrevocable Transferable Standby LC");

		binder.forField(formOfDocumentaryCrSF).withConverter(new FormOfDocumentaryCreditConverter())
				.bind("formOfDocumentaryCr");

		documentaryCrNoTF = new TextField();
		binder.forField(documentaryCrNoTF).bind("documentaryCrNo");

		referenceToPreAdviceTF = new TextField();
		binder.forField(referenceToPreAdviceTF).bind("referenceToPreAdvice");

		dateOfIssueDP = new DatePicker();
		binder.forField(dateOfIssueDP).bind("dateOfIssue");

		applicableRulesNarrativeTF = new TextField();
		binder.forField(applicableRulesNarrativeTF).bind("applicableRulesNarrative");

		applicableRulesSF = new Select();
		applicableRulesSF.setItems("EUCP LATEST VERSION", "EUCPURR LATEST VERSION", "ISP LATEST VERSION",
				"UCP LATEST VERSION", "UCPURR LATEST VERSION", "OTHR");
		binder.forField(applicableRulesSF).bind("applicableRules");

		dateOfExpiryDP = new DatePicker();
		binder.forField(dateOfExpiryDP).bind("dateOfExpiry");

		placeOfExpiryTF = new TextField();
		binder.forField(placeOfExpiryTF).bind("placeOfExpiry");

		Party applicantParty = entity.getApplicant() != null ? entity.getApplicant() : new Party();
		//applicantPDF = new PartyDetailsCrudView(applicantParty);
		//binder.forField(applicantPDF).bind("applicant");
		applicantDtlsPanel = new CustomAccordionPanel(UILabelUtil.getFieldLabel(entity, "applicant"));

		Party beneficiaryParty = entity.getBeneficiary() != null ? entity.getBeneficiary() : new Party();
		//beneficiaryPDF = new PartyDetailsCrudView(beneficiaryParty);
		//binder.forField(beneficiaryPDF).bind("beneficiary");
		beneficiaryDtlsPanel = new CustomAccordionPanel(UILabelUtil.getFieldLabel(entity, "beneficiary"));

		Party availableWithBy = entity.getAvailableWithBy() != null ? entity.getAvailableWithBy() : new Party();
		//availableWithByPDF = new PartyDetailsCrudView(availableWithBy);
		//binder.forField(availableWithByPDF).bind("availableWithBy");
		availableWithDtlsPanel = new CustomAccordionPanel(UILabelUtil.getFieldLabel(entity, "availableWithBy"));

		confirmationInstructionSF = new Select();
		confirmationInstructionSF.setItems("Confirm", "May Add", "Without");
		binder.forField(confirmationInstructionSF).withConverter(new ConfirmationInstructionConverter())
				.bind("confirmationInstructions");

		currencyCodeSF = new Select();
		currencyCodeSF.setItems("USD", "GBP", "NGN");
		binder.forField(currencyCodeSF).bind("currencyCode");

		amountOfDocumentaryCreditNF = new NumberField();
		binder.forField(amountOfDocumentaryCreditNF).withConverter(new DoubleToBigDecimalConverter())
				.bind("amountOfDocumentaryCredit");

		tolerance1NF = new NumberField();
		binder.forField(tolerance1NF).withConverter(new DoubleToIntegerConverter()).bind("tolerance1");

		tolerance2NF = new NumberField();
		binder.forField(tolerance2NF).withConverter(new DoubleToIntegerConverter()).bind("tolerance2");

		Party drawee = entity.getDrawee() != null ? entity.getDrawee() : new Party();
		//draweePDF = new PartyDetailsCrudView(drawee);
		//binder.forField(draweePDF).bind("drawee");
		draweeDtls = new CustomAccordionPanel(UILabelUtil.getFieldLabel(entity, "drawee"));

		additionalAmountsCoveredTA = new TextArea();
		binder.forField(additionalAmountsCoveredTA).bind("additionalAmountsCovered");

		draftsatTA = new TextArea();
		binder.forField(draftsatTA).bind("draftsAt");

		mixedPaymentTA = new TextArea();
		binder.forField(mixedPaymentTA).bind("mixedPaymentDetails");

		negotiationDefferdPayTA = new TextArea();
		binder.forField(negotiationDefferdPayTA).bind("negotiationDeferredPaymentDetails");

		partialShipmentSF = new Select();
		partialShipmentSF.setItems("ALLOWED", "CONDITIONAL", "NOT ALLOWED");
		binder.forField(partialShipmentSF).bind("partialShipments");

		transShipmentSF = new Select();
		transShipmentSF.setItems("ALLOWED", "CONDITIONAL", "NOT ALLOWED");
		binder.forField(transShipmentSF).bind("transhipments");

		dispatchFromTF = new TextField();
		binder.forField(dispatchFromTF).bind("dispatchFrom");

		porOfLoadingTF = new TextField();
		binder.forField(porOfLoadingTF).bind("portOfLoading");

		portOfDischargeTF = new TextField();
		binder.forField(portOfDischargeTF).bind("portOfDischarge");

		finalDestinationTF = new TextField();
		binder.forField(finalDestinationTF).bind("placeOfFinalDestination");

		latestShipmentDF = new DatePicker();
		binder.forField(latestShipmentDF).bind("latestDateOfShipment");

		shipmentPeriodTA = new TextArea();
		binder.forField(shipmentPeriodTA).bind("shipmentPeriod");

		goodsDescriptionTA = new TextArea();
		binder.forField(goodsDescriptionTA).bind("goodDesription");

		documentsRequiredTA = new TextArea();
		binder.forField(documentsRequiredTA).bind("documentsRequired");

		additionalConditionTA = new TextArea();
		binder.forField(additionalConditionTA).bind("additionalConditions");

		specialConditionsForBenTA = new TextArea();
		binder.forField(specialConditionsForBenTA).bind("specialPaymentConditionBeneficiary");

		specialConditionForRBTA = new TextArea();
		binder.forField(specialConditionForRBTA).bind("specialPaymentConditionReceivingBank");

		chargesTA = new TextArea();
		binder.forField(chargesTA).bind("charges");

		periodOfPresentationDaysNF = new NumberField();
		binder.forField(periodOfPresentationDaysNF).withConverter(new DoubleToIntegerConverter())
				.bind("periodOfPresentationDays");

		periodOfPresentationNarrativeTF = new TextField();
		binder.forField(periodOfPresentationNarrativeTF).bind("periodOfPresentationNarrative");

		Party requestedConfirmationParty = entity.getRequestedConfirmationParty() != null
				? entity.getRequestedConfirmationParty()
				: new Party();
		//requestedConfirmationPartyPDF = new PartyDetailsCrudView(requestedConfirmationParty);
		//binder.forField(requestedConfirmationPartyPDF).bind("requestedConfirmationParty");
		requestedConfirmationPartyDtls = new CustomAccordionPanel(
				UILabelUtil.getFieldLabel(entity, "requestedConfirmationParty"));

		Party reimbursingBankParty = entity.getReimbursingBankParty() != null ? entity.getReimbursingBankParty()
				: new Party();
		//reimbursingBankPartyPDF = new PartyDetailsCrudView(reimbursingBankParty);
		//binder.forField(reimbursingBankPartyPDF).bind("reimbursingBankParty");
		reimbursingBankPartyDtls = new CustomAccordionPanel(UILabelUtil.getFieldLabel(entity, "reimbursingBankParty"));

		Party advThrBankParty = entity.getAdvThrBankParty() != null ? entity.getAdvThrBankParty() : new Party();
		//advThrBankPartyPDF = new PartyDetailsCrudView(advThrBankParty);
		//binder.forField(advThrBankPartyPDF).bind("advThrBankParty");
		advThrBankPartyDtls = new CustomAccordionPanel(UILabelUtil.getFieldLabel(entity, "advThrBankParty"));

		instructionsToPayBankTA = new TextArea();
		binder.forField(instructionsToPayBankTA).bind("instructionsToPayBank");

		senderToReceiverInfoTA = new TextArea();
		binder.forField(senderToReceiverInfoTA).bind("senderToReceiverInfo");
	}

	private void setColspan(Component component, int colspan)
	{
		component.getElement().setAttribute("colspan", Integer.toString(colspan));
	}

}

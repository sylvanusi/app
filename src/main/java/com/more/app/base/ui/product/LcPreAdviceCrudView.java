package com.more.app.base.ui.product;

import com.more.app.base.converters.DoubleToIntegerConverter;
import com.more.app.base.converters.FormOfDocumentaryCreditConverter;
import com.more.app.entity.product.ImportLetterOfCreditPreAdvise;
import com.more.app.entity.product.Party;
import com.more.app.util.annotations.UILabelUtil;
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
import com.vaadin.flow.data.converter.DoubleToBigDecimalConverter;

public class LcPreAdviceCrudView extends Dialog {

	private ImportLetterOfCreditPreAdvise entity;

	private LcPreAdviceCrudView view;
	private Binder<ImportLetterOfCreditPreAdvise> binder = new Binder<>(ImportLetterOfCreditPreAdvise.class);
	private SplitLayout layout = new SplitLayout();

	public LcPreAdviceCrudView(ImportLetterOfCreditPreAdvise entity) {
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

		Button confirmButton = new Button("Save Record", event -> {
			try {
				binder.writeBean(entity);
				if (binder.isValid()) {
					//FacadeFactory.getFacade().store(entity);
					binder.readBean(entity);
				} else {
					Dialog dg = new Dialog();
					dg.add(new Text("Some Fields are Invalid"));

					dg.open();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		Button cancelButton = new Button("Close Dialog", event -> {
			close();
		});

		confirmButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

		cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

		vlp.add(confirmButton, cancelButton);
		vlp.setAlignItems(Alignment.START);

		VerticalLayout vls = new VerticalLayout();
		// vls.setMargin(true);
		vls.setSpacing(true);

		H4 title = new H4("Letter Of Credit - Pre Advice");

		HorizontalLayout lcamthl = new HorizontalLayout();
		lcamthl.add(currencyCodeSF, amountOfDocumentaryCreditNF);
		lcamthl.setSizeFull();

		lcamthl.setVerticalComponentAlignment(Alignment.BASELINE, currencyCodeSF, amountOfDocumentaryCreditNF);

		HorizontalLayout tolerancehl = new HorizontalLayout();
		tolerancehl.add(tolerance1NF, tolerance2NF);
		tolerancehl.setSizeFull();

		tolerancehl.setVerticalComponentAlignment(Alignment.BASELINE, tolerance1NF, tolerance2NF);

		basicdtlsFL.setMaxWidth("1000px");
		basicdtlsFL.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));

		basicdtlsFL.add(documentaryCrNoTF, formOfDocumentaryCrSF, dateOfExpiryDP, placeOfExpiryTF, lcamthl,
				tolerancehl);

		lcdtlspanel.add(basicdtlsFL);
		applicantDtlsPanel.add(applicantPDF);
		beneficiaryDtlsPanel.add(beneficiaryPDF);
		availableWithDtlsPanel.add(availableWithByPDF);

		shipingDetailsFL.setMaxWidth("1000px");
		shipingDetailsFL.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));

		shipingDetailsFL.add(additionalAmountsCoveredTA, dispatchFromTF, porOfLoadingTF, portOfDischargeTF,
				finalDestinationTF, latestShipmentDF, shipmentPeriodTA, goodsDescriptionTA);

		shipingDtls.add(shipingDetailsFL);
		advThrBankPartyDtls.add(advThrBankPartyPDF);

		otherDtlPanelFL.setMaxWidth("1000px");
		otherDtlPanelFL.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
		otherDtlPanelFL.add(narrativeTA, senderToReceiverInfoTA);
		otherDtlsPanel.add(otherDtlPanelFL);

		vls.add(title, lcdtlspanel, applicantDtlsPanel, beneficiaryDtlsPanel, availableWithDtlsPanel, shipingDtls,
				advThrBankPartyDtls, otherDtlsPanel);

		layout.setOrientation(Orientation.HORIZONTAL);
		layout.addThemeVariants(SplitLayoutVariant.LUMO_SMALL);
		layout.setSplitterPosition(10);
		layout.setSizeFull();

		layout.addToPrimary(vlp);

		layout.addToSecondary(vls);

		add(layout);
	}

	private Select formOfDocumentaryCrSF, currencyCodeSF;
	private TextField documentaryCrNoTF, placeOfExpiryTF, dispatchFromTF, porOfLoadingTF, portOfDischargeTF,
			finalDestinationTF;
	private DatePicker dateOfExpiryDP, latestShipmentDF;
	private CustomAccordionPanel applicantDtlsPanel, beneficiaryDtlsPanel, availableWithDtlsPanel, advThrBankPartyDtls;
	private PartyDetailsCrudView applicantPDF, beneficiaryPDF, availableWithByPDF, advThrBankPartyPDF;
	private NumberField amountOfDocumentaryCreditNF, tolerance1NF, tolerance2NF;
	private TextArea additionalAmountsCoveredTA, shipmentPeriodTA, goodsDescriptionTA, narrativeTA,
			senderToReceiverInfoTA;

	private CustomAccordionPanel lcdtlspanel = new CustomAccordionPanel("LC Details");
	private CustomAccordionPanel otherDtlsPanel = new CustomAccordionPanel("Other Details");
	private CustomAccordionPanel shipingDtls = new CustomAccordionPanel("Shiping Details");

	private FormLayout basicdtlsFL = new FormLayout();
	private FormLayout shipingDetailsFL = new FormLayout();
	private FormLayout otherDtlPanelFL = new FormLayout();

	private void initializeComponents(ImportLetterOfCreditPreAdvise entity) {
		formOfDocumentaryCrSF = new Select();
		formOfDocumentaryCrSF.setItems("Irrevocable", "Irrevocable and Transferable", "Irrevocable Standby LC",
				"Irrevocable Transferable Standby LC");
		formOfDocumentaryCrSF.setLabel(UILabelUtil.getFieldLabel(entity, "formOfDocumentaryCr"));
		formOfDocumentaryCrSF.setPlaceholder("Select an option");
		formOfDocumentaryCrSF.setEmptySelectionCaption("Select an option");
		formOfDocumentaryCrSF.setEmptySelectionAllowed(true);
		formOfDocumentaryCrSF.setRequiredIndicatorVisible(true);

		binder.forField(formOfDocumentaryCrSF).withConverter(new FormOfDocumentaryCreditConverter())
				.bind("formOfDocumentaryCr");

		documentaryCrNoTF = new TextField();
		documentaryCrNoTF.setLabel(UILabelUtil.getFieldLabel(entity, "documentaryCrNo"));
		documentaryCrNoTF.setMaxLength(16);
		documentaryCrNoTF.setRequired(true);

		binder.forField(documentaryCrNoTF).bind("documentaryCrNo");

		dateOfExpiryDP = new DatePicker();
		dateOfExpiryDP.setLabel(UILabelUtil.getFieldLabel(entity, "dateOfExpiry"));
		dateOfExpiryDP.setClearButtonVisible(true);
		dateOfExpiryDP.setRequired(true);

		binder.forField(dateOfExpiryDP).bind("dateOfExpiry");

		placeOfExpiryTF = new TextField();
		placeOfExpiryTF.setLabel(UILabelUtil.getFieldLabel(entity, "placeOfExpiry"));
		placeOfExpiryTF.setMaxLength(29);
		placeOfExpiryTF.setRequired(true);

		binder.forField(placeOfExpiryTF).bind("placeOfExpiry");

		Party applicantParty = entity.getApplicant() != null ? entity.getApplicant() : new Party();
		applicantPDF = new PartyDetailsCrudView(applicantParty);
		applicantDtlsPanel = new CustomAccordionPanel(UILabelUtil.getFieldLabel(entity, "applicant"));

		binder.forField(applicantPDF).bind("applicant");

		Party beneficiaryParty = entity.getBeneficiary() != null ? entity.getBeneficiary() : new Party();
		beneficiaryPDF = new PartyDetailsCrudView(beneficiaryParty);
		beneficiaryDtlsPanel = new CustomAccordionPanel(UILabelUtil.getFieldLabel(entity, "beneficiary"));

		binder.forField(beneficiaryPDF).bind("beneficiary");

		Party availableWithBy = entity.getAvailableWithBy() != null ? entity.getAvailableWithBy() : new Party();
		availableWithByPDF = new PartyDetailsCrudView(availableWithBy);
		availableWithDtlsPanel = new CustomAccordionPanel(UILabelUtil.getFieldLabel(entity, "availableWithBy"));

		binder.forField(availableWithByPDF).bind("availableWithBy");

		currencyCodeSF = new Select();
		currencyCodeSF.setItems("USD", "GBP", "NGN");
		currencyCodeSF.setLabel(UILabelUtil.getFieldLabel(entity, "currencyCode"));

		// The empty selection item is the first item that maps to an null item.
		// As the item is not selectable, using it also as placeholder
		currencyCodeSF.setPlaceholder("Select an option");
		currencyCodeSF.setEmptySelectionCaption("Select an option");
		currencyCodeSF.setEmptySelectionAllowed(true);
		currencyCodeSF.setRequiredIndicatorVisible(true);

		binder.forField(currencyCodeSF).bind("currencyCode");

		amountOfDocumentaryCreditNF = new NumberField();
		amountOfDocumentaryCreditNF.setLabel(UILabelUtil.getFieldLabel(entity, "amountOfDocumentaryCredit"));
		//amountOfDocumentaryCreditNF.setPreventInvalidInput(true);
		amountOfDocumentaryCreditNF.setInvalid(true);
		amountOfDocumentaryCreditNF.setErrorMessage("Invalid Entry, Enter an amount");
		//amountOfDocumentaryCreditNF.setMaxLength(15);
		amountOfDocumentaryCreditNF.setMinWidth("280px");

		binder.forField(amountOfDocumentaryCreditNF).withConverter(new DoubleToBigDecimalConverter())
				.bind("amountOfDocumentaryCredit");

		tolerance1NF = new NumberField();
		tolerance1NF.setLabel(UILabelUtil.getFieldLabel(entity, "tolerance1"));
		//tolerance1NF.setPreventInvalidInput(true);
		tolerance1NF.setInvalid(true);
		tolerance1NF.setErrorMessage("Invalid Entry, Enter an interger");
		// \\d{6}
		//tolerance1NF.setMaxLength(2);
		tolerance1NF.setMaxWidth("200px");
		//tolerance1NF.setPattern("\\\\d{2}");

		binder.forField(tolerance1NF).withConverter(new DoubleToIntegerConverter()).bind("tolerance1");

		tolerance2NF = new NumberField();
		tolerance2NF.setLabel(UILabelUtil.getFieldLabel(entity, "tolerance2"));
		//tolerance2NF.setPreventInvalidInput(true);
		tolerance2NF.setInvalid(true);
		tolerance2NF.setErrorMessage("Invalid Entry, Enter an interger");
		//tolerance2NF.setMaxLength(2);
		tolerance2NF.setMinWidth("200px");

		binder.forField(tolerance2NF).withConverter(new DoubleToIntegerConverter()).bind("tolerance2");

		additionalAmountsCoveredTA = new TextArea();
		additionalAmountsCoveredTA.setLabel(UILabelUtil.getFieldLabel(entity, "additionalAmountsCovered"));

		binder.forField(additionalAmountsCoveredTA).bind("additionalAmountsCovered");

		dispatchFromTF = new TextField();
		dispatchFromTF.setLabel(UILabelUtil.getFieldLabel(entity, "dispatchFrom"));
		dispatchFromTF.setMaxLength(65);

		binder.forField(dispatchFromTF).bind("dispatchFrom");

		porOfLoadingTF = new TextField();
		porOfLoadingTF.setLabel(UILabelUtil.getFieldLabel(entity, "portOfLoading"));
		porOfLoadingTF.setMaxLength(65);

		binder.forField(porOfLoadingTF).bind("portOfLoading");

		portOfDischargeTF = new TextField();
		portOfDischargeTF.setLabel(UILabelUtil.getFieldLabel(entity, "portOfDischarge"));
		portOfDischargeTF.setMaxLength(65);

		binder.forField(portOfDischargeTF).bind("portOfDischarge");

		finalDestinationTF = new TextField();
		finalDestinationTF.setLabel(UILabelUtil.getFieldLabel(entity, "placeOfFinalDestination"));
		finalDestinationTF.setMaxLength(65);

		binder.forField(finalDestinationTF).bind("placeOfFinalDestination");

		latestShipmentDF = new DatePicker();
		latestShipmentDF.setLabel(UILabelUtil.getFieldLabel(entity, "latestDateOfShipment"));
		latestShipmentDF.setClearButtonVisible(true);

		binder.forField(latestShipmentDF).bind("latestDateOfShipment");

		shipmentPeriodTA = new TextArea();
		shipmentPeriodTA.setLabel(UILabelUtil.getFieldLabel(entity, "shipmentPeriod"));
		binder.forField(shipmentPeriodTA).bind("shipmentPeriod");

		goodsDescriptionTA = new TextArea();
		goodsDescriptionTA.setLabel(UILabelUtil.getFieldLabel(entity, "goodDesription"));
		binder.forField(goodsDescriptionTA).bind("goodDesription");

		Party advThrBankParty = entity.getAdvThrBankParty() != null ? entity.getAdvThrBankParty() : new Party();
		advThrBankPartyPDF = new PartyDetailsCrudView(advThrBankParty);
		advThrBankPartyDtls = new CustomAccordionPanel(UILabelUtil.getFieldLabel(entity, "advThrBankParty"));

		binder.forField(advThrBankPartyPDF).bind("advThrBankParty");

		narrativeTA = new TextArea();
		narrativeTA.setLabel(UILabelUtil.getFieldLabel(entity, "narrative"));

		binder.forField(narrativeTA).bind("narrative");

		senderToReceiverInfoTA = new TextArea();
		senderToReceiverInfoTA.setLabel(UILabelUtil.getFieldLabel(entity, "senderToReceiverInfo"));

		binder.forField(senderToReceiverInfoTA).bind("senderToReceiverInfo");
	}
}
